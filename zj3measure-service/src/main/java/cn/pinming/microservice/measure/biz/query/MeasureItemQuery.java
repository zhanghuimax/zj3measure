package cn.pinming.microservice.measure.biz.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MeasureItemQuery extends BaseQuery{
    @ApiModelProperty("测量任务主键id")
    private String TemplateId;
}
