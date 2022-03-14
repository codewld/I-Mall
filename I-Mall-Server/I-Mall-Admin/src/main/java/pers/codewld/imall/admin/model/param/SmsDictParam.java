package pers.codewld.imall.admin.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import pers.codewld.imall.common.config.ValidatorConfig;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <p>
 * UmsDict参数类
 * </p>
 *
 * @author codewld
 * @since 2022-03-09
 */
@Data
@ApiModel(value = "SmsDictParam对象", description = "UmsDict参数类")
public class SmsDictParam {

    @NotBlank(groups = ValidatorConfig.Group.add.class, message = "编码不能为空")
    @Size(min = 2, max = 10, message = "编码长度应在2-10之间")
    @ApiModelProperty("编码")
    private String code;

    @NotBlank(groups = ValidatorConfig.Group.add.class, message = "名称不能为空")
    @Size(min = 2, max = 10, message = "名称长度应在2-10之间")
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("备注")
    private String note;

}
