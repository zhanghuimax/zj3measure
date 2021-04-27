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
 * 实测实量--测量部位表
 * </p>
 *
 * @author admin
 * @since 2020-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("d_measure_place")
@ApiModel(value = "MeasurePlace对象", description = "实测实量-测量部位表")
public class MeasurePlace implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部位主键id")
    @TableId(value = "place_id",type = IdType.ASSIGN_UUID)
    private String placeId;

    @ApiModelProperty(value = "企业id")
    @TableField(fill = FieldFill.INSERT)
    private Integer companyId;

    @ApiModelProperty(value = "项目id")
    @TableField(fill = FieldFill.INSERT)
    private Integer projectId;

    @ApiModelProperty(value = "区域")
    private String placeOne;

    @ApiModelProperty(value = "楼栋")
    private String placeTwo;

    @ApiModelProperty(value = "单元")
    private String placeThree;

    @ApiModelProperty(value = "楼层")
    private String placeFour;

    @ApiModelProperty(value = "部位")
    private String placeFive;

    @ApiModelProperty(value = "图纸名称")
    private String drawingName;

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
    @TableLogic(value = "1",delval = "2")
//    和服务实现类的 deleteMeasurePlace 搭配
    private Byte isDelete;


}
