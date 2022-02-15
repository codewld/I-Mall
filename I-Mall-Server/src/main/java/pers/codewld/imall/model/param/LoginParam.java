package pers.codewld.imall.model.param;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p>
 * 登录参数
 * </p>
 *
 * @author codewld
 * @since 2022-02-06
 */
@Getter
@Setter
@ApiModel(value = "LoginParam对象", description = "登录参数")
public class LoginParam {

    @Size(min = 5, max = 10, message = "用户名长度应在5-10之间")
    private String username;

    @NotNull(message = "密码不能为空")
    private String password;

}
