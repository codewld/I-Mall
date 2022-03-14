package pers.codewld.imall.admin.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.codewld.imall.admin.model.entity.SmsDict;
import pers.codewld.imall.admin.model.entity.SmsDictDetail;
import pers.codewld.imall.admin.model.param.SmsDictDetailParam;
import pers.codewld.imall.admin.model.param.SmsDictParam;
import pers.codewld.imall.admin.service.SmsDictService;
import pers.codewld.imall.common.config.ValidatorConfig;
import pers.codewld.imall.common.model.vo.PageVO;

import javax.validation.constraints.Min;
import java.util.List;

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
    public boolean add(@RequestBody @Validated(ValidatorConfig.Group.add.class) @ApiParam("字典参数") SmsDictParam smsDictParam) {
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
                                @RequestParam(value = "code", required = false) @ApiParam("编码") String code,
                                @RequestParam(value = "name", required = false) @ApiParam("名称") String name) {
        return smsDictService.page(pageNum, pageSize, code, name);
    }

    @PostMapping("/detail")
    @ApiOperation("添加字典细节")
    public boolean addDetail(@RequestBody @Validated(ValidatorConfig.Group.add.class) @ApiParam("字典细节参数") SmsDictDetailParam smsDictDetailParam) {
        return smsDictService.addDetail(smsDictDetailParam);
    }

    @DeleteMapping("/detail/{detailId}")
    @ApiOperation("删除字典细节")
    public boolean delDetail(@PathVariable @ApiParam("字典细节id") Long detailId) {
        return smsDictService.delDetail(detailId);
    }

    @PutMapping("/detail/{detailId}")
    @ApiOperation("修改字典细节")
    public boolean updateDetail(@PathVariable @ApiParam("字典细节id") Long detailId,
                                @RequestBody @Validated @ApiParam("字典细节参数") SmsDictDetailParam smsDictDetailParam) {
        return smsDictService.updateDetail(detailId, smsDictDetailParam);
    }

    @GetMapping("/detail/list")
    @ApiOperation("批量查询字典细节")
    public List<SmsDictDetail> listDetail(@RequestParam(value = "id") @ApiParam("字典id") Long id) {
        return smsDictService.listDetail(id);
    }

}
