package pers.codewld.imall.chat.server.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.codewld.imall.chat.model.entity.User;
import pers.codewld.imall.chat.model.message.queue.CommunicationMsg;
import pers.codewld.imall.chat.model.message.queue.UserStatusMsg;
import pers.codewld.imall.chat.server.util.ConfigUtilPlus;
import pers.codewld.imall.common.util.RedisUtil;

import javax.annotation.PostConstruct;

@Component
public class MsgConsumer {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    ConfigUtilPlus configUtilPlus;

    @PostConstruct
    public void init() {
        redisUtil.brPopLPush(
                configUtilPlus.getPRE_QUEUE(),
                configUtilPlus.getPRE_QUEUE_BACK(),
                o -> {
                    if (o instanceof UserStatusMsg) {
                        handleUserStatus((UserStatusMsg) o);
                    } else if (o instanceof CommunicationMsg) {
                        System.out.println("CommunicationMsg");
                    }
                });
    }

    /**
     * 处理用户的在线状态
     */
    void handleUserStatus(UserStatusMsg userStatusMsg) {
        User user = userStatusMsg.getUser();
        String userStr = getUserStr(user);
        if (userStatusMsg.getOnline()) { // 如果在线
            if (userStatusMsg.getActive()) { // 如果活跃
                User contact = userStatusMsg.getContact();
                String contactStr = getUserStr(contact);
                // 存在交流者时，记录交流者；否则记录状态为激活
                contactStr = contactStr != null ? contactStr : "__ACTIVE__";
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
     * 获取user的字符串表示
     */
    private String getUserStr(User user) {
        if (user == null) {
            return null;
        }
        return user.getSystem() + "_" + user.getId();
    }

}
