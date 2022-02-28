package pers.codewld.imall.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.codewld.imall.config.ValidatorConfig;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <p>
 * UmsRole参数类
 * </p>
 *
 * @author codewld
 * @since 2022-02-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "UmsRoleParam对象", description = "UmsRole参数类")
public class UmsRoleParam {

    @NotBlank(groups = ValidatorConfig.Group.add.class, message = "编码不能为空")
    @Size(min = 4, max = 20, message = "编码长度应在4-20之间")
    @ApiModelProperty("编码")
    private String code;

    @NotBlank(groups = ValidatorConfig.Group.add.class, message = "名称不能为空")
    @Size(min = 4, max = 20, message = "用户名长度应在4-20之间")
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("备注")
    private String note;

}
