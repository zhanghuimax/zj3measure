package cn.pinming.microservice.measure.configuration;

import cn.pinming.core.common.exception.CommonExceptionEnum;
import cn.pinming.core.common.exception.ErrorView;
import cn.pinming.core.common.exception.ValidationException;
import cn.pinming.core.web.exception.BaseErrorControllerAdvice;
import cn.pinming.core.web.response.ErrorResponse;
import cn.pinming.core.web.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * Created by jin on 2020-02-18.
 */
@ControllerAdvice
@Slf4j
public class GlobeControllerAdvice extends BaseErrorControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintViolationException(ConstraintViolationException e) {
        if (log.isInfoEnabled()) {
            log.info(e.getMessage(), e);
        }
        String msg;
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        if (!constraintViolations.isEmpty()) {
            msg = constraintViolations.iterator().next().getMessage();
        } else {
            msg = e.getMessage();
        }
        ErrorResponse result = new ErrorResponse(CommonExceptionEnum.PARAM_VALIDATE_ERROR.getErrorCode(), msg);
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<?> baseRunException(ValidationException e) {
        if (log.isInfoEnabled()) {
            log.info(e.getMessage(), e);
        }
        ErrorResponse result = new ErrorResponse(e.getErrorCode(), e.getMessage());
        return ResponseEntity.ok(result);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeException(RuntimeException e) {
        if (log.isErrorEnabled()) {
            log.error(e.getMessage(), e);
        }
        String errorCode, errorMsg;
        if (e instanceof ErrorView) {
            errorCode = ((ErrorView) e).getErrorCode();
            errorMsg = ((ErrorView) e).getErrorMsg();
        } else {
            errorCode = CommonExceptionEnum.SYS_ERROR.getErrorCode();
            errorMsg = CommonExceptionEnum.SYS_ERROR.getErrorMsg();
        }
        ErrorResponse result = new ErrorResponse(errorCode, errorMsg);
        return ResponseEntity.ok(result);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        log.error("POST参数异常。", e);
        String defaultMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        return new ResponseEntity<>(new ErrorResponse("-200", defaultMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
