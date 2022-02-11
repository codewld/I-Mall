package pers.codewld.imall.service;

import pers.codewld.imall.param.LoginParam;

/**
 * <p>
 * 后台账户 服务类
 * </p>
 *
 * @author codewld
 * @since 2022-02-10
 */
public interface AdminAccountService {

    /**
     * 登录
     */
    String login(LoginParam loginParam);

}
