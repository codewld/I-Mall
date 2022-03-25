package pers.codewld.imall.chat.server.service;

import pers.codewld.imall.chat.server.model.entity.Msg;

/**
 * <p>
 * 信息 服务类
 * </p>
 *
 * @author codewld
 * @since 2022-03-25
 */
public interface MsgService {

    /**
     * 添加未读消息
     */
    void addUnreadMsg(Msg msg);

    /**
     * 查询接收者的未读消息数
     */
    Long countUnreadMsg(String recipient);

}
