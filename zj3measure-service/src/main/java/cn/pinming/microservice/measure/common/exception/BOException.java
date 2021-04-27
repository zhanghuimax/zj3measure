package cn.pinming.microservice.measure.common.exception;

/**
 * @author Sam
 * @version 1.0
 * @Description : 业务异常
 * @created 2011-9-2 下午01:27:57
 * @fileName com.weqia.common.exception.BOException.java
 */
public class BOException extends BaseRuntimeException {
    private static final long serialVersionUID = -8246635855921697781L;

    /**
     * BOExceptionEnum构造业务层异常
     *
     * @param en
     */
    public BOException(cn.pinming.microservice.measure.common.exception.BOExceptionEnum en) {
        super(en.errorCode(), en.errorMsg());
    }


    public BOException(String errorMsg) {
        super("", errorMsg);
    }

    /**
     * 构造函数
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    //@Deprecated
    public BOException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    /**
     * 抛出BOException异常
     *
     * @param en 异常枚举
     * @Description
     */
    public static void throwz(cn.pinming.microservice.measure.common.exception.BOExceptionEnum en) {
        throw new BOException(en);
    }

    public boolean isSame(cn.pinming.microservice.measure.common.exception.BOExceptionEnum en) {
        return this.getErrorCode().equals(en.errorCode()) && this.getErrorMsg().equals(en.errorMsg());
    }
}
