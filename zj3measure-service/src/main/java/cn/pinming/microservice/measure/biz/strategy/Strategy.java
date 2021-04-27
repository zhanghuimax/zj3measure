package cn.pinming.microservice.measure.biz.strategy;

import cn.pinming.core.web.interfaces.BaseStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jin on 2020-03-12.
 */
@Slf4j
public class Strategy extends BaseStrategy{

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected String toJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }
}
