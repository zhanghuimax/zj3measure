package cn.pinming.microservice.measure.biz.query;

import cn.pinming.microservice.measure.biz.form.BaseForm;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 测量记录query对象
 */
@Data
public class MeasureTaskQuery extends BaseQuery {

    @ApiModelProperty("测量任务名称")
    private String taskName;

    @ApiModelProperty("测量任务编号")
    private String taskNo;

    @ApiModelProperty("测量类型id")
    private String typeId;

    @ApiModelProperty("测量任务状态")
    private Byte status;

    @ApiModelProperty("合格率")
    private Byte passRate;

    @ApiModelProperty("合格率范围(开始)")
    private Byte startPassRate;

    @ApiModelProperty("合格率范围(结束)")
    private Byte endPassRate;

    @ApiModelProperty("测量日期开始时间")
    private Date startTime;

    @ApiModelProperty("测量日期结束时间")
    private Date endTime;

    @ApiModelProperty("测量部位id")
    private String placeId;

    @ApiModelProperty("排序名称")
    private String orderName;

    @ApiModelProperty("排序规则 不传 默认升序 传desc降序")
    private String orderType;

    @ApiModelProperty("部门id列表")
    private List<Integer> departmentIds;
}
