package pers.codewld.imall.admin.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 * 后台菜单标记 VO类
 * </p>
 *
 * @author codewld
 * @since 2022-02-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "UmsMenuMarkVO对象", description = "后台菜单标记 VO类")
public class UmsMenuMarkVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("子菜单列表")
    private List<UmsMenuMarkVO> children;

}
