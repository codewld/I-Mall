package pers.codewld.imall.service;

import pers.codewld.imall.model.param.LoginParam;

/**
 * <p>
 * 账户 服务类
 * </p>
 *
 * @author codewld
 * @since 2022-02-10
 */
public interface AccountService {

    /**
     * 登录
     */
    String login(LoginParam loginParam);

}
