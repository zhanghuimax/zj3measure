package cn.pinming.microservice.measure.biz.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class MeasureStandardForm extends BaseForm {
    @ApiModelProperty("评判标准主键id")
    private String standardId;

    @ApiModelProperty("测量分项主键id")
    private String itemId;

    @Length(max = 20,min = 1,message = "评判标准名称不能为空,不能大于20字符")
    @ApiModelProperty(value = "评判标准名称" , required = true)
    private String standardName;

    @NotNull(message = "基准值不能为空")
    @ApiModelProperty(value = "基准值" , required = true)
    @Range(min = -999999,max = 999999,message = "您输入的基准值有误！")
    private BigDecimal baseValue;

    @NotNull(message = "偏差小值不能为空")
    @ApiModelProperty(value = "偏差小值" , required = true)
    @Range(min = -999999,max = 999999,message = "您输入的偏差小值有误！")
    private BigDecimal judgeStartValue;

    @NotNull(message = "偏差大值不能为空")
    @ApiModelProperty(value = "偏差大值" , required = true)
    @Range(min = -999999,max = 999999,message = "您输入的偏差大值有误！")
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
