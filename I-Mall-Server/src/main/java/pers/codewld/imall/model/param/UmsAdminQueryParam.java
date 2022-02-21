package pers.codewld.imall.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * <p>
 * UmsAdmin查询参数类
 * </p>
 *
 * @author codewld
 * @since 2022-02-21
 */
@Data
@ApiModel(value = "UmsAdminQueryParam", description = "UmsAdmin查询参数对象")
public class UmsAdminQueryParam {

    @Size(min = 4, max = 10, message = "用户名长度应在4-10之间")
    private String username;

    @Email
    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("启用状态")
    private Boolean status;
}
