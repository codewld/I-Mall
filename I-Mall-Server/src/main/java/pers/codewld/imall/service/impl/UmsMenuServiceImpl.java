package pers.codewld.imall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import pers.codewld.imall.exception.CustomException;
import pers.codewld.imall.mapper.UmsMenuMapper;
import pers.codewld.imall.model.entity.UmsMenu;
import pers.codewld.imall.model.enums.ResultCode;
import pers.codewld.imall.model.param.UmsMenuParam;
import pers.codewld.imall.model.vo.UmsMenuMarkVO;
import pers.codewld.imall.service.UmsMenuService;
import pers.codewld.imall.util.TransformUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台菜单 服务实现类
 * </p>
 *
 * @author codewld
 * @since 2022-02-04
 */
@Service
public class UmsMenuServiceImpl extends ServiceImpl<UmsMenuMapper, UmsMenu> implements UmsMenuService {

    @Override
    public boolean add(UmsMenuParam umsMenuParam) {
        UmsMenu umsMenu = TransformUtil.transform(umsMenuParam);
        checkParent(umsMenu.getParentId());
        checkCode(umsMenuParam);
        return this.save(umsMenu);
    }

    // 删除菜单，则清空所有"角色对应的菜单编码列表"的缓存
    // 删除菜单，无需改变"由编码获取的菜单"
    // 删除菜单，无需改变"由ID获取的菜单"
    @CacheEvict(value = "MenuCodeListOfRole", allEntries = true)
    @Override
    public boolean del(Long id) {
        return this.removeById(id);
    }

    // 更新菜单的编码，清空所有"角色对应的菜单编码列表"的缓存
    // 更新菜单，清空所有"由编码获取的菜单"的缓存
    // 更新菜单，清空"由ID获取的菜单"的缓存
    @Caching(evict = {
            @CacheEvict(value = "MenuCodeListOfRole", allEntries = true, condition = "#umsMenuParam.code != null"),
            @CacheEvict(value = "MenuGetByCode", allEntries = true),
            @CacheEvict(value = "MenuGetById", key = "#id")
    })
    @Override
    public boolean update(Long id, UmsMenuParam umsMenuParam) {
        UmsMenu umsMenu = TransformUtil.transform(umsMenuParam);
        checkParent(umsMenu.getParentId());
        checkCode(umsMenuParam);
        umsMenu.setId(id);
        return this.updateById(umsMenu);
    }

    // 缓存"由ID获取的菜单"
    @Cacheable(value = "MenuGetById", key = "#id")
    @Override
    public UmsMenu get(Long id) {
        return this.getById(id);
    }

    // 缓存"由编码获取的菜单"
    @Cacheable(value = "MenuGetByCode", key = "#code")
    @Override
    public UmsMenu get(String code) {
        QueryWrapper<UmsMenu> queryWrapper = new QueryWrapper<UmsMenu>()
                .eq("code", code);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<UmsMenu> generateTree(List<UmsMenu> list) {
        // 实例化虚拟的父结点
        UmsMenu top = new UmsMenu();
        top.setId(0L);
        top.setNonLeaf(true);
        top.setChildren(new ArrayList<>());
        // 组合为树形
        setChildren(top, list);
        return top.getChildren();
    }

    @Override
    public List<UmsMenu> tree() {
        return this.generateTree(this.list());
    }

    @Override
    public List<UmsMenuMarkVO> treeMark() {
        return this.tree()
                .stream()
                .map(TransformUtil::transform2Mark)
                .collect(Collectors.toList());
    }

    /**
     * 递归设置子节点
     *
     * @param father 父节点
     * @param list   剩余节点列表
     */
    private void setChildren(UmsMenu father, List<UmsMenu> list) {
        if (list.size() == 0) {
            return;
        }
        // 如果父结点不是非叶节点
        if (!father.getNonLeaf()) {
            return;
        }
        if (father.getChildren() == null) {
            father.setChildren(new ArrayList<>());
        }
        // 获取所有的子节点
        List<UmsMenu> children = list
                .stream()
                .filter(o -> Objects.equals(father.getId(), o.getParentId()))
                .collect(Collectors.toList());
        // 将子节点放入
        children.forEach(o -> {
            // 确保创建children属性
            if (o.getNonLeaf() && o.getChildren() == null) {
                o.setChildren(new ArrayList<>());
            }
            father.getChildren().add(o);
        });
        // 删除子节点
        list.removeAll(children);
        // 递归设置子节点的子节点
        if (father.getChildren() != null) {
            father.getChildren().forEach(o -> {
                setChildren(o, list);
            });
        }
    }

    /**
     * 检查父级ID是否合法
     */
    private void checkParent(Long parentId) {
        if (parentId == null) {
            return;
        }
        if (parentId == 0) {
            return;
        }
        UmsMenu parent = this.get(parentId);
        if (parent == null || !parent.getNonLeaf()) {
            throw new CustomException(ResultCode.VALIDATE_FAILED, "父级ID错误");
        }
    }

    /**
     * 检查编码是否合法
     */
    private void checkCode(UmsMenuParam umsMenuParam) {
        String code = umsMenuParam.getCode();
        if (code == null) {
            return;
        }
        QueryWrapper<UmsMenu> queryWrapper = new QueryWrapper<UmsMenu>()
                .eq("code", code);
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new CustomException(ResultCode.VALIDATE_FAILED, "编码重复");
        }
    }

}
