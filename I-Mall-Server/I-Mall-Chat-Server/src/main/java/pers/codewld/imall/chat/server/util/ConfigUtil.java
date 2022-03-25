package pers.codewld.imall.chat.server.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 配置 工具类 扩展
 * </p>
 *
 * @author codewld
 * @since 2022-03-23
 */
@Getter
@Component("myConfigUtil")
public class ConfigUtil extends pers.codewld.imall.chat.util.ConfigUtil {

    @Value("${chat.user-status-hash}")
    String USER_STATUS_HASH;
}
