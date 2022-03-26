package pers.codewld.imall.chat.server.service;

import pers.codewld.imall.chat.model.entity.User;
import pers.codewld.imall.chat.model.message.queue.MsgMsg;

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
    void addUnreadMsg(MsgMsg msgMsg);

    /**
     * 发送未读信息数
     */
    void sendUnreadCount(User recipient);

    /**
     * 发送未读消息
     */
    void sendUnreadMsg(User recipient);

    /**
     * 发送消息
     */
    void sendMsg(MsgMsg msgMsg);

}
