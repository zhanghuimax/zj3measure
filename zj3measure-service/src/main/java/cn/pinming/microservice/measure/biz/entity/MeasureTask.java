package cn.pinming.microservice.measure.biz.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * <p>
 * 实测实量--测量部位表
 * </p>
 *
 * @author zh
 * @since 2021-04-16
 */
@TableName("d_measure_task")
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MeasureTask对象", description = "实测实量-测量任务表")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeasureTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务主键id")
    @TableId(value = "task_id",type = IdType.ASSIGN_UUID)
    private String taskId;

    @ApiModelProperty(value = "企业id")
    @TableField(fill = FieldFill.INSERT)
    private Integer companyId;

    @ApiModelProperty(value = "项目id")
    @TableField(fill = FieldFill.INSERT)
    private Integer projectId;

    @ApiModelProperty(value = "测量任务名称")
    private String taskName;

    @ApiModelProperty(value = "测量任务编号")
    private String taskNo;

    @ApiModelProperty(value = "模板主键id")
    private String templateId;

    @ApiModelProperty(value = "模板名称")
    private String templateName;

    @ApiModelProperty(value = "测量人员id")
    private String memberId;

    @ApiModelProperty(value = "测量人员名称")
    private String memberName;

    @ApiModelProperty(value = "测量类型id")
    private String typeId;

    @ApiModelProperty(value = "部位主键id")
    private String placeId;

    @ApiModelProperty(value = "测量部位")
    private String placeName;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "所属部门id")
    private Integer departmentId;

    @ApiModelProperty(value = "分包单位id")
    private Integer unitId;

    @ApiModelProperty(value = "分包单位名称")
    private String unitName;

    @ApiModelProperty(value = "测量任务状态 1指派待测 2指派进行中 3本地进行中 4待整改 5待复测 6已完成 ")
    private Byte status;

    @ApiModelProperty(value = "合格率")
    private BigDecimal passRate;

    @ApiModelProperty(value = "当前完成测量点数")
    private Integer currentPoints;

    @ApiModelProperty(value = "合格点数")
    private Integer qualifiedPoints;

    @ApiModelProperty(value = "测量点总数")
    private Integer totalPoints;

    @ApiModelProperty(value = "测量进度")
    private BigDecimal progress;

    @ApiModelProperty(value = "测量分项总数")
    private Integer totalItems;

    @ApiModelProperty(value = "提交时间")
    private Date checkTime;

    @ApiModelProperty(value = "检查分项json")
    private String checkItem;

    @ApiModelProperty(value = "检查人员")
    private String checkName;

    @ApiModelProperty(value = "检查类型")
    private Byte checkType;

    @ApiModelProperty(value = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createId;

    @ApiModelProperty(value = "创建人")
    private String createName;

    @ApiModelProperty(value = "修改人")
    @TableField(fill = FieldFill.UPDATE)
    private String modifyId;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime gmtModify;

    @ApiModelProperty(value = "是否删除：1 正常 2 删除")
    private Byte isDelete;


}
