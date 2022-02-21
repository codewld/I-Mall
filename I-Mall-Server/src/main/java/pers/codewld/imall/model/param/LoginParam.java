package pers.codewld.imall.model.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <p>
 * 登录参数
 * </p>
 *
 * @author codewld
 * @since 2022-02-06
 */
@Data
@ApiModel(value = "LoginParam对象", description = "登录参数")
public class LoginParam {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 5, max = 10, message = "用户名长度应在5-10之间")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 5, max = 10, message = "密码长度应在5-10之间")
    private String password;

}
