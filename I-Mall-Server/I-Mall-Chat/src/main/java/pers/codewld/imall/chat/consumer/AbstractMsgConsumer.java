package pers.codewld.imall.chat.consumer;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import pers.codewld.imall.chat.model.entity.User;
import pers.codewld.imall.chat.model.enums.SystemCode;
import pers.codewld.imall.chat.model.message.queue.MsgMsg;
import pers.codewld.imall.chat.model.message.queue.UnreadCountMsg;
import pers.codewld.imall.chat.model.message.queue.MsgListMsg;
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
                    if (o instanceof UnreadCountMsg) {
                        UnreadCountMsg unreadCountMsg = (UnreadCountMsg) o;
                        JSONObject data = new JSONObject();
                        data.put("count", unreadCountMsg.getCount());
                        sendMsg(unreadCountMsg.getRecipient(), "unreadCount", data);
                    } else if (o instanceof MsgListMsg) {
                        MsgListMsg msgListMsg = (MsgListMsg) o;
                        JSONObject data = new JSONObject();
                        data.put("list", msgListMsg.getList());
                        sendMsg(msgListMsg.getRecipient(), "msg", data);
                    }
                }
        );
    }

    /**
     * 发送消息
     *
     * @param user 接收者
     * @param type 通信类型
     * @param data 数据
     */
    private void sendMsg(User user, String type, JSONObject data) {
        WebSocketMsg webSocketMsg = new WebSocketMsg(type, data.toJSONString());
        BaseWebSocketServer webSocketServer = BaseWebSocketServer.connections.get(String.valueOf(user.getId()));
        try {
            webSocketServer.sendMessage(JSONObject.toJSONString(webSocketMsg));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
