package cn.pinming.microservice.measure.configuration;

import cn.pinming.core.common.exception.ErrorView;
import cn.pinming.core.common.exception.ValidationException;
import cn.pinming.openapi.client.DefaultOpenApInvokerFactory;
import cn.pinming.openapi.client.DefaultOpenApiResult;
import cn.pinming.openapi.spring.autoconfigure.CustomErrorHandler;
import cn.pinming.openapi.spring.autoconfigure.JsonComponent;
import cn.pinming.platform.gateway.client.core.Invoker;
import cn.pinming.platform.gateway.client.core.export.GatewayExceptionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import static cn.pinming.openapi.spring.autoconfigure.CustomErrorHandler.SYSTEM_ERROR_CODE;
import static cn.pinming.openapi.spring.autoconfigure.CustomErrorHandler.SYSTEM_ERROR_MSG;


@Slf4j
@Configuration
public class ApplicationConfiguration {

    @Bean(name = "openApiInvoker")
    public Invoker openApiInvoker(ApplicationContext applicationContext, @Qualifier("openapiExceptionHandler") GatewayExceptionHandler handler) {
        return DefaultOpenApInvokerFactory.newInstance(applicationContext, handler);
    }

    @Bean(name = "openapiExceptionHandler")
    public GatewayExceptionHandler exceptionHandler(JsonComponent jsonComponent, CustomErrorHandler customErrorHandler) {
        Logger logger = LoggerFactory.getLogger("cn.pinming.openapi.OpenapiExceptionHandler");
        return e -> {
            DefaultOpenApiResult result = new DefaultOpenApiResult();
            result.setSuccess(false);
            if (e instanceof ErrorView) {
                if (logger.isInfoEnabled()) {
                    logger.info(e.getMessage(), e);
                }
                result.setErrorCode(((ErrorView) e).getErrorCode());
                result.setError(((ErrorView) e).getErrorMsg());
            } else if (e instanceof ValidationException) { //兼容老版本
                if (logger.isInfoEnabled()) {
                    logger.info(e.getMessage(), e);
                }
                result.setErrorCode(((ValidationException) e).getErrorCode());
                result.setError(((ValidationException) e).getErrorMsg());
            } else if (customErrorHandler.handle(e, result)) {

            } else {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage(), e);
                }
                result.setErrorCode(SYSTEM_ERROR_CODE);
                result.setError(SYSTEM_ERROR_MSG);
            }
            return jsonComponent.toJson(result);
        };
    }

    @Bean(name = "openApiCustomErrorHandler")
    public CustomErrorHandler openApiCustomErrorHandler() {
        return (e, context) -> {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
            context.setErrorCode(SYSTEM_ERROR_CODE);
            context.setError(SYSTEM_ERROR_MSG);
            context.setSuccess(false);
            return true;
        };
    }

    @Bean(name = "openApiJacksonJsonComponent")
    public JsonComponent openApiJacksonJsonComponent(ObjectMapper om) {
        return new JsonComponent() {
            @Override
            public String toJson(Object o) {
                try {
                    return om.writeValueAsString(o);
                } catch (JsonProcessingException e) {
                    if (log.isErrorEnabled()) {
                        log.error(e.getMessage(), e);
                    }
                }
                return null;
            }

            @Override
            public <T> T toObject(String json, Class<T> clazz) {
                try {
                    return om.readValue(json, clazz);
                } catch (IOException e) {
                    if (log.isErrorEnabled()) {
                        log.error(e.getMessage(), e);
                    }
                }
                return null;
            }
        };
    }


    @Bean
    public Object openApiGatewayProxy(ApplicationContext applicationContext, @Qualifier("openapiExceptionHandler") GatewayExceptionHandler handler) {
        ServiceConfig<GenericService> service = new ServiceConfig<>();
        service.setInterface("cn.pinming.openapi.proxy.zj3MeasureService");
        service.setRef(openApiInvoker(applicationContext,handler));
        service.export();
        return new Object();
    }


}
