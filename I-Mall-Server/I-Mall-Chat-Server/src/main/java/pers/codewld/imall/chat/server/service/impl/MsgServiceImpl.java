package pers.codewld.imall.chat.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.codewld.imall.chat.model.entity.User;
import pers.codewld.imall.chat.model.message.queue.CommunicationMsg;
import pers.codewld.imall.chat.model.message.queue.UnreadCountMsg;
import pers.codewld.imall.chat.server.model.entity.Msg;
import pers.codewld.imall.chat.server.repository.MsgRepository;
import pers.codewld.imall.chat.server.service.MsgService;
import pers.codewld.imall.chat.server.util.ConfigUtilPlus;
import pers.codewld.imall.chat.server.util.TransformUtil;
import pers.codewld.imall.common.util.RedisUtil;

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

    @Autowired
    ConfigUtilPlus configUtilPlus;

    @Autowired
    MsgRepository msgRepository;

    @Override
    public void addUnreadMsg(Msg msg) {
        msgRepository.addUnreadMsg(msg);
    }

    @Override
    public void sendUnReadMsg(User recipient) {
        redisUtil.lPush(
                getPostQueue(recipient),
                new UnreadCountMsg(recipient, msgRepository.countUnreadMsg(TransformUtil.transform(recipient))),
                0);
    }

    @Override
    public void sendCommunicationMsg(CommunicationMsg communicationMsg) {
        redisUtil.lPush(
                getPostQueue(communicationMsg.getRecipient()),
                communicationMsg,
                0);
    }

    /**
     * 获取用户对应的处理后队列PostQueue
     */
    private String getPostQueue(User user) {
        return configUtilPlus.getPOST_QUEUE_PREFIX() + "-" + user.getSystem().getName();
    }
}
