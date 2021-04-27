package cn.pinming.microservice.measure.biz.dto;

import cn.pinming.microservice.measure.configuration.SerializerBigDecimal;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@ApiModel("任务返回实体类")
@Data
public class MeasureTaskDTO implements Serializable {
    @ApiModelProperty("任务主键id")
    @ExcelIgnore
    private String taskId;

    @ApiModelProperty("企业id")
    @ExcelIgnore
    private Integer companyId;

    @ApiModelProperty("项目id")
    @ExcelIgnore
    private Integer projectId;

    @ApiModelProperty("项目名称")
    @ExcelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("测量任务名称")
    @ExcelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("测量任务编号")
    @ExcelProperty("任务编号")
    private String taskNo;

    @ApiModelProperty("模板主键id")
    @ExcelIgnore
    private String templateId;

    @ApiModelProperty("实测实量指标名称")
    @ExcelProperty("实测实量指标")
    private String templateName;

    @ApiModelProperty("测量类型id")
    @ExcelIgnore
    private String typeId;

    @ApiModelProperty("测量类型名称")
    @ExcelProperty("测量类型")
    private String typeName;

    @ApiModelProperty("部位主键id")
    @ExcelIgnore
    private String placeId;

    @ApiModelProperty("测量部位名称")
    @ExcelProperty("测量部位")
    private String placeName;

    @ApiModelProperty("合格率")
    @ExcelProperty("合格率")
    @JsonSerialize(using = SerializerBigDecimal.class)
    private BigDecimal passRate;

    @ApiModelProperty("测量任务状态")
    @ExcelIgnore
    private Byte status;

    @ApiModelProperty("测量任务状态描述")
    @ExcelProperty("测量任务状态")
    private String statusDescription;

    @ApiModelProperty("构件名称")
    @ExcelProperty("构件名称")
    private String componentName;

    @ApiModelProperty("强度等级")
    @ExcelProperty("强度等级(%)")
    private BigDecimal designStrength;

    @ApiModelProperty("测量进度")
    @ExcelProperty("测量进度")
    private BigDecimal measureProgress;

    @ApiModelProperty("检查类型")
    @ExcelIgnore
    private Byte checkType;

    @ApiModelProperty("检查类型名称")
    @ExcelProperty("检查类型")
    private String checkTypeName;

    @ApiModelProperty("检查人员名称")
    @ExcelProperty("检查人员")
    private String checkName;

    @ApiModelProperty("任务创建时间")
    @ExcelProperty("任务创建时间")
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date gmtCreate;

    @ApiModelProperty("测量人员id")
    @ExcelIgnore
    private String memberId;

    @ApiModelProperty("测量人员名称")
    @ExcelProperty("测量人员")
    private String memberName;

    @ApiModelProperty("检查分项数量")
    @ExcelIgnore
    private Integer totalItems;

    @ApiModelProperty("当前完成测量点数")
    @JsonIgnore
    @ExcelIgnore
    private Integer currentPoints;

    @ApiModelProperty("测量总点数")
    @JsonIgnore
    @ExcelIgnore
    private Integer totalPoints;

    @ExcelProperty("提交时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date checkTime;

    @ApiModelProperty("检查分项json")
    @JsonIgnore
    private String checkItem;

    @ApiModelProperty("合格的测量点数")
    @JsonIgnore
    private Integer qualifiedPoints;

}
