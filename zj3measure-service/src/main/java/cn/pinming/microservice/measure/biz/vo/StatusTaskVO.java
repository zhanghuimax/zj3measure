package cn.pinming.microservice.measure.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StatusTaskVO implements Serializable {
    @ApiModelProperty("指派待测状态")
    private Boolean assignedTestStatus;

    @ApiModelProperty("等待复测状态")
    private Boolean waitingRetestStatus;

    @ApiModelProperty("整改状态")
    private Boolean rectificationStatus;


}
