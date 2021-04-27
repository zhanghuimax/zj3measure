package cn.pinming.microservice.measure.common.permission;

import cn.pinming.core.cookie.AuthUser;
import cn.pinming.microservice.measure.biz.enums.PermissionEnum;
import cn.pinming.microservice.measure.common.exception.BOException;
import cn.pinming.microservice.measure.common.exception.BOExceptionEnum;
import cn.pinming.microservice.measure.configuration.SiteContextHolder;
import cn.pinming.v2.common.api.service.authority.AuthorityServerSideService;
import cn.pinming.v2.common.api.service.authority.dto.RolesDto;
import org.apache.dubbo.config.annotation.Reference;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author wyh
 * @version 1.0
 * @date 2020/8/12 10:45
 */
@Component
@Aspect
public class PermissionCheck {

    @Resource
    private SiteContextHolder siteContextHolder;
    @Reference
    private AuthorityServerSideService authorityServerSideService;

    @Pointcut("@annotation(cn.pinming.microservice.measure.common.permission.Permission))")
    public void checkPointcut(){
    }

    @Before("checkPointcut()")
    public void domain(JoinPoint joinPoint){
        AuthUser user = siteContextHolder.getNonNullCurrentUser();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Permission annotation = method.getAnnotation(Permission.class);
        byte type = annotation.type().getValue();
        String[] keys = annotation.keys();

        if(type == PermissionEnum.COMPANY_PROJECT.getValue()){
            type = user.getCurrentProjectId() == null ? PermissionEnum.COMPANY.getValue() : PermissionEnum.PROJECT.getValue();
        }

        for (String k : keys){
            RolesDto role = null;
            if (StringUtils.isEmpty(k)) {
                throw new BOException(BOExceptionEnum.PERMISSION_NOT_CONFIGURATION);
            }
            if(type == PermissionEnum.PROJECT.getValue()){
                if(user.getCurrentProjectId() == null) {
                    throw new BOException(BOExceptionEnum.NO_PERMISSION);
                }
                role = authorityServerSideService.findHasProjectFunctionRoles(user.getCurrentProjectId(), user.getId(), k);
            }else if (type == PermissionEnum.COMPANY.getValue()){
                role = authorityServerSideService.findHasCompanyFunctionRoles(user.getCurrentCompanyId(), user.getId(), k);
            }
            assert role != null;
            if(role != null && role.isHasPermission()) {
                return;
            }
        }
        throw new BOException(BOExceptionEnum.NO_PERMISSION);
    }
}