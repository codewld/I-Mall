package pers.codewld.imall.mapper;

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
    int insertRelation(Long adminId, List<Long> roleIdList);

    /**
     * 根据用户ID删除关系
     */
    int deleteRelationByAdminId(Long adminId);

    /**
     * 根据角色ID删除关系
     */
    int deleteRelationByRoleId(Long roleId);

    /**
     * 根据用户ID查询角色列表
     */
    List<Long> selectRoleListByAdminId(Long adminId);

}
