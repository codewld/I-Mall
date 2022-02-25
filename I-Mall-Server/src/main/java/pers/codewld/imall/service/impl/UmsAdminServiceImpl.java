package pers.codewld.imall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.codewld.imall.exception.CustomException;
import pers.codewld.imall.mapper.UmsAdminMapper;
import pers.codewld.imall.mapper.UmsAdminRoleRelationMapper;
import pers.codewld.imall.model.entity.UmsAdmin;
import pers.codewld.imall.model.enums.ResultCode;
import pers.codewld.imall.model.param.UmsAdminParam;
import pers.codewld.imall.model.vo.PageVO;
import pers.codewld.imall.model.vo.UmsAdminVO;
import pers.codewld.imall.model.vo.UmsRoleMarkVO;
import pers.codewld.imall.security.MD5PasswordEncoder;
import pers.codewld.imall.service.UmsAdminService;
import pers.codewld.imall.util.BeanUtil;
import pers.codewld.imall.util.TransformUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台用户表 服务实现类
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
    UmsAdminRoleRelationMapper umsAdminRoleRelationMapper;

    @CacheEvict(value = "blacklist", allEntries = true)
    @Override
    public boolean add(UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = TransformUtil.transform(umsAdminParam);
        this.checkUsernameDuplicate(umsAdmin);
        return this.save(umsAdmin);
    }

    @CacheEvict(value = "blacklist", allEntries = true)
    @Override
    public boolean del(Long id) {
        return this.removeById(id);
    }

    @CacheEvict(value = "blacklist", allEntries = true)
    @Override
    public boolean update(Long id, UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = TransformUtil.transform(umsAdminParam);
        umsAdmin.setId(id);
        this.checkUsernameDuplicate(umsAdmin);
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

    @Cacheable(value = "blacklist")
    @Override
    public List<Long> getBlacklist() {
        QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper<UmsAdmin>()
                .select("id").eq("status", 0);
        return this.listObjs(queryWrapper, o -> (long) o);
    }

    @Override
    public boolean isInBlacklist(Long id) {
        List<Long> blacklist = getBean().getBlacklist();
        return blacklist.contains(id);
    }

    @Transactional
    @Override
    public boolean updateRole(Long id, List<Long> roleIdList) {
        umsAdminRoleRelationMapper.deleteByAdminId(id);
        if (roleIdList == null || roleIdList.size() == 0) {
            return true;
        }
        int res = umsAdminRoleRelationMapper.insert(id, roleIdList);
        if (res != roleIdList.size()) {
            throw new CustomException(ResultCode.FAILED);
        }
        return true;
    }

    @Override
    public List<UmsRoleMarkVO> listRoleMark(Long id) {
        return umsAdminRoleRelationMapper.selectRoleMarkListByAdminId(id);
    }


    /**
     * 获取自身的Bean，以避免直接调用自身时AOP的失效
     */
    private UmsAdminService getBean() {
        return BeanUtil.getBean(UmsAdminService.class);
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