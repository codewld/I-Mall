package pers.codewld.imall.admin.webSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.codewld.imall.chat.consumer.AbstractMsgConsumer;
import pers.codewld.imall.chat.model.enums.SystemCode;

/**
 * <p>
 * 消息 消费者实现类
 * </p>
 *
 * @author codewld
 * @since 2022-03-25
 */
@Component
public class MsgConsumer extends AbstractMsgConsumer {

    @Autowired
    WebSocketServer webSocketServer;

    @Override
    protected SystemCode getSystemCode() {
        return SystemCode.ADMIN;
    }

}
