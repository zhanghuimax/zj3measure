package cn.pinming.microservice.measure.configuration;

import cn.pinming.core.common.exception.CommonExceptionEnum;
import cn.pinming.core.common.exception.ErrorView;
import cn.pinming.core.common.exception.ValidationException;
import cn.pinming.core.web.response.ErrorResponse;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author jin
 * @date 2020-02-19
 */
@Component
public class SystemErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> attr = new LinkedHashMap<>();
        Throwable error = this.getError(webRequest);
        String errorCode;
        Object errorMsg;
        if (error == null) {
            Integer httpStatusCode = (Integer) webRequest.getAttribute("javax.servlet.error.status_code", 0);
            if (httpStatusCode != null) {
                errorCode = httpStatusCode.toString();
                errorMsg = HttpStatus.valueOf(httpStatusCode).getReasonPhrase();
            } else {
                errorCode = CommonExceptionEnum.SYS_ERROR.getErrorCode();
                errorMsg = CommonExceptionEnum.SYS_ERROR.getErrorMsg();
            }
        } else {
            ErrorResponse jsonResult = resolveError(error);
            errorCode = jsonResult.getErrorCode();
            errorMsg = jsonResult.getErrorMsg();
        }
        attr.put("success", false);
        attr.put("errorCode", errorCode);
        attr.put("errorMsg", errorMsg);
        return attr;
    }

    private ErrorResponse resolveError(Throwable t) {
        String errorCode, errorMsg;
        if (t instanceof ErrorView) {
            errorCode = ((ErrorView) t).getErrorCode();
            errorMsg = ((ErrorView) t).getErrorMsg();
        } else if (t instanceof ValidationException) {
            errorCode = ((ValidationException) t).getErrorCode();
            errorMsg = ((ValidationException) t).getErrorMsg();
        } else {
            errorCode = CommonExceptionEnum.SYS_ERROR.getErrorCode();
            errorMsg = t.getMessage();
        }
        return new ErrorResponse(errorCode, errorMsg);
    }

}
