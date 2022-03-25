package pers.codewld.imall.chat.model.message.queue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.codewld.imall.chat.model.entity.User;

/**
 * <p>
 * 未读消息数量 消息类
 * </p>
 *
 * @author codewld
 * @since 2022-03-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnreadCountMsg {

    /**
     * 接收者
     */
    private User recipient;

    /**
     * 未读消息数量
     */
    private Long count;

}
