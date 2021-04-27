package cn.pinming.microservice.measure.biz.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
//序列化的作用是为了保存对象的完整性
public class MeasurePlaceVO implements Serializable {
    @ApiModelProperty(value = "主键id")
    @ExcelIgnore
    private String placeId;


    @ApiModelProperty(value = "区域")
    @ExcelProperty("区域")
    private String placeOne;

    @ApiModelProperty(value = "楼栋")
    @ExcelProperty("楼栋")
    private String placeTwo;

    @ApiModelProperty(value = "单元")
    @ExcelProperty("单元")
    private String placeThree;

    @ApiModelProperty(value = "楼层")
    @ExcelProperty("楼层")
    private String placeFour;

    @ApiModelProperty(value = "部位")
    @ExcelProperty("部位")
    private String placeFive;

    @ApiModelProperty(value = "图纸名称")
    @ExcelProperty("图纸名称")
    private String drawingName;

    @ApiModelProperty(value = "文件uuid")
    private String fileUuid;
}
