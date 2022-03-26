package pers.codewld.imall.chat.server.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.codewld.imall.chat.model.entity.User;
import pers.codewld.imall.chat.model.message.queue.MsgMsg;
import pers.codewld.imall.chat.model.message.queue.UserStatusMsg;
import pers.codewld.imall.chat.server.service.MsgService;
import pers.codewld.imall.chat.server.util.ConfigUtil;
import pers.codewld.imall.chat.util.TransformUtil;
import pers.codewld.imall.common.util.RedisUtil;

import javax.annotation.PostConstruct;

/**
 * <p>
 * 消息 消费者类
 * </p>
 *
 * @author codewld
 * @since 2022-03-25
 */
@Component
public class MsgConsumer {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    ConfigUtil configUtil;

    @Autowired
    MsgService msgService;

    @PostConstruct
    public void init() {
        redisUtil.brPopLPush(
                configUtil.getPRE_QUEUE(),
                configUtil.getPRE_QUEUE() + "-" + "back",
                o -> {
                    if (o instanceof UserStatusMsg) {
                        handleUserStatus((UserStatusMsg) o);
                    } else if (o instanceof MsgMsg) {
                        handleCommunication((MsgMsg) o);
                    }
                });
    }

    /**
     * 处理用户的在线状态
     */
    void handleUserStatus(UserStatusMsg userStatusMsg) {
        User user = userStatusMsg.getUser();
        String userStr = TransformUtil.transform(user);
        if (userStatusMsg.getOnline()) { // 如果在线
            if (userStatusMsg.getActive()) { // 如果活跃
                User contact = userStatusMsg.getContact();
                String contactStr = TransformUtil.transform(contact);
                redisUtil.hSet(
                        configUtil.getUSER_STATUS_HASH(),
                        userStr,
                        contactStr != null ? contactStr : "__ACTIVE__", // 存在交流者时，记录交流者；否则记录状态为激活
                        0);
                msgService.sendUnreadMsg(user);
            } else { // 如果不活跃
                redisUtil.hSet(
                        configUtil.getUSER_STATUS_HASH(),
                        userStr,
                        "__ONLINE__",
                        0);
                msgService.sendUnreadCount(user);
            }
        } else { // 如果不在线
            redisUtil.hDel(
                    configUtil.getUSER_STATUS_HASH(),
                    userStr);
        }
    }

    /**
     * 处理通信
     */
    void handleCommunication(MsgMsg msgMsg) {
        User recipient = msgMsg.getRecipient();
        String senderStr = TransformUtil.transform(msgMsg.getSender());
        String recipientStr = TransformUtil.transform(recipient);
        // Redis 中查询接收者状态
        String recipientStatus = (String) redisUtil.hGet(
                configUtil.getUSER_STATUS_HASH(),
                recipientStr);
        if (recipientStatus == null) { // 接收者离线
            msgService.addUnreadMsg(msgMsg);
        } else if (recipientStatus.equals("__ONLINE__")) { // 接收者在线
            msgService.addUnreadMsg(msgMsg);
            msgService.sendUnreadCount(recipient);
        } else if (recipientStatus.equals("__ACTIVE__") || !recipientStatus.equals(senderStr)) { // 接收者活跃或接收者正在与其它用户对话
            msgService.addUnreadMsg(msgMsg);
            msgService.sendMsg(msgMsg);
        } else { // 接收者正在与发送者对话
            msgService.sendMsg(msgMsg);
        }
    }
}
