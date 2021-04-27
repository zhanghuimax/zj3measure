package cn.pinming.microservice.measure.biz.form;

import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 新增测量模板 form
 * @author zh
 */
@Data
public class MeasureTaskTemplateForm extends BaseForm {
    @ApiModelProperty("模板主键id,修改时候需要传")
    private String templateId;

    @ApiModelProperty(value = "一级名称",required = true)
//    @Length(max = 200,min = 1,message = "一级任务名称不能为空,不能大于200字符")
    private String oneName;

    @ApiModelProperty(value = "二级名称",required = true)
    @Length(max = 200,min = 1,message = "二级任务名称不能为空,不能大于200字符")
    private String twoName;

    @ApiModelProperty(value = "所属部门名称")
    private String orgName;

    @ApiModelProperty(value = "模板设置")
    private JSONObject setting;

    @ApiModelProperty(value = "测量类型id", required = true)
    @NotNull(message = "测量类型不能为空")
    private String typeId;

    @Valid
    @ApiModelProperty(value = "测量分项json数组", required = true)
    @Size(max = 50, message = "测量分项上限为50条！")
    private List<MeasureItemForm> measures;
}
