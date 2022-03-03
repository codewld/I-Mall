package pers.codewld.imall.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 * 路由 VO类
 * </p>
 *
 * <p>
 * 前端解析后生成router及菜单
 * </p>
 *
 * @author codewld
 * @since 2022-03-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "UmsRoleMarkVO对象", description = "后台角色标记 VO类")
public class RouterVO {

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("组件")
    private String component;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("子路由列表")
    private List<RouterVO> children;

}
