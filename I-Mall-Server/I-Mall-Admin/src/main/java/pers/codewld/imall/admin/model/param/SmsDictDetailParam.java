package pers.codewld.imall.admin.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import pers.codewld.imall.common.config.ValidatorConfig;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

/**
 * <p>
 * UmsDictDetail参数类
 * </p>
 *
 * @author codewld
 * @since 2022-03-13
 */
@Data
@ApiModel(value = "SmsDictDetailParam对象", description = "UmsDictDetail参数类")
public class SmsDictDetailParam {

    @Null(groups = ValidatorConfig.Group.update.class, message = "不允许修改字典ID")
    @ApiModelProperty("字典ID")
    private Long dictId;

    @NotBlank(groups = ValidatorConfig.Group.add.class, message = "值不能为空")
    @ApiModelProperty("值")
    private String value;

    @NotBlank(groups = ValidatorConfig.Group.add.class, message = "名不能为空")
    @ApiModelProperty("名")
    private String label;

}
