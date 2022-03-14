package pers.codewld.imall.admin.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户和角色关系 Mapper 接口
 * </p>
 *
 * @author codewld
 * @since 2022-02-23
 */
@Mapper
public interface UmsAdminRoleRelationMapper {

    /**
     * 新增关系
     */
    int insert(Long adminId, List<Long> roleIdList);

    /**
     * 根据用户ID删除
     */
    int deleteByAdminId(Long adminId);

    /**
     * 根据角色ID删除
     */
    int deleteByRoleId(Long roleId);

    /**
     * 根据用户ID查询角色ID
     */
    List<Long> selectRoleIdByAdminId(Long adminId);

}
