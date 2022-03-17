package pers.codewld.imall.log.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * <p>
 * 日志 实体类
 * </p>
 *
 * @author codewld
 * @since 2022-03-16
 */
@Document("log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {

    @ApiModelProperty("接口概述")
    private String summary;

    @ApiModelProperty("执行状态")
    private Boolean status;

    @ApiModelProperty("URI")
    private String uri;

    @ApiModelProperty("请求方法")
    private String method;

    @ApiModelProperty("操作用户")
    private String username;

    @ApiModelProperty("IP地址")
    private String ip;

    @ApiModelProperty("请求参数")
    private String parameter;

    @ApiModelProperty("操作时间")
    private LocalDateTime time;

    @ApiModelProperty("操作耗时")
    private Long spendTime;

}
