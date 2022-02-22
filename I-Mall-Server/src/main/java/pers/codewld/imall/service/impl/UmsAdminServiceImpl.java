package pers.codewld.imall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pers.codewld.imall.mapper.UmsAdminMapper;
import pers.codewld.imall.model.entity.UmsAdmin;
import pers.codewld.imall.model.param.UmsAdminParam;
import pers.codewld.imall.model.param.UmsAdminQueryParam;
import pers.codewld.imall.model.vo.PageVO;
import pers.codewld.imall.model.vo.UmsAdminVO;
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

    /**
     * 获取自身的Bean，以避免直接调用自身时AOP的失效
     */
    private UmsAdminService getBean() {
        return BeanUtil.getBean(UmsAdminService.class);
    }

    @Override
    public boolean add(UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = TransformUtil.transform(umsAdminParam);
        // 未设置status或设置status为false时，更新黑名单
        if (umsAdmin.getStatus() == null || !umsAdmin.getStatus()) {
            getBean().refreshBlacklist();
        }
        return this.save(umsAdmin);
    }

    @Override
    public boolean del(Long id) {
        boolean res = this.removeById(id);
        // 更新黑名单
        getBean().refreshBlacklist();
        return res;
    }

    @Override
    public boolean update(Long id, UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = TransformUtil.transform(umsAdminParam);
        umsAdmin.setId(id);
        // 如果设置了status，更新黑名单
        if (umsAdmin.getStatus() != null) {
            getBean().refreshBlacklist();
        }
        return this.updateById(umsAdmin);
    }

    @Override
    public UmsAdmin getByUsername(String username) {
        QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper<UmsAdmin>().eq("username", username);
        return this.getOne(queryWrapper, false);
    }

    @Override
    public PageVO<UmsAdminVO> page(Integer pageNum, Integer pageSize, UmsAdminQueryParam umsAdminQueryParam) {
        QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper<>();
        String username = umsAdminQueryParam.getUsername();
        String email = umsAdminQueryParam.getEmail();
        String nickName = umsAdminQueryParam.getNickName();
        Boolean status = umsAdminQueryParam.getStatus();
        queryWrapper
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
        QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id").eq("status", 0);
        return this.listObjs(queryWrapper, o -> (long) o);
    }

    @CacheEvict(value = "blacklist", allEntries = true)
    @Override
    public void refreshBlacklist() {

    }

    @Override
    public boolean isInBlacklist(Long id) {
        List<Long> blacklist = getBean().getBlacklist();
        return blacklist.contains(id);
    }

}