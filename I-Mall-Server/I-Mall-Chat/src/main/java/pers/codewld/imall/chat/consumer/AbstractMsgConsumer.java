package pers.codewld.imall.chat.consumer;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import pers.codewld.imall.chat.model.entity.User;
import pers.codewld.imall.chat.model.enums.SystemCode;
import pers.codewld.imall.chat.model.message.queue.CommunicationMsg;
import pers.codewld.imall.chat.model.message.queue.UnreadCountMsg;
import pers.codewld.imall.chat.model.message.queue.UnreadMsgMsg;
import pers.codewld.imall.chat.model.message.websocket.WebSocketMsg;
import pers.codewld.imall.chat.server.BaseWebSocketServer;
import pers.codewld.imall.chat.util.ConfigUtil;
import pers.codewld.imall.common.util.RedisUtil;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * <p>
 * 消息 消费者抽象类
 * </p>
 *
 * @author codewld
 * @since 2022-03-25
 */
@Slf4j
public abstract class AbstractMsgConsumer {

    /**
     * 当前所属系统
     */
    protected abstract SystemCode getSystemCode();

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    ConfigUtil configUtil;

    @PostConstruct
    public void init() {
        redisUtil.brPopLPush(
                configUtil.getPOST_QUEUE_PREFIX() + "-" + getSystemCode().getName(),
                configUtil.getPOST_QUEUE_PREFIX() + "-" + getSystemCode().getName() + "-" + "back",
                o -> {
                    System.out.println(o);
                    if (o instanceof CommunicationMsg) {
                        CommunicationMsg communicationMsg = (CommunicationMsg) o;
                        WebSocketMsg webSocketMsg = new WebSocketMsg();
                        webSocketMsg.setType("msg");
                        JSONObject data = new JSONObject();
                        data.put("msg", communicationMsg.getMsg());
                        webSocketMsg.setData(data.toJSONString());
                        sendMsg(communicationMsg.getRecipient(), webSocketMsg);
                    } else if (o instanceof UnreadCountMsg) {
                        UnreadCountMsg unreadCountMsg = (UnreadCountMsg) o;
                        WebSocketMsg webSocketMsg = new WebSocketMsg();
                        webSocketMsg.setType("unreadCount");
                        JSONObject data = new JSONObject();
                        data.put("count", unreadCountMsg.getCount());
                        webSocketMsg.setData(data.toJSONString());
                        sendMsg(unreadCountMsg.getRecipient(), webSocketMsg);
                    } else if (o instanceof UnreadMsgMsg) {
                        UnreadMsgMsg unreadMsgMsg = (UnreadMsgMsg) o;
                        WebSocketMsg webSocketMsg = new WebSocketMsg();
                        webSocketMsg.setType("unreadMsg");
                        JSONObject data = new JSONObject();
                        data.put("msgList", unreadMsgMsg.getMsgList());
                        webSocketMsg.setData(data.toJSONString());
                        sendMsg(unreadMsgMsg.getRecipient(), webSocketMsg);
                    }
                }
        );
    }

    /**
     * 发送消息
     *
     * @param user         接收者
     * @param webSocketMsg 消息
     */
    private void sendMsg(User user, WebSocketMsg webSocketMsg) {
        BaseWebSocketServer webSocketServer = BaseWebSocketServer.connections.get(String.valueOf(user.getId()));
        try {
            webSocketServer.sendMessage(JSONObject.toJSONString(webSocketMsg));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
