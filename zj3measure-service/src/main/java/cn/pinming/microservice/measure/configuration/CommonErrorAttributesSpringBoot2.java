package cn.pinming.microservice.measure.configuration;

import cn.pinming.core.common.exception.CommonExceptionEnum;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 通用异常处理
 *
 * @author jin
 * @date 2018/7/27
 */
public class CommonErrorAttributesSpringBoot2 extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        Integer status = addStatus(errorAttributes, webRequest);
        HttpStatus httpStatus = HttpStatus.valueOf(status);
        if (httpStatus == null) {
            errorAttributes.put("errorCode", CommonExceptionEnum.SYS_ERROR.getErrorCode());
        } else {
            if (httpStatus.is4xxClientError()) {
                errorAttributes.put("errorCode", CommonExceptionEnum.PARAM_VALIDATE_ERROR.getErrorCode());
            } else if (httpStatus.is5xxServerError()) {
                errorAttributes.put("errorCode", CommonExceptionEnum.SYS_ERROR.getErrorCode());
            } else {
                errorAttributes.put("errorCode", CommonExceptionEnum.SYS_ERROR.getErrorCode());
            }
        }
        errorAttributes.put("success", "false");
        return errorAttributes;
    }

    private Integer addStatus(Map<String, Object> errorAttributes, RequestAttributes requestAttributes) {
        Integer status = this.getAttribute(requestAttributes, "javax.servlet.error.status_code");
        if (status == null) {
            errorAttributes.put("status", 999);
            errorAttributes.put("error", "None");
        } else {
            errorAttributes.put("status", status);

            try {
                errorAttributes.put("error", HttpStatus.valueOf(status).getReasonPhrase());
            } catch (Exception var5) {
                errorAttributes.put("error", "Http Status " + status);
            }
        }
        return status;
    }

    @SuppressWarnings("unchecked")
    private <T> T getAttribute(RequestAttributes requestAttributes, String name) {
        return (T) requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
    }
}
