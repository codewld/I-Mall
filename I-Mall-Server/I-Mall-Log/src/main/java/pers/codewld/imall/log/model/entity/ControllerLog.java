package pers.codewld.imall.log.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 接口日志实体类
 * </p>
 *
 * @author codewld
 * @since 2022-03-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControllerLog {

    /**
     * URI
     */
    private String uri;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 操作用户
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
     * 操作时间
     */
    private Long startTime;

    /**
     * 消耗时间
     */
    private Long spendTime;

    /**
     * 操作描述
     */
    private String description;

    @Override
    public String toString() {
        return "uri='" + uri + '\'' +
                ", method='" + method + '\'' +
                ", username='" + username + '\'' +
                ", ip='" + ip + '\'' +
                ", parameter=" + parameter +
                ", startTime=" + startTime +
                ", spendTime=" + spendTime +
                ", description='" + description + '\'';
    }
}
