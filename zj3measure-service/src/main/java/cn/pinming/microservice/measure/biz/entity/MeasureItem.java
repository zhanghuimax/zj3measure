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
 * 实测实量--测量分项表
 * </p>
 *
 * @author zh
 * @since 2021-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("d_measure_item")
@ApiModel(value="MeasureItem对象", description="实测实量--测量分项表")
public class MeasureItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "测量分项主键id")
    @TableId(value = "item_id",type = IdType.ASSIGN_UUID)
    private String itemId;

    @ApiModelProperty(value = "企业id")
    @TableField(fill = FieldFill.INSERT)
    private Integer companyId;

    @ApiModelProperty(value = "任务模板主键id")
    private String templateId;

    @ApiModelProperty(value = "测量类型主键id")
    private String typeId;

    @ApiModelProperty(value = "检查方法描述")
    private String inspectMethodDescribe;

    @ApiModelProperty(value = "引用规范描述")
    private String citeNormDescribe;

    @ApiModelProperty(value = "测量区数量")
    private Integer measureAreaCount;

    @ApiModelProperty(value = "每区测量点数量")
    private Integer areaPointCount;

    @ApiModelProperty(value = "是否禁止手工录入：1 禁止 2 非禁止")
    private Byte isForbidden;

    @ApiModelProperty(value = "禁止删除数值：1 禁止 2 非禁止")
    private Byte isManualDelete;

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
