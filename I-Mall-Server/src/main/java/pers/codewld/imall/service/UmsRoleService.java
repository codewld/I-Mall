package pers.codewld.imall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.codewld.imall.model.entity.UmsRole;
import pers.codewld.imall.model.param.UmsRoleParam;
import pers.codewld.imall.model.vo.PageVO;
import pers.codewld.imall.model.vo.UmsRoleMarkVO;

import java.util.List;

/**
 * <p>
 * 后台角色 服务类
 * </p>
 *
 * @author codewld
 * @since 2022-02-04
 */
public interface UmsRoleService extends IService<UmsRole> {

    /**
     * 添加角色
     */
    boolean add(UmsRoleParam umsRoleParam);

    /**
     * 删除角色
     */
    boolean del(Long id);

    /**
     * 修改角色
     */
    boolean update(Long id, UmsRoleParam umsRoleParam);

    /**
     * 分页查询，可搜索
     *
     * @param pageNum  页数
     * @param pageSize 每页条数
     * @param name     名称
     * @param code     编码
     */
    PageVO<UmsRole> page(Integer pageNum, Integer pageSize, String name, String code);

    /**
     * 根据编码批量查询角色
     */
    List<UmsRole> listByCodeList(List<String> codeList);

    /**
     * 查询角色 [标记列表]
     */
    List<UmsRoleMarkVO> listMark();

    /**
     * 修改角色拥有的菜单
     *
     * @param id         角色id
     * @param menuIdList 菜单id列表
     * @return 修改结果
     */
    boolean updateMenu(Long id, List<Long> menuIdList);

    /**
     * 查询角色拥有的菜单 [ID列表]
     *
     * @param id 角色id
     * @return 菜单ID
     */
    List<Long> listMenuId(Long id);
}
