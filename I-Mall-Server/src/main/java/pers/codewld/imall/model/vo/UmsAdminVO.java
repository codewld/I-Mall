package pers.codewld.imall.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

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
@ApiModel(value = "UmsAdminVO对象", description = "后台用户表 VO类")
public class UmsAdminVO implements Serializable {

    private Long id;

    private String username;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("备注")
    private String note;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("最后登录时间")
    private LocalDateTime loginTime;

    @ApiModelProperty("启用状态")
    private Boolean status;

}
