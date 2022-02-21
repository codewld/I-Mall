package pers.codewld.imall.util;

import pers.codewld.imall.model.entity.UmsAdmin;
import pers.codewld.imall.model.vo.UmsAdminVO;

import java.time.LocalDateTime;

/**
 * <p>
 * 结构体转换工具类
 * </p>
 * <p>
 * 描述：帮助结构体之间进行转换 [entity -> VO; param -> entity]
 * </p>
 *
 * @author codewld
 * @since 2022-02-21
 */
public class TransformUtil {

    public static UmsAdminVO transform(UmsAdmin umsAdmin) {
        Long id = umsAdmin.getId();
        String username = umsAdmin.getUsername();
        String icon = umsAdmin.getIcon();
        String email = umsAdmin.getEmail();
        String nickName = umsAdmin.getNickName();
        String note = umsAdmin.getNote();
        LocalDateTime createTime = umsAdmin.getCreateTime();
        LocalDateTime loginTime = umsAdmin.getLoginTime();
        Boolean status = umsAdmin.getStatus();
        return new UmsAdminVO(id, username, icon, email, nickName, note, createTime, loginTime, status);
    }

}
