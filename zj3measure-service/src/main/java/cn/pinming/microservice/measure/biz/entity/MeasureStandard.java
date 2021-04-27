package cn.pinming.microservice.measure.biz.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 实测实量--测量分项评判标准表
 * </p>
 *
 * @author zh
 * @since 2021-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("d_measure_standard")
@ApiModel(value="MeasureStandard对象", description="实测实量--测量分项评判标准表")
public class MeasureStandard implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评判标准主键id")
    @TableId(value = "standard_id",type = IdType.ASSIGN_UUID)
    private String standardId;

    @ApiModelProperty(value = "企业id")
    @TableField(fill = FieldFill.INSERT)
    private Integer companyId;

    @ApiModelProperty(value = "测量分项主键id")
    private String itemId;

    @ApiModelProperty(value = "评判标准名称")
    private String standardName;

    @ApiModelProperty(value = "基准值")
    private BigDecimal baseValue;

    @ApiModelProperty(value = "评判标准开始值")
    private BigDecimal judgeStartValue;

    @ApiModelProperty(value = "评判标准结束值")
    private BigDecimal judgeEndValue;

    @ApiModelProperty(value = "是否绝对值：1、是；2、否")
    private Byte isAbsoluteValue;

    @ApiModelProperty(value = "是否极差算法：1、是；2、否")
    private Byte isRangeArithmetic;

    @ApiModelProperty(value = "是否自定义基准值：1、是；2、否")
    private Byte isCustom;

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

    @ApiModelProperty(value = "是否删除：1 正常 2 删除")
    private Byte isDelete;


}
