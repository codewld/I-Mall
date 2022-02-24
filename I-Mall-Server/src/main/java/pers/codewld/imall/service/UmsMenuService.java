package pers.codewld.imall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.codewld.imall.model.entity.UmsMenu;

import java.util.List;

/**
 * <p>
 * 后台菜单表 服务类
 * </p>
 *
 * @author codewld
 * @since 2022-02-04
 */
public interface UmsMenuService extends IService<UmsMenu> {

    /**
     * 批量查询位于根结点的菜单
     */
    List<UmsMenu> listRoot();

    /**
     * 批量查询父结点下的子菜单
     */
    List<UmsMenu> listSon(Long id);

}
