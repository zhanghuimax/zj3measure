package cn.pinming.microservice.measure.biz.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseQuery extends Page {

    @ApiModelProperty("企业id")
    private Integer companyId;

    @ApiModelProperty("项目id")
    private Integer projectId;

    @ApiModelProperty("人员id")
    private String memberId;
}
