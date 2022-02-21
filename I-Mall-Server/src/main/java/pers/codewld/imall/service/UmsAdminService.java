package pers.codewld.imall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.codewld.imall.model.entity.UmsAdmin;
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
     * 根据username查询
     */
    UmsAdmin getByUsername(String username);

    /**
     * 分页查询
     * @param pageNum 页数
     * @param pageSize 每页条数
     */
    PageVO<UmsAdminVO> page(Integer pageNum, Integer pageSize);

}
