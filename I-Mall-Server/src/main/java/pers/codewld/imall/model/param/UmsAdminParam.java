package pers.codewld.imall.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import pers.codewld.imall.config.ValidatorConfig;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <p>
 * UmsAdmin参数类
 * </p>
 *
 * @author codewld
 * @since 2022-02-21
 */
@Data
@ApiModel(value = "UmsAdminParam对象", description = "UmsAdmin参数类")
public class UmsAdminParam {

    @NotBlank(groups = ValidatorConfig.Group.add.class, message = "用户名不能为空")
    @Size(min = 4, max = 10, message = "用户名长度应在4-10之间")
    private String username;

    @NotBlank(groups = ValidatorConfig.Group.add.class, message = "密码不能为空")
    @Size(min = 5, max = 10, message = "密码长度应在5-10之间")
    private String password;

    @ApiModelProperty("头像")
    private String icon;

    @Email
    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("备注")
    private String note;

    @ApiModelProperty("启用状态")
    private Boolean status;

}
