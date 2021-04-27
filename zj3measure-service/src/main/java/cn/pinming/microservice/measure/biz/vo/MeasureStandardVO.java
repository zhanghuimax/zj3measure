package cn.pinming.microservice.measure.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class MeasureStandardVO implements Serializable {
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

    @ApiModelProperty(value = "分项详细信息")
    private String itemDescribe;

}
