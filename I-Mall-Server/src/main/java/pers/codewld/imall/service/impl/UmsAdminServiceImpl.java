package pers.codewld.imall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.codewld.imall.model.entity.UmsAdmin;
import pers.codewld.imall.mapper.UmsAdminMapper;
import pers.codewld.imall.model.param.UmsAdminParam;
import pers.codewld.imall.model.vo.PageVO;
import pers.codewld.imall.model.vo.UmsAdminVO;
import pers.codewld.imall.security.MD5PasswordEncoder;
import pers.codewld.imall.service.UmsAdminService;
import pers.codewld.imall.util.TransformUtil;

import java.time.LocalDateTime;
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

    @Override
    public Boolean add(UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = TransformUtil.transform(umsAdminParam);
        umsAdmin.setPassword(md5PasswordEncoder.encode(umsAdmin.getPassword()));
        umsAdmin.setCreateTime(LocalDateTime.now());
        return this.save(umsAdmin);
    }

    @Override
    public Boolean update(Long id, UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = TransformUtil.transform(umsAdminParam);
        umsAdmin.setId(id);
        if (umsAdmin.getPassword() != null) {
            umsAdmin.setPassword(md5PasswordEncoder.encode(umsAdmin.getPassword()));
        }
        umsAdmin.setUpdateTime(LocalDateTime.now());
        return this.updateById(umsAdmin);
    }

    @Override
    public UmsAdmin getByUsername(String username) {
        QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper<UmsAdmin>().eq("username", username);
        return this.getOne(queryWrapper, false);
    }

    @Override
    public PageVO<UmsAdminVO> page(Integer pageNum, Integer pageSize) {
        Page<UmsAdmin> page = this.page(new Page<>(pageNum, pageSize));
        long total = page.getTotal();
        List<UmsAdminVO> list = page.getRecords().stream().map(TransformUtil::transform).collect(Collectors.toList());
        return new PageVO<>(total, list);
    }

}
