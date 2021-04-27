package cn.pinming.microservice.measure.common.exception;

import cn.pinming.core.common.exception.ErrorView;

/**
 * 运行时异常的基类
 *
 * @author Sam
 * @version 1.0
 * @Description :
 * @created Oct 18, 2011 3:10:12 PM
 * @fileName com.weqia.common.exception.BaseException.java
 */
public class BaseRuntimeException extends RuntimeException implements ErrorView {

    /**
     * @Description
     * @author
     * @create at Oct 18, 2011 3:11:24 PM
     */
    private static final long serialVersionUID = 9051275901375243959L;
    /**
     * 错误码
     */
    private String errorCode;

    @Deprecated
    public BaseRuntimeException() {
        super();
    }

    /**
     * 构造函数
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    public BaseRuntimeException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }

    /**
     * 取错误码
     *
     * @return
     * @Description
     */
    @Override
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 取错误信息
     *
     * @return
     * @Description
     */
    @Override
    public String getErrorMsg() {
        return getMessage();
    }
}
