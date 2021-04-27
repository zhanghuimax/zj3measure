package cn.pinming.microservice.measure.configuration;

import cn.pinming.core.cookie.AuthUserHelper;
import cn.pinming.core.cookie.CorsFilter;
import cn.pinming.core.cookie.support.AuthUserHolder;
import cn.pinming.core.cookie.support.spring.AuthUserInterceptor;
import cn.pinming.core.web.exception.UnauthorizedException;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Objects;


/**
 * Created by tcz on 2020/02/19.
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Configuration
    static class WebComponentConfiguration{
        @Bean
        public AuthUserHelper authUserHelper(@Value("${cookie.domain}") String domain) {
            AuthUserHelper helper = new AuthUserHelper();
            helper.setDomain(domain);
            return helper;
        }

        @Bean
        public FilterRegistrationBean corsFilter() {
            FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter());
            bean.setOrder(0);
            return bean;
        }

        @Bean
        public ErrorAttributes errorAttributes() {
            return new CommonErrorAttributesSpringBoot2();
        }

        @Bean
        public AuthUserInterceptor authUserInterceptor(
                @Qualifier("siteContextHolder") AuthUserHolder holder,
                AuthUserHelper authUserHelper) {
            return new AuthUserInterceptor(holder,authUserHelper, errorMessage -> {
                if (errorMessage == null) {
                    throw new UnauthorizedException();
                } else {
                    throw new UnauthorizedException(errorMessage);
                }
            });
        }
    }

    @Autowired
    private AuthUserInterceptor authUserInterceptor;

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);
        return converter;
    }

    @Bean
    public LogInterceptor logInterceptor() {
        return new LogInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        Objects.requireNonNull(authUserInterceptor, "AuthUserInterceptor can not be null");
        registry.addInterceptor(logInterceptor());
        registry.addInterceptor(authUserInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/common/**")
                .excludePathPatterns("/api/callback/**")
        ;
    }
}
