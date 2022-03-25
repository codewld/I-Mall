package pers.codewld.imall.chat.server.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>
 * 信息 实体类
 * </p>
 *
 * @author codewld
 * @since 2022-03-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Msg {

    @ApiModelProperty("发送者")
    private String sender;

    @ApiModelProperty("接收者")
    private String recipient;

    @ApiModelProperty("消息")
    private String msg;

    @ApiModelProperty("发送时间")
    private LocalDateTime time;

}
