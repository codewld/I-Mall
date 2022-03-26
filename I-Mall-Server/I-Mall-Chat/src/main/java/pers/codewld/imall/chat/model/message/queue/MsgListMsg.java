package pers.codewld.imall.chat.model.message.queue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.codewld.imall.chat.model.entity.User;

import java.util.List;

/**
 * <p>
 * 消息列表 消息类
 * </p>
 *
 * @author codewld
 * @since 2022-03-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgListMsg {

    /**
     * 接收者
     */
    private User recipient;

    /**
     * 消息列表
     */
    private List<MsgMsg> list;

}
