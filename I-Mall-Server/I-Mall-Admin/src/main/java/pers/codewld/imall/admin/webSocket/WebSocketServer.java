package pers.codewld.imall.admin.webSocket;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pers.codewld.imall.chat.model.enums.SystemCode;
import pers.codewld.imall.chat.server.BaseWebSocketServer;

import javax.websocket.server.ServerEndpoint;

/**
 * <p>
 * WebSocket 服务实现类
 * </p>
 *
 * @author codewld
 * @since 2022-03-21
 */
@Getter
@Slf4j
@Component
@ServerEndpoint("/websocket/{id}")
public class WebSocketServer extends BaseWebSocketServer {

    @Override
    protected SystemCode getSystemCode() {
        return SystemCode.ADMIN;
    }

}
