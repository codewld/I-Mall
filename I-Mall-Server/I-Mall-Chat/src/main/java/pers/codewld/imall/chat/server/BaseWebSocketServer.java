package pers.codewld.imall.chat.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pers.codewld.imall.chat.model.entity.User;
import pers.codewld.imall.chat.model.enums.SystemCode;
import pers.codewld.imall.chat.model.message.queue.MsgMsg;
import pers.codewld.imall.chat.model.message.queue.UserStatusMsg;
import pers.codewld.imall.chat.model.message.websocket.WebSocketMsg;
import pers.codewld.imall.chat.util.ConfigUtil;
import pers.codewld.imall.common.util.BeanUtil;
import pers.codewld.imall.common.util.RedisUtil;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * WebSocket 服务抽象类
 * </p>
 *
 * @author codewld
 * @since 2022-03-21
 */
@Getter
@Slf4j
public abstract class BaseWebSocketServer {

    /**
     * 当前所属系统
     */
    protected abstract SystemCode getSystemCode();

    /**
     * 配置工具
     */
    ConfigUtil configUtil;

    public ConfigUtil getConfigUtil() {
        if (configUtil == null) {
            configUtil = BeanUtil.getBean(ConfigUtil.class);
        }
        return configUtil;
    }

    /**
     * Redis工具类
     */
    private RedisUtil redisUtil;

    private RedisUtil getRedisUtil() {
        if (redisUtil == null) {
            redisUtil = BeanUtil.getBean(RedisUtil.class);
        }
        return redisUtil;
    }

    /**
     * 处理前队列
     */
    private String getPreQueue() {
        return getConfigUtil().getPRE_QUEUE();
    }

    /**
     * 静态变量，记录所有的连接
     */
    public static final Map<String, BaseWebSocketServer> connections = new LinkedHashMap<>();

    /**
     * 与当前连接对应的Session
     */
    private Session session;

    /**
     * 当前用户
     */
    private User user;

    /**
     * 联系人
     */
    private User contact;

    /**
     * 连接成功的回调方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("id") Long id) {
        this.session = session;
        connections.put(String.valueOf(id), this);
        this.user = new User(getSystemCode(), id);
        sendMsgToPreQueue(new UserStatusMsg(user, true, false));
    }

    /**
     * 接收消息时的回调方法
     */
    @OnMessage
    public void onMessage(String message) {
        WebSocketMsg webSocketMsg = JSONObject.parseObject(message, WebSocketMsg.class);
        JSONObject data = JSONObject.parseObject(webSocketMsg.getData());
        switch (webSocketMsg.getType()) {
            // 活跃状态
            case "activeStatus":
                Boolean active = data.getBoolean("active");
                contact = null;
                sendMsgToPreQueue(new UserStatusMsg(user, true, active, null));
                break;
            // 会话建立
            case "sessionEstablish":
                contact = JSON.toJavaObject(data.getJSONObject("contact"), User.class);
                sendMsgToPreQueue(new UserStatusMsg(user, true, true, contact));
                break;
            // 发送消息
            case "sendMsg":
                String msg = data.getString("msg");
                sendMsgToPreQueue(new MsgMsg(user, contact, msg, LocalDateTime.now()));
                break;
            default:
                break;
        }
    }

    /**
     * 发生错误时的回调方法
     */
    @OnError
    public void onError(Throwable error) throws Throwable {
        log.error(error.getMessage());
        // 向消息队列中传递消息
        sendMsgToPreQueue(new UserStatusMsg(user, false));
        throw error;
    }

    /**
     * 连接关闭的回调方法
     */
    @OnClose
    public void onClose(@PathParam("id") Long id) {
        connections.remove(String.valueOf(id));
        sendMsgToPreQueue(new UserStatusMsg(user, false));
    }

    /**
     * 发送消息
     */
    public void sendMessage(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    /**
     * 向处理前队列中发送消息
     */
    private void sendMsgToPreQueue(Object msg) {
        getRedisUtil().lPush(getPreQueue(), msg, 0);
    }
}
