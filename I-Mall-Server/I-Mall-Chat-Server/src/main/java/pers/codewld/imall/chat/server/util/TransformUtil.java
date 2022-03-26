package pers.codewld.imall.chat.server.util;

import pers.codewld.imall.chat.model.message.queue.CommunicationMsg;
import pers.codewld.imall.chat.server.model.entity.Msg;

/**
 * <p>
 * 结构体转换 工具类
 * </p>
 * <p>
 * 描述：帮助结构体之间进行转换 [entity -> VO; param -> entity]
 * </p>
 *
 * @author codewld
 * @since 2022-03-25
 */
public class TransformUtil extends pers.codewld.imall.chat.util.TransformUtil {

    public static Msg transform(CommunicationMsg communicationMsg) {
        Msg msg = new Msg();
        msg.setSender(TransformUtil.transform(communicationMsg.getSender()));
        msg.setRecipient(TransformUtil.transform(communicationMsg.getRecipient()));
        msg.setMsg(communicationMsg.getMsg());
        msg.setTime(communicationMsg.getTime());
        return msg;
    }

    public static CommunicationMsg transform(Msg msg) {
        CommunicationMsg communicationMsg = new CommunicationMsg();
        communicationMsg.setSender(TransformUtil.transform(msg.getSender()));
        communicationMsg.setRecipient(TransformUtil.transform(msg.getRecipient()));
        communicationMsg.setMsg(msg.getMsg());
        communicationMsg.setTime(msg.getTime());
        return communicationMsg;
    }

}
