package pers.codewld.imall.chat.model.message.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * websocket 消息类
 * </p>
 *
 * <p>
 * 用于前后端websocket通信
 * </p>
 *
 * @author codewld
 * @since 2022-03-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketMsg {

    /**
     * 通信类型
     */
    String type;

    /**
     * 数据
     */
    String data;

}
