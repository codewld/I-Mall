package pers.codewld.imall.admin.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 字典
 * </p>
 *
 * @author codewld
 * @since 2022-03-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sms_dict")
@ApiModel(value = "SmsDict对象", description = "字典")
public class SmsDict implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("备注")
    private String note;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

}
