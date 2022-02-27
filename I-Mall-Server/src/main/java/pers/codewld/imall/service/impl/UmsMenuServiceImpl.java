package pers.codewld.imall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * 后台菜单表 服务实现类
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

    @Override
    public boolean del(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean update(Long id, UmsMenuParam umsMenuParam) {
        UmsMenu umsMenu = TransformUtil.transform(umsMenuParam);
        checkParent(umsMenu.getParentId());
        checkCode(umsMenuParam);
        umsMenu.setId(id);
        return this.updateById(umsMenu);
    }

    @Override
    public List<UmsMenu> listRoot() {
        QueryWrapper<UmsMenu> queryWrapper = new QueryWrapper<UmsMenu>()
                .eq("parent_id", 0);
        return this.list(queryWrapper);
    }

    @Override
    public List<UmsMenu> listSon(Long id) {
        QueryWrapper<UmsMenu> queryWrapper = new QueryWrapper<UmsMenu>()
                .eq("parent_id", id);
        return this.list(queryWrapper);
    }

    @Override
    public List<UmsMenuMarkVO> listMark() {
        UmsMenuMarkVO top = new UmsMenuMarkVO(0L, "无父级", new ArrayList<>());
        List<UmsMenu> list = this.list();
        setChildren(top, list);
        System.out.println(top.getChildren());
        return top.getChildren();
    }

    /**
     * 递归设置子节点
     * @param father 父节点
     * @param list 剩余节点列表
     */
    private void setChildren(UmsMenuMarkVO father, List<UmsMenu> list) {
        if (list.size() == 0) {
            return;
        }
        // 获取所有的子节点
        List<UmsMenu> children = list
                .stream()
                .filter(o -> Objects.equals(father.getId(), o.getParentId()))
                .collect(Collectors.toList());
        // 将子节点转换为VO并放入
        children.forEach(o -> {
            UmsMenuMarkVO childrenVO = TransformUtil.transform(o);
            if (father.getChildren() == null) {
                father.setChildren(new ArrayList<>());
            }
            father.getChildren().add(childrenVO);
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
        UmsMenu parent = this.getById(parentId);
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
