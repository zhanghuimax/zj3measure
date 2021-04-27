package cn.pinming.microservice.measure.biz.vo;

import cn.pinming.microservice.measure.biz.entity.MeasureTask;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 任务模板VO
 *
 * @author zh
 */
@Data
public class MeasureTaskTemplateVO implements Serializable {
//    数据库里是没有嵌套这种结构的，所以需要有id进行跟踪
    @ApiModelProperty(value = "主键id")
    private String templateId;

    @ApiModelProperty(value = "一级名称")
    private String oneName;

    @ApiModelProperty(value = "二级名称")
    private String twoName;

    @ApiModelProperty(value = "测量类型id")
    private String typeId;

    @ApiModelProperty(value = "状态 是否强制爆点标点")
    private Byte status;

    @ApiModelProperty(value = "测量分项列表")
    private List<MeasureItemVO> templateItem;





}
