package cn.pinming.microservice.measure.common;

import cn.pinming.core.common.exception.ErrorView;
import cn.pinming.microservice.measure.common.exception.BOException;
import javax.validation.ConstraintViolation;
import java.util.Set;

public class ValidCommon {

    /**
     * 参数校验
     * @param violationSet
     * @param <T>
     */
    public static <T> void validRequestParams(Set<ConstraintViolation<T>> violationSet){
        for (ConstraintViolation<T> model : violationSet) {
            ErrorView view = new ErrorView() {
                @Override
                public String getErrorCode() {
                    return "-30";
                }

                @Override
                public String getErrorMsg() {
                    return model.getMessage();
                }
            };
            throw new BOException(view.getErrorMsg());
        }
    }
}
