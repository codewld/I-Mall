package pers.codewld.imall.param;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

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

    private String username;

    private String password;

}
