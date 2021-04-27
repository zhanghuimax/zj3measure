package cn.pinming.microservice.measure.biz.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MeasureStandardQuery extends BaseQuery{
    @ApiModelProperty(value = "测量分项主键id")
    private String itemId;
}
