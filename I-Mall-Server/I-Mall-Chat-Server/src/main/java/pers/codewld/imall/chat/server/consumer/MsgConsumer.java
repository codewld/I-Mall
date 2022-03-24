package pers.codewld.imall.chat.server.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.codewld.imall.chat.model.entity.User;
import pers.codewld.imall.chat.model.message.CommunicationMsg;
import pers.codewld.imall.chat.model.message.UserStatusMsg;
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
        String idStr = String.valueOf(user.getId());
        String userStatusHashAdmin = configUtilPlus.getUSER_STATUS_HASH_PREFIX() + "-" + user.getSystem().getName();
        if (userStatusMsg.getOnline()) { // 如果在线
            if (userStatusMsg.getActive()) { // 如果活跃
                String contactId = null;
                User contact = userStatusMsg.getContact();
                if (contact != null) {
                    contactId = String.valueOf(contact.getId());
                }
                // 存在交流者时，记录交流者；否则记录状态为激活
                contactId = contactId != null ? contactId : "__ACTIVE__";
                redisUtil.hSet(
                        userStatusHashAdmin,
                        idStr,
                        contactId,
                        0);
            } else { // 如果不活跃
                redisUtil.hSet(
                        userStatusHashAdmin,
                        idStr,
                        "__ONLINE__",
                        0);
            }
        } else { // 如果不在线
            redisUtil.hDel(
                    userStatusHashAdmin,
                    idStr);
        }
    }

}
