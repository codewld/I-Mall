package pers.codewld.imall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.codewld.imall.model.entity.UmsMenu;
import pers.codewld.imall.model.param.UmsMenuParam;
import pers.codewld.imall.model.vo.UmsMenuMarkVO;

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
     * 添加菜单
     */
    boolean add(UmsMenuParam umsMenuParam);

    /**
     * 删除菜单
     */
    boolean del(Long id);

    /**
     * 修改菜单
     */
    boolean update(Long id, UmsMenuParam umsMenuParam);

    /**
     * 批量查询位于根结点的菜单
     */
    List<UmsMenu> listRoot();

    /**
     * 批量查询父结点下的子菜单
     */
    List<UmsMenu> listSon(Long id);

    /**
     * 批量查询位于根结点的菜单标记
     */
    List<UmsMenuMarkVO> listRootMark();

    /**
     * 批量查询父结点下的子菜单标记
     */
    List<UmsMenuMarkVO> listSonMark(Long id);

}
