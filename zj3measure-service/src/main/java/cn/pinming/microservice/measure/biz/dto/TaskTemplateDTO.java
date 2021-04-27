package cn.pinming.microservice.measure.biz.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel("实测实量指标DTO")
//@ToString
public class TaskTemplateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String templateId;

    @ApiModelProperty(value = "企业id")
    private Integer companyId;

    @ApiModelProperty(value = "一级名称")
    private String oneName;

    @ApiModelProperty(value = "二级名称")
    private String twoName;

    @ApiModelProperty(value = "所属单位")
    private String orgName;

    @ApiModelProperty(value = "测量类型id")
    private String typeId;

    @ApiModelProperty(value = "检查分项数量")
    private Integer checkItemCount;

    @ApiModelProperty(value = "测量部位数量")
    private Integer measurePositionCount;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "创建人名称")
    private String createName;

    @ApiModelProperty(value = "修改人")
    private String modifyId;

    @ApiModelProperty(value = "修改人名称")
    private String modifyName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime gmtModify;

    @ApiModelProperty(value = "实测实量名称")
    private String taskName;

    @ApiModelProperty(value = "是否启用（1.启用,2.禁用）")
    private Byte enabled;
}
