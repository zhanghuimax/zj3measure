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
 * 实测指标--测量任务模板
 * </p>
 *
 * @author zh
 * @since 2021-04-22
 */

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("d_measure_task_template")
@ApiModel(value="MeasureTaskTemplate对象", description="实测指标--测量任务模板")
public class MeasureTaskTemplate implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "template_id",type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "主键id")
    private String templateId;

    @ApiModelProperty(value = "企业id")
    @TableField(fill = FieldFill.INSERT)
    private Integer companyId;

    @ApiModelProperty(value = "一级名称")
    private String oneName;

    @ApiModelProperty(value = "二级名称")
    private String twoName;

    @ApiModelProperty(value = "所属单位")
    private String orgName;

    @ApiModelProperty(value = "测量类型id")
    private String typeId;

    @ApiModelProperty(value = "模板通用设置json (禁止收工录入、禁止删除数据等...)")
    private String setting;

    @ApiModelProperty(value = "检查分项数量")
    private Integer checkItemCount;

    @ApiModelProperty(value = "测量部位数量")
    private Integer measurePositionCount;

    @ApiModelProperty(value = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createId;

    @ApiModelProperty(value = "修改人")
    @TableField(fill = FieldFill.UPDATE)
    private String modifyId;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime gmtModify;

    @ApiModelProperty(value = "是否删除：1、正常；2、删除")
    private Byte isDelete;

    @ApiModelProperty(value = "是否启用（1.启用,2.禁用）")
    private Byte enabled;
}
