package pers.codewld.imall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.codewld.imall.mapper.UmsRoleMapper;
import pers.codewld.imall.model.entity.UmsRole;
import pers.codewld.imall.model.param.UmsRoleParam;
import pers.codewld.imall.model.vo.PageVO;
import pers.codewld.imall.service.UmsRoleService;
import pers.codewld.imall.util.TransformUtil;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 服务实现类
 * </p>
 *
 * @author codewld
 * @since 2022-02-04
 */
@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements UmsRoleService {

    @Override
    public boolean add(UmsRoleParam umsRoleParam) {
        UmsRole umsRole = TransformUtil.transform(umsRoleParam);
        return this.save(umsRole);
    }

    @Override
    public boolean del(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean update(Long id, UmsRoleParam umsRoleParam) {
        UmsRole umsRole = TransformUtil.transform(umsRoleParam);
        umsRole.setId(id);
        return this.updateById(umsRole);
    }

    @Override
    public PageVO<UmsRole> list(Integer pageNum, Integer pageSize, String name, Boolean status) {
        QueryWrapper<UmsRole> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like(name != null, "name", name)
                .eq(status != null, "status", status);
        Page<UmsRole> page = this.page(new Page<>(pageNum, pageSize), queryWrapper);
        long total = page.getTotal();
        List<UmsRole> list = page.getRecords();
        return new PageVO<>(total, list);
    }
}
