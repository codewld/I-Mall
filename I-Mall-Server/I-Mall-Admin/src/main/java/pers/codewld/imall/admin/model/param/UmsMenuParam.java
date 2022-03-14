package pers.codewld.imall.admin.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.codewld.imall.common.config.ValidatorConfig;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @NotBlank(groups = ValidatorConfig.Group.add.class, message = "编码不能为空")
    @Size(min = 4, max = 20, message = "编码长度应在4-20之间")
    @ApiModelProperty("编码")
    private String code;

    @NotBlank(groups = ValidatorConfig.Group.add.class, message = "名称不能为空")
    @Size(min = 4, max = 20, message = "名称长度应在4-20之间")
    @ApiModelProperty("名称")
    private String name;

    @Min(value = 0, message = "排序应从 0 开始")
    @ApiModelProperty("排序")
    private Integer sort;

    @NotNull(groups = ValidatorConfig.Group.add.class, message = "需指定是否为非叶结点")
    @ApiModelProperty("是否为非叶结点")
    private Boolean nonLeaf;

    @ApiModelProperty("组件")
    private String component;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("备注")
    private String note;

}
