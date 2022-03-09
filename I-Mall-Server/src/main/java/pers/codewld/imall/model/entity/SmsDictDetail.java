package pers.codewld.imall.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 字典详情
 * </p>
 *
 * @author codewld
 * @since 2022-03-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sms_dict_detail")
@ApiModel(value = "SmsDictDetail对象", description = "字典详情")
public class SmsDictDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("字典ID")
    private Long dictId;

    @ApiModelProperty("值")
    private String value;

    @ApiModelProperty("名")
    private String label;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

}
