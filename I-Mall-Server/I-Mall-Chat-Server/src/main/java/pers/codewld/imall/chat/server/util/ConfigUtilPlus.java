package pers.codewld.imall.chat.server.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pers.codewld.imall.chat.util.ConfigUtil;

/**
 * <p>
 * 配置 工具类 扩展
 * </p>
 *
 * @author codewld
 * @since 2022-03-23
 */
@Getter
@Component
public class ConfigUtilPlus extends ConfigUtil {

    @Value("${chat.pre-queue-back}")
    String PRE_QUEUE_BACK;

    @Value("${chat.user-status-hash}")
    String USER_STATUS_HASH;
}
