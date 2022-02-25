package pers.codewld.imall.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 后台菜单
 * </p>
 *
 * @author codewld
 * @since 2022-02-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ums_menu")
@ApiModel(value = "UmsMenu对象", description = "后台菜单")
public class UmsMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("父级ID")
    private Long parentId;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("是否为非叶结点")
    private Boolean nonLeaf;

    @ApiModelProperty("组件")
    private String component;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("备注")
    private String note;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

}
