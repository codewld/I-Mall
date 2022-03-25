package pers.codewld.imall.chat.server.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.codewld.imall.chat.model.entity.User;
import pers.codewld.imall.chat.model.message.queue.CommunicationMsg;
import pers.codewld.imall.chat.model.message.queue.UnreadCountMsg;
import pers.codewld.imall.chat.model.message.queue.UserStatusMsg;
import pers.codewld.imall.chat.server.service.MsgService;
import pers.codewld.imall.chat.server.util.ConfigUtilPlus;
import pers.codewld.imall.chat.server.util.TransformUtil;
import pers.codewld.imall.common.util.RedisUtil;

import javax.annotation.PostConstruct;

@Component
public class MsgConsumer {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    ConfigUtilPlus configUtilPlus;

    @Autowired
    MsgService msgService;

    @PostConstruct
    public void init() {
        redisUtil.brPopLPush(
                configUtilPlus.getPRE_QUEUE(),
                configUtilPlus.getPRE_QUEUE_BACK(),
                o -> {
                    if (o instanceof UserStatusMsg) {
                        handleUserStatus((UserStatusMsg) o);
                    } else if (o instanceof CommunicationMsg) {
                        handleCommunication((CommunicationMsg) o);
                    }
                });
    }

    /**
     * 处理用户的在线状态
     */
    void handleUserStatus(UserStatusMsg userStatusMsg) {
        String userStr = TransformUtil.transform(userStatusMsg.getUser());
        if (userStatusMsg.getOnline()) { // 如果在线
            if (userStatusMsg.getActive()) { // 如果活跃
                // 存在交流者时，记录交流者；否则记录状态为激活
                String contactStr = "__ACTIVE__";
                User contact = userStatusMsg.getContact();
                if (contact != null) {
                    contactStr = TransformUtil.transform(contact);
                }
                redisUtil.hSet(
                        configUtilPlus.getUSER_STATUS_HASH(),
                        userStr,
                        contactStr,
                        0);
            } else { // 如果不活跃
                redisUtil.hSet(
                        configUtilPlus.getUSER_STATUS_HASH(),
                        userStr,
                        "__ONLINE__",
                        0);
            }
        } else { // 如果不在线
            redisUtil.hDel(
                    configUtilPlus.getUSER_STATUS_HASH(),
                    userStr);
        }
    }

    /**
     * 处理通信
     */
    void handleCommunication(CommunicationMsg communicationMsg) {
        User recipient = communicationMsg.getRecipient();
        String senderStr = TransformUtil.transform(communicationMsg.getSender());
        String recipientStr = TransformUtil.transform(recipient);
        // Redis 中查询接收者状态
        String recipientStatus = (String) redisUtil.hGet(
                configUtilPlus.getUSER_STATUS_HASH(),
                recipientStr);
        if (recipientStatus == null) { // 接收者离线
            msgService.addUnreadMsg(TransformUtil.transform(communicationMsg));
        } else if (recipientStatus.equals("__ONLINE__")) { // 接收者在线
            msgService.addUnreadMsg(TransformUtil.transform(communicationMsg));
            // 发送未读消息数
            redisUtil.lPush(getPostQueue(recipient), new UnreadCountMsg(recipient, msgService.countUnreadMsg(recipientStr)), 0);
        } else if (recipientStatus.equals("__ACTIVE__") || !recipientStatus.equals(senderStr)) { // 接收者活跃或接收者正在与其它用户对话
            msgService.addUnreadMsg(TransformUtil.transform(communicationMsg));
            // 发送消息
            redisUtil.lPush(getPostQueue(recipient), communicationMsg, 0);
        } else { // 接收者正在与发送者对话
            // 发送消息
            redisUtil.lPush(getPostQueue(recipient), communicationMsg, 0);
        }
    }

    /**
     * 获取用户对应的处理后队列PostQueue
     * @param user
     * @return
     */
    private String getPostQueue(User user) {
        return configUtilPlus.getPOST_QUEUE_PREFIX() + "-" + user.getSystem().getName();
    }

}
