package cn.pinming.microservice.measure.biz.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 实测实量-项目图纸
 * </p>
 *
 * @author zh
 * @since 2021-04-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("d_measure_drawing")
@ApiModel(value="MeasureDrawing对象", description="实测实量-项目图纸")
public class MeasureDrawing implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "drawing_id",type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "主键id")
    private String drawingId;

    @ApiModelProperty(value = "图纸名称")
    private String name;

    @ApiModelProperty(value = "企业id")
    @TableField(fill = FieldFill.INSERT)
    private Integer companyId;

    @ApiModelProperty(value = "项目id")
    @TableField(fill = FieldFill.INSERT)
    private Integer projectId;

    @ApiModelProperty(value = "所属部门id")
    private Integer departmentId;

    @ApiModelProperty(value = "所属部门名称")
    private String departmentName;

    @ApiModelProperty(value = "文件uuid")
    private String fileUuid;

    @ApiModelProperty(value = "创建人id")
    @TableField(fill = FieldFill.INSERT)
    private String createId;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "修改人id")
    @TableField(fill = FieldFill.UPDATE)
    private String modifyId;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime gmtModify;

    @ApiModelProperty(value = "删除状态  1 正常 2 删除")
    @TableLogic(value = "1",delval = "2")
    private Byte isDelete;
}
