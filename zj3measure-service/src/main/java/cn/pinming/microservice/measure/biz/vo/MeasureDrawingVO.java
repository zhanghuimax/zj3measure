package cn.pinming.microservice.measure.biz.vo;

import cn.pinming.microservice.measure.configuration.LocalDateTimeSerializer;
import cn.pinming.v2.common.api.dto.FileDto;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MeasureDrawingVO implements Serializable {

    @ApiModelProperty(value = "主键id")
    private String drawingId;

    @ApiModelProperty(value = "图纸名称")
    private String name;

    @ApiModelProperty(value = "创建人")
    private String createName;

    @ApiModelProperty(value = "文件uuid")
    private String fileUuid;

    @ApiModelProperty(value = "文件dto")
    private FileDto fileDto;

    @ApiModelProperty(value = "创建时间")
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern ="yyyy-MM-dd")
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "修改人")
    private String modifyName;

    @ApiModelProperty(value = "修改时间")
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtModify;


}
