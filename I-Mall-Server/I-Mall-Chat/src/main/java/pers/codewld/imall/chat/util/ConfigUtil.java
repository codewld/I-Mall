package pers.codewld.imall.chat.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 配置 工具类
 * </p>
 *
 * @author codewld
 * @since 2022-03-21
 */
@Getter
@Component
public class ConfigUtil {

    @Value("${chat.pre-queue}")
    String PRE_QUEUE;

    @Value("${chat.post-queue-prefix}")
    String POST_QUEUE_PREFIX;
}
