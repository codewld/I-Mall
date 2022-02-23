package pers.codewld.imall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.codewld.imall.model.entity.UmsRole;
import pers.codewld.imall.model.param.UmsRoleParam;
import pers.codewld.imall.model.vo.PageVO;
import pers.codewld.imall.model.vo.UmsRoleMarkVO;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 服务类
 * </p>
 *
 * @author codewld
 * @since 2022-02-04
 */
public interface UmsRoleService extends IService<UmsRole> {

    /**
     * 新增角色
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
     * 查询角色列表，分页，可搜索
     * @param pageNum 页数
     * @param pageSize 每页条数
     * @param name 名称
     */
    PageVO<UmsRole> list(Integer pageNum, Integer pageSize, String name);

    /**
     * 查询角色标记列表
     */
    List<UmsRoleMarkVO> listMark();
}
