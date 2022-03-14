package pers.codewld.imall.admin.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.codewld.imall.admin.model.param.UmsAdminParam;
import pers.codewld.imall.common.model.vo.PageVO;
import pers.codewld.imall.admin.model.vo.UmsAdminVO;
import pers.codewld.imall.admin.model.vo.UmsRoleMarkVO;
import pers.codewld.imall.admin.service.UmsAdminService;
import pers.codewld.imall.common.config.ValidatorConfig;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * <p>
 * 后台用户 前端控制器
 * </p>
 *
 * @author codewld
 * @since 2022-02-04
 */
@PreAuthorize("hasAuthority('ums-admin')")
@Validated
@RestController
@RequestMapping("/ums/admin")
public class UmsAdminController {

    @Autowired
    UmsAdminService umsAdminService;

    @PostMapping()
    @ApiOperation("添加用户")
    public boolean add(@RequestBody @Validated(ValidatorConfig.Group.add.class) @ApiParam("用户参数") UmsAdminParam umsAdminParam) {
        return umsAdminService.add(umsAdminParam);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    public boolean del(@PathVariable @ApiParam("用户id") Long id) {
        return umsAdminService.del(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("修改用户")
    public boolean update(@PathVariable @ApiParam("用户id") Long id, @RequestBody @Validated @ApiParam("用户参数") UmsAdminParam umsAdminParam) {
        return umsAdminService.update(id, umsAdminParam);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询，可搜索")
    public PageVO<UmsAdminVO> page(@RequestParam(value = "pageNum", defaultValue = "1") @Min(value = 1, message = "页数最小为1") @ApiParam("当前页数") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "5") @Min(value = 1, message = "每页条数最小为1") @ApiParam("每页条数") Integer pageSize,
                                   @RequestParam(value = "username", required = false) @ApiParam("用户名") String username,
                                   @RequestParam(value = "status", required = false) @ApiParam("启用状态") Boolean status,
                                   @RequestParam(value = "nickName", required = false) @ApiParam("昵称") String nickName,
                                   @RequestParam(value = "email", required = false) @ApiParam("邮箱") String email) {
        return umsAdminService.page(pageNum, pageSize, username, status, nickName, email);
    }

    @PutMapping("/role/{id}")
    @ApiOperation("修改用户拥有的角色")
    public boolean updateRole(@PathVariable @ApiParam("用户id") Long id,
                              @RequestBody(required = false) @ApiParam("角色id列表") List<Long> roleIdList) {
        return umsAdminService.updateRole(id, roleIdList);
    }

    @GetMapping("/role/mark/{id}")
    @ApiOperation("查询用户拥有的角色 [标记列表]")
    public List<UmsRoleMarkVO> listRoleMark(@PathVariable @ApiParam("用户id") Long id) {
        return umsAdminService.listRoleMark(id);
    }

}
