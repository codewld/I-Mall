package pers.codewld.imall.chat.model.message.queue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.codewld.imall.chat.model.entity.User;

/**
 * <p>
 * 通信 消息类
 * </p>
 *
 * @author codewld
 * @since 2022-03-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunicationMsg {

    /**
     * 发送者
     */
    private User sender;

    /**
     * 联系者
     */
    private User contact;

    /**
     * 消息
     */
    private String msg;

}
