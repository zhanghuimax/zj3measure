package cn.pinming.microservice.measure.biz.form;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MeasurePlaceForm extends BaseForm{
    @ApiModelProperty(value = "主键id")
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

    @ApiModelProperty("楼层")
    @ExcelProperty("楼层")
    private String placeFour;

    @ApiModelProperty(value = "部位")
    @ExcelProperty("部位")
    private String placeFive;

    @ExcelProperty("图纸名称")
    @ApiModelProperty(value = "图纸名称")
    private String drawingName;

    @ApiModelProperty(value = "图纸uuid")
    private String fileUuid;
}
