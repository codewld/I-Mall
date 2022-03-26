package pers.codewld.imall.chat.util;

import pers.codewld.imall.chat.model.entity.User;
import pers.codewld.imall.chat.model.enums.SystemCode;

/**
 * <p>
 * 结构体转换 工具类
 * </p>
 * <p>
 * 描述：帮助结构体之间进行转换 [entity -> VO; param -> entity]
 * </p>
 *
 * @author codewld
 * @since 2022-03-25
 */
public class TransformUtil {

    public static String transform(User user) {
        if (user == null) {
            return null;
        }
        return user.getSystem() + "_" + user.getId();
    }

    public static User transform(String str) {
        try {
            int index = str.indexOf("_");
            User user = new User();
            user.setSystem(SystemCode.valueOf(str.substring(0, index)));
            user.setId(Long.valueOf(str.substring(index + 1)));
            return user;
        } catch (Exception e) {
            return null;
        }
    }

}
