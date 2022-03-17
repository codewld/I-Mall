package pers.codewld.imall.log.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * <p>
 * 接口日志实体类
 * </p>
 *
 * @author codewld
 * @since 2022-03-16
 */
@Document("log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControllerLog {

    /**
     * 接口概述
     */
    private String summary;

    /**
     * URI
     */
    private String uri;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 用户名
     */
    private String username;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 请求参数
     */
    private String parameter;

    /**
     * 时间
     */
    private LocalDateTime time;

    /**
     * 消耗时间
     */
    private Long spendTime;

    /**
     * 操作成功
     */
    private Boolean success;
}
