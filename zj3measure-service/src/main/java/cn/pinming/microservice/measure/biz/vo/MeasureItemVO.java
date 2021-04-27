package cn.pinming.microservice.measure.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;

import javax.xml.validation.Validator;
import java.io.Serializable;
import java.util.List;

@Data
public class MeasureItemVO implements Serializable {
    @ApiModelProperty(value = "任务模板主键id")
    private String templateId;

    @ApiModelProperty(value = "测量分项主键id")
    private String itemId;

    @ApiModelProperty(value = "测量类型主键id")
    private String typeId;

    @ApiModelProperty(value = "测量区数量")
    private Integer measureAreaCount;

    @ApiModelProperty(value = "每区测量点数量")
    private Integer areaPointCount;

    @ApiModelProperty(value = "实测方法描述")
    private String inspectMethodDescribe;

    @ApiModelProperty(value = "引用规范描述")
    private String citeNormDescribe;

    @ApiModelProperty(value = "测量标准列表")
    private List<MeasureStandardVO> standards;

    @ApiModelProperty(value = "是否禁止手工录入：1、禁止 2、非禁止")
    private Byte isForbidden;
}
