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
     * 根据ID查询
     */
    UmsMenu get(Long id);

    /**
     * 根据编码查询
     */
    UmsMenu get(String code);

    /**
     * 生成树形结构
     */
    List<UmsMenu> generateTree(List<UmsMenu> list);

    /**
     * 查询树形结构的菜单
     */
    List<UmsMenu> tree();

    /**
     * 查询树形结构的菜单 [标记形式]
     */
    List<UmsMenuMarkVO> treeMark();

}
