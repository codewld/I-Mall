package pers.codewld.imall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.codewld.imall.model.entity.UmsAdmin;
import pers.codewld.imall.model.param.UmsAdminParam;
import pers.codewld.imall.model.param.UmsAdminQueryParam;
import pers.codewld.imall.model.vo.PageVO;
import pers.codewld.imall.model.vo.UmsAdminVO;

import java.util.List;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author codewld
 * @since 2022-02-04
 */
public interface UmsAdminService extends IService<UmsAdmin> {

    /**
     * 新增用户
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
     * 查询用户列表，分页，可搜索
     * @param pageNum 页数
     * @param pageSize 每页条数
     * @param umsAdminQueryParam 搜索参数
     */
    PageVO<UmsAdminVO> page(Integer pageNum, Integer pageSize, UmsAdminQueryParam umsAdminQueryParam);

    /**
     * 获取黑名单 [黑名单为所有被禁用的用户]
     */
    List<Long> getBlacklist();

    /**
     * 刷新黑名单 [清空缓冲，下次获取黑名单时需重新获取]
     */
    void refreshBlacklist();

    /**
     * 判断用户是否在黑名单中
     */
    boolean isInBlacklist(Long id);
}