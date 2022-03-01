package pers.codewld.imall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.codewld.imall.model.entity.UmsMenu;
import pers.codewld.imall.model.param.UmsMenuParam;
import pers.codewld.imall.model.vo.UmsMenuMarkVO;

import java.util.List;

/**
 * <p>
 * 后台菜单 服务类
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
     * 查询父节点下的子菜单
     */
    List<UmsMenu> listSon(Long id);

    /**
     * 查询菜单 [标记形式、树形结构]
     */
    List<UmsMenuMarkVO> listMark();

}
