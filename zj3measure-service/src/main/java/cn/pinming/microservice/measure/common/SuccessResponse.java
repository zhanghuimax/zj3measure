package cn.pinming.microservice.measure.common;

import cn.pinming.core.web.response.Response;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 成功的响应
 * Created by jin on 2018/7/31.
 */
@Data
public class SuccessResponse extends Response {

    private Object data;

    public SuccessResponse(Object data) {
        this.success = true;
        this.data = data;
    }

    public SuccessResponse() {
        this.success = true;
    }
}
