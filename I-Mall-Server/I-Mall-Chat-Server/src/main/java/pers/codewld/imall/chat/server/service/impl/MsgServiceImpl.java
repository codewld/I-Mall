package pers.codewld.imall.chat.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pers.codewld.imall.chat.model.entity.User;
import pers.codewld.imall.chat.model.message.queue.MsgMsg;
import pers.codewld.imall.chat.model.message.queue.UnreadCountMsg;
import pers.codewld.imall.chat.model.message.queue.MsgListMsg;
import pers.codewld.imall.chat.server.repository.MsgRepository;
import pers.codewld.imall.chat.server.service.MsgService;
import pers.codewld.imall.chat.server.util.ConfigUtil;
import pers.codewld.imall.common.util.RedisUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 信息 服务实现类
 * </p>
 *
 * @author codewld
 * @since 2022-03-25
 */
@Service
public class MsgServiceImpl implements MsgService {

    @Autowired
    RedisUtil redisUtil;

    @Qualifier("myConfigUtil")
    @Autowired
    ConfigUtil configUtil;

    @Autowired
    MsgRepository msgRepository;

    @Override
    public void addUnreadMsg(MsgMsg msgMsg) {
        msgRepository.addUnreadMsg(msgMsg);
    }

    @Override
    public void sendUnreadCount(User recipient) {
        Long unreadMsgCount = msgRepository.countUnreadMsg(recipient);
        redisUtil.lPush(
                getPostQueue(recipient),
                new UnreadCountMsg(recipient, unreadMsgCount),
                0);
    }

    @Override
    public void sendUnreadMsg(User user) {
        List<MsgMsg> unreadMsgList = msgRepository.listUnreadMsg(user);
        sendMsgList(user, unreadMsgList);
    }

    @Override
    public void sendMsg(MsgMsg msgMsg) {
        List<MsgMsg> list = new ArrayList<>();
        list.add(msgMsg);
        sendMsgList(msgMsg.getRecipient(), list);
    }

    /**
     * 发送消息列表
     */
    private void sendMsgList(User recipient, List<MsgMsg> list) {
        redisUtil.lPush(
                getPostQueue(recipient),
                new MsgListMsg(recipient, list),
                0
        );
    }

    /**
     * 获取用户对应的处理后队列PostQueue
     */
    private String getPostQueue(User user) {
        return configUtil.getPOST_QUEUE_PREFIX() + "-" + user.getSystem().getName();
    }
}
