package cn.pinming.microservice.measure.biz.form;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
public class MeasureDrawingForm extends BaseForm{
    @ApiModelProperty(value = "主键id")
    private String drawingId;

    @ApiModelProperty(value = "文件uuid，不能乱填")
    private String fileUuid;

    @ApiModelProperty(value = "图纸名称")
    private String name;
}
