package cn.pinming.microservice.measure.biz.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 测量部位DTO
 * @author luo hao
 */
@ApiModel("测量部位DTO")
@Data
@ToString
public class MeasurePlaceDTO implements Serializable {

    @ApiModelProperty(value = "主键id")
    private String placeId;

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

    @ApiModelProperty(value = "任务数量")
    private Integer taskNum;


}
