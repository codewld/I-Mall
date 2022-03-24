package pers.codewld.imall.chat.model.message.queue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.codewld.imall.chat.model.entity.User;

/**
 * <p>
 * 用户状态 消息类
 * </p>
 *
 * @author codewld
 * @since 2022-03-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatusMsg {

    /**
     * 用户
     */
    private User user;

    /**
     * 是否在线
     */
    private Boolean online;

    /**
     * 是否活跃
     */
    private Boolean active;

    /**
     * 联系人
     */
    private User contact;

    public UserStatusMsg(User user, Boolean online) {
        this.user = user;
        this.online = online;
    }

    public UserStatusMsg(User user, Boolean online, Boolean active) {
        this.user = user;
        this.online = online;
        this.active = active;
    }
}
