package pers.codewld.imall.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 后台角色标记 VO类
 * </p>
 *
 * @author codewld
 * @since 2022-02-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "UmsRoleMarkVO对象", description = "后台角色标记 VO类")
public class UmsRoleMarkVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("名称")
    private String name;

}
