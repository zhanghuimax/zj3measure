package cn.pinming.microservice.measure.common.permission;

import cn.pinming.microservice.measure.biz.enums.PermissionEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wyh
 * @version 1.0
 * @date 2020/8/12 10:42
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
    PermissionEnum type() default PermissionEnum.COMPANY_PROJECT; //企业级1 项目级2 默认为0 代表企业和项目
    String[] keys();  //权限key
}
