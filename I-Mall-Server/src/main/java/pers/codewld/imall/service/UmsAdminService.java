package pers.codewld.imall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.codewld.imall.model.entity.UmsAdmin;
import pers.codewld.imall.model.param.UmsAdminParam;
import pers.codewld.imall.model.param.UmsAdminQueryParam;
import pers.codewld.imall.model.vo.PageVO;
import pers.codewld.imall.model.vo.UmsAdminVO;

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
    Boolean add(UmsAdminParam umsAdminParam);

    /**
     * 修改用户
     */
    Boolean update(Long id, UmsAdminParam umsAdminParam);

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
}
