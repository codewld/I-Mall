package pers.codewld.imall.service;

import pers.codewld.imall.model.param.LoginParam;
import pers.codewld.imall.model.vo.RouterVO;

import java.util.List;

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

    /**
     * 获取前端路由
     */
    List<RouterVO> router();

}
