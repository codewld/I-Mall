package pers.codewld.imall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import pers.codewld.imall.exception.CustomException;
import pers.codewld.imall.mapper.UmsAdminRoleRelationMapper;
import pers.codewld.imall.mapper.UmsRoleMapper;
import pers.codewld.imall.mapper.UmsRoleMenuRelationMapper;
import pers.codewld.imall.model.entity.UmsMenu;
import pers.codewld.imall.model.entity.UmsRole;
import pers.codewld.imall.model.enums.ResultCode;
import pers.codewld.imall.model.param.UmsRoleParam;
import pers.codewld.imall.model.vo.PageVO;
import pers.codewld.imall.model.vo.UmsRoleMarkVO;
import pers.codewld.imall.service.UmsMenuService;
import pers.codewld.imall.service.UmsRoleService;
import pers.codewld.imall.util.TransformUtil;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    UmsMenuService umsMenuService;

    @Autowired
    UmsAdminRoleRelationMapper umsAdminRoleRelationMapper;

    @Autowired
    UmsRoleMenuRelationMapper umsRoleMenuRelationMapper;

    @Override
    public boolean add(UmsRoleParam umsRoleParam) {
        UmsRole umsRole = TransformUtil.transform(umsRoleParam);
        this.checkCodeDuplicate(umsRole);
        return this.save(umsRole);
    }

    @Transactional
    @Override
    public boolean del(Long id) {
        umsAdminRoleRelationMapper.deleteByRoleId(id);
        return this.removeById(id);
    }

    @Override
    public boolean update(Long id, UmsRoleParam umsRoleParam) {
        UmsRole umsRole = TransformUtil.transform(umsRoleParam);
        umsRole.setId(id);
        this.checkCodeDuplicate(umsRole);
        return this.updateById(umsRole);
    }

    @Override
    public PageVO<UmsRole> page(Integer pageNum, Integer pageSize, String name, String code) {
        QueryWrapper<UmsRole> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like(name != null, "name", name)
                .like(code != null, "code", code);
        Page<UmsRole> page = this.page(new Page<>(pageNum, pageSize), queryWrapper);
        long total = page.getTotal();
        List<UmsRole> list = page.getRecords();
        return new PageVO<>(total, list);
    }

    @Override
    public List<UmsRoleMarkVO> listMark() {
        List<UmsRole> roleList = this.list();
        return roleList.stream().map(TransformUtil::transform).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public boolean updateMenu(Long id, List<Long> menuIdList) {
        umsRoleMenuRelationMapper.deleteByRoleId(id);
        if (CollectionUtils.isEmpty(menuIdList)) {
            return true;
        }
        int res = umsRoleMenuRelationMapper.insert(id, menuIdList);
        if (res != menuIdList.size()) {
            throw new CustomException(ResultCode.FAILED);
        }
        return true;
    }

    @Override
    public List<Long> listMenuId(Long id) {
        return umsRoleMenuRelationMapper.selectMenuIdByRoleId(id);
    }

    @Override
    public List<String> listMenuCode(Long id) {
        List<Long> menuIdList = this.listMenuId(id);
        return umsMenuService.listByIds(menuIdList)
                .stream()
                .map(UmsMenu::getCode)
                .collect(Collectors.toList());
    }

    /**
     * 检查编码是否重复
     */
    private void checkCodeDuplicate(UmsRole umsRole) {
        String code = umsRole.getCode();
        if (code == null) {
            return;
        }
        QueryWrapper<UmsRole> queryWrapper = new QueryWrapper<UmsRole>()
                .eq("code", code);
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new CustomException(ResultCode.VALIDATE_FAILED, "编码重复");
        }
    }
}
