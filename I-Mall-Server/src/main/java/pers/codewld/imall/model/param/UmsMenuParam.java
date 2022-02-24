package pers.codewld.imall.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.codewld.imall.config.ValidatorConfig;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * UmsMenu参数类
 * </p>
 *
 * @author codewld
 * @since 2022-02-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "UmsMenuParam对象", description = "UmsMenu参数类")
public class UmsMenuParam {

    @NotNull(groups = ValidatorConfig.Group.add.class, message = "父级ID不能为空")
    @ApiModelProperty("父级ID")
    private Long parentId;

    @NotNull(groups = ValidatorConfig.Group.add.class, message = "需指定是否为叶子结点")
    @ApiModelProperty("是否为叶结点")
    private Boolean leaf;

    @Min(value = 0, message = "排序应从 0 开始")
    @ApiModelProperty("排序")
    private Integer sort;

    @NotBlank(groups = ValidatorConfig.Group.add.class, message = "编号不能为空")
    @ApiModelProperty("编号")
    private String code;

    @NotBlank(groups = ValidatorConfig.Group.add.class, message = "名称不能为空")
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("组件")
    private String component;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("启用状态")
    private Boolean status;

    @ApiModelProperty("备注")
    private String note;

}
