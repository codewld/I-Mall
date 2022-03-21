package pers.codewld.imall.chat.model.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import pers.codewld.imall.chat.model.entity.User;

/**
 * <p>
 * 用户在线状态 消息类
 * </p>
 *
 * @author codewld
 * @since 2022-03-21
 */
@Data
@AllArgsConstructor
public class UserStatusMsg {

    /**
     * 用户
     */
    private User user;

    /**
     * 状态
     */
    private Boolean status;

}
