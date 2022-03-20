package pers.codewld.imall.admin.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 后台用户表 VO类
 * </p>
 *
 * @author codewld
 * @since 2022-02-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "UmsAdminVO对象", description = "后台用户表 VO类")
public class UmsAdminVO implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("启用状态")
    private Boolean status;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("备注")
    private String note;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("最后登录时间")
    private LocalDateTime loginTime;

}
