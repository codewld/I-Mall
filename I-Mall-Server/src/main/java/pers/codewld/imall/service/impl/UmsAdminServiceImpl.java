package pers.codewld.imall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.codewld.imall.entity.UmsAdmin;
import pers.codewld.imall.mapper.UmsAdminMapper;
import pers.codewld.imall.service.UmsAdminService;

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

    @Override
    public UmsAdmin getByUsername(String username) {
        return this.getOne(new QueryWrapper<UmsAdmin>().eq("username", username), false);
    }

    @Override
    public Object page(Integer pageNum, Integer pageSize) {
        return this.page(new Page<>(pageNum, pageSize));
    }

}
