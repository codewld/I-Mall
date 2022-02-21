package pers.codewld.imall.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 * UmsAdmin参数对象
 * </p>
 *
 * @author codewld
 * @since 2022-02-21
 */
@Data
@ApiModel(value = "UmsAdminParam对象", description = "UmsAdmin参数对象")
public class UmsAdminParam {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
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
