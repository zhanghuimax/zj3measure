package cn.pinming.microservice.measure.biz.form;

import com.fasterxml.jackson.databind.ser.Serializers;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class MeasureItemForm extends BaseForm {
    @ApiModelProperty("测量分项主键id")
    private String itemId;

    @ApiModelProperty("任务模板主键id")
    private String templateId;

    @NotEmpty(message = "测量类型id不能为空")
    @ApiModelProperty(value = "测量类型id" , required = true)
    private String typeId;

    @ApiModelProperty(value = "测量类型名称" , required = true)
    private String typeName;

    @Length(max = 500,min = 1,message = "检查方法描述不能为空,不能大于500字符")
    @ApiModelProperty(value = "检查方法描述" , required = true)
    private String inspectMethodDescribe;

    @Length(max = 500,min = 1,message = "引用规范描述不能为空,不能大于500字符")
    @ApiModelProperty(value = "引用规范描述" , required = true)
    private String citeNormDescribe;

    @NotNull(message = "测量区数量不能为空")
    @ApiModelProperty(value = "测量区数量" , required = true)
    private Integer measureAreaCount;

    @NotNull(message = "每区测量点数量不能为空")
    @ApiModelProperty(value = "每区测量点数量" , required = true)
    private Integer areaPointCount;

    @Valid
    @ApiModelProperty(value = "评判标准列表", required = true)
    @Size(max = 10, message = "评判标准上限为10条！")
    private List<MeasureStandardForm> standards;

    @NotNull(message = "是否禁止手工录入标识不能为空")
    @ApiModelProperty(value = "否禁止手工录入（1.禁止手工录入，2.非禁止）" , required = true)
    private Byte isForbidden;

    @ApiModelProperty(value = "禁止删除数值：1 禁止 2 非禁止")
    private Byte isManualDelete;

    @ApiModelProperty(value = "1 合格 2 不合格  3 未填完 null 未填")
    private Byte status;
}
