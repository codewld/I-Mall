package pers.codewld.imall.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.codewld.imall.config.ValidatorConfig;
import pers.codewld.imall.model.entity.SmsDict;
import pers.codewld.imall.model.param.SmsDictParam;
import pers.codewld.imall.model.vo.PageVO;
import pers.codewld.imall.service.SmsDictService;

import javax.validation.constraints.Min;

/**
 * <p>
 * 字典 前端控制器
 * </p>
 *
 * @author codewld
 * @since 2022-02-09
 */
@Validated
@RestController
@RequestMapping("/sms/dict")
public class SmsDictController {

    @Autowired
    SmsDictService smsDictService;

    @PostMapping()
    @ApiOperation("添加字典")
    public boolean add(@RequestBody @Validated(ValidatorConfig.Group.add.class) @ApiParam("用户参数") SmsDictParam smsDictParam) {
        return smsDictService.add(smsDictParam);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除字典")
    public boolean del(@PathVariable @ApiParam("字典id") Long id) {
        return smsDictService.del(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("修改字典")
    public boolean update(@PathVariable @ApiParam("字典id") Long id,
                          @RequestBody @Validated @ApiParam("字典参数") SmsDictParam smsDictParam) {
        return smsDictService.update(id, smsDictParam);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询，可搜索")
    public PageVO<SmsDict> page(@RequestParam(value = "pageNum", defaultValue = "1") @Min(value = 1, message = "页数最小为1") @ApiParam("当前页数") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "5") @Min(value = 1, message = "每页条数最小为1") @ApiParam("每页条数") Integer pageSize,
                                @RequestParam(value = "code", required = false) @ApiParam("用户名") String code,
                                @RequestParam(value = "name", required = false) @ApiParam("启用状态") String name) {
        return smsDictService.page(pageNum, pageSize, code, name);
    }

}
