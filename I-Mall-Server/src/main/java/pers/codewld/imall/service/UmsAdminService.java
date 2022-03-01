package pers.codewld.imall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.codewld.imall.model.entity.UmsAdmin;
import pers.codewld.imall.model.param.UmsAdminParam;
import pers.codewld.imall.model.vo.PageVO;
import pers.codewld.imall.model.vo.UmsAdminVO;
import pers.codewld.imall.model.vo.UmsRoleMarkVO;

import java.util.List;

/**
 * <p>
 * 后台用户 服务类
 * </p>
 *
 * @author codewld
 * @since 2022-02-04
 */
public interface UmsAdminService extends IService<UmsAdmin> {

    /**
     * 添加用户
     */
    boolean add(UmsAdminParam umsAdminParam);

    /**
     * 删除用户
     */
    boolean del(Long id);

    /**
     * 修改用户
     */
    boolean update(Long id, UmsAdminParam umsAdminParam);

    /**
     * 根据username查询
     */
    UmsAdmin getByUsername(String username);

    /**
     * 分页查询用户，可搜索
     *
     * @param pageNum  页数
     * @param pageSize 每页条数
     * @param username 用户名
     * @param status   启用状态
     * @param nickName 昵称
     * @param email    邮箱
     */
    PageVO<UmsAdminVO> page(Integer pageNum, Integer pageSize, String username, Boolean status, String nickName, String email);

    /**
     * 修改用户拥有的角色
     *
     * @param id         用户id
     * @param roleIdList 角色id列表
     * @return 修改结果
     */
    boolean updateRole(Long id, List<Long> roleIdList);

    /**
     * 批量查询用户拥有的角色 [ID列表]
     *
     * @param id 用户id
     * @return 角色ID列表
     */
    List<Long> listRoleId(Long id);

    /**
     * 批量查询用户拥有的角色 [标记列表]
     *
     * @param id 用户id
     * @return 角色标记列表
     */
    List<UmsRoleMarkVO> listRoleMark(Long id);

    /**
     * 获取黑名单 [黑名单为所有被禁用的用户]
     */
    List<Long> getBlacklist();

    /**
     * 判断用户是否在黑名单中
     */
    boolean isInBlacklist(Long id);
}