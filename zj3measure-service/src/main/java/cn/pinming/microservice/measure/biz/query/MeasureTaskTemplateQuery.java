package cn.pinming.microservice.measure.biz.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MeasureTaskTemplateQuery extends BaseQuery{

    @ApiModelProperty("指标名称")
    private String templateName;

}
