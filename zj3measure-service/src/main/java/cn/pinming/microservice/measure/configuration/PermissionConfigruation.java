package cn.pinming.microservice.measure.configuration;

import cn.pinming.core.service.permission.support.dubbo.DubboServiceBinder;
import cn.pinming.v2.common.api.service.authority.AuthorityServerSideService;
import cn.pinming.v2.common.api.service.authority.DataAccessRangeService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PermissionConfigruation {

    @Reference
    private DataAccessRangeService dataAccessRangeService;
    @Reference
    private AuthorityServerSideService authorityServerSideService;

    @Bean
    public DubboServiceBinder dubboServiceBinder() {
        return new DubboServiceBinder(dataAccessRangeService, authorityServerSideService);
    }
}