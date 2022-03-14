package pers.codewld.imall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import pers.codewld.imall.common.exception.CustomException;
import pers.codewld.imall.admin.model.entity.UmsAdmin;
import pers.codewld.imall.admin.model.entity.UmsRole;
import pers.codewld.imall.common.model.enums.ResultCode;
import pers.codewld.imall.admin.model.param.UmsAdminParam;
import pers.codewld.imall.admin.model.vo.UmsAdminVO;
import pers.codewld.imall.admin.model.vo.UmsRoleMarkVO;
import pers.codewld.imall.admin.service.UmsAdminService;
import pers.codewld.imall.admin.service.UmsRoleService;
import pers.codewld.imall.security.util.MD5PasswordEncoder;
import pers.codewld.imall.admin.mapper.UmsAdminMapper;
import pers.codewld.imall.admin.mapper.UmsAdminRoleRelationMapper;
import pers.codewld.imall.common.model.vo.PageVO;
import pers.codewld.imall.common.util.RedisUtil;
import pers.codewld.imall.admin.util.TransformUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台用户 服务实现类
 * </p>
 *
 * @author codewld
 * @since 2022-02-04
 */
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements UmsAdminService {

    @Autowired
    MD5PasswordEncoder md5PasswordEncoder;

    @Autowired
    UmsRoleService umsRoleService;

    @Autowired
    UmsAdminRoleRelationMapper umsAdminRoleRelationMapper;

    @Autowired
    RedisUtil redisUtil;

    @Value("${jwt.expiration}")
    private Long expiration; // 过期时长，以秒为单位

    @CacheEvict(value = "DisabledAdmin", condition = "#umsAdminParam.status == false ", allEntries = true)
    @Override
    public boolean add(UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = TransformUtil.transform(umsAdminParam);
        this.checkUsernameDuplicate(umsAdmin);
        return this.save(umsAdmin);
    }

    @Override
    public boolean del(Long id) {
        invalidateIssuedJWT(id);
        return this.removeById(id);
    }

    @CacheEvict(value = "DisabledAdmin", condition = "#umsAdminParam.status != null ", allEntries = true)
    @Override
    public boolean update(Long id, UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = TransformUtil.transform(umsAdminParam);
        umsAdmin.setId(id);
        this.checkUsernameDuplicate(umsAdmin);
        if (umsAdminParam.getPassword() != null) {
            invalidateIssuedJWT(id);
        }
        return this.updateById(umsAdmin);
    }

    @Override
    public UmsAdmin getByUsername(String username) {
        QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper<UmsAdmin>()
                .eq("username", username);
        return this.getOne(queryWrapper, false);
    }

    @Override
    public PageVO<UmsAdminVO> page(Integer pageNum, Integer pageSize, String username, Boolean status, String nickName, String email) {
        QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper<UmsAdmin>()
                .like(username != null, "username", username)
                .like(email != null, "email", email)
                .like(nickName != null, "nick_name", nickName)
                .eq(status != null, "status", status);
        Page<UmsAdmin> page = this.page(new Page<>(pageNum, pageSize), queryWrapper);
        long total = page.getTotal();
        List<UmsAdminVO> list = page.getRecords().stream().map(TransformUtil::transform).collect(Collectors.toList());
        return new PageVO<>(total, list);
    }

    @Transactional
    @Override
    public boolean updateRole(Long id, List<Long> roleIdList) {
        umsAdminRoleRelationMapper.deleteByAdminId(id);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return true;
        }
        int res = umsAdminRoleRelationMapper.insert(id, roleIdList);
        if (res != roleIdList.size()) {
            throw new CustomException(ResultCode.FAILED);
        }
        invalidateIssuedJWT(id);
        return true;
    }

    @Override
    public List<Long> listRoleId(Long id) {
        return umsAdminRoleRelationMapper.selectRoleIdByAdminId(id);
    }

    @Override
    public List<UmsRoleMarkVO> listRoleMark(Long id) {
        List<Long> roleIdList = umsAdminRoleRelationMapper.selectRoleIdByAdminId(id);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return null;
        }
        QueryWrapper<UmsRole> queryWrapper = new QueryWrapper<UmsRole>()
                .in("id", roleIdList);
        return umsRoleService.list(queryWrapper).stream().map(TransformUtil::transform).collect(Collectors.toList());
    }

    @Cacheable(value = "DisabledAdmin")
    @Override
    public List<Long> listDisabled() {
        QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper<UmsAdmin>()
                .select("id").eq("status", 0);
        return this.listObjs(queryWrapper, o -> (long) o);
    }

    @Override
    public void invalidateIssuedJWT(Long id) {
        redisUtil.set("invalidateIssuedJWT::" + id, System.currentTimeMillis(), expiration);
    }

    @Override
    public boolean isJWTInvalidated(Long id, Long time) {
        Object o = redisUtil.get("invalidateIssuedJWT::" + id);
        // 如果未设置失效
        if (o == null) {
            return false;
        }
        // 如果签发时间晚于最早有效时间
        if ((Long) o <= time) {
            return false;
        }
        return true;
    }

    /**
     * 检查用户名是否重复
     */
    private void checkUsernameDuplicate(UmsAdmin umsAdmin) {
        String username = umsAdmin.getUsername();
        if (username == null) {
            return;
        }
        QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper<UmsAdmin>()
                .eq("username", username);
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new CustomException(ResultCode.VALIDATE_FAILED, "用户名重复");
        }
    }

}