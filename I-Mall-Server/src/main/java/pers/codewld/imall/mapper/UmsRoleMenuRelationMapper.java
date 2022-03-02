package pers.codewld.imall.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 角色和菜单关系 Mapper 接口
 * </p>
 *
 * @author codewld
 * @since 2022-02-27
 */
@Mapper
public interface UmsRoleMenuRelationMapper {

    /**
     * 新增关系
     */
    int insert(long roleId, List<Long> menuIdList);

    /**
     * 根据角色ID删除
     */
    int deleteByRoleId(Long roleId);

    /**
     * 根据菜单ID删除
     */
    int deleteByMenuId(Long menuId);

    /**
     * 根据角色ID查询菜单ID
     */
    List<Long> selectMenuIdByRoleId(Long roleId);
}
