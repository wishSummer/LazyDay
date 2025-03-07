package io.github.wishsummer.common.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RequiresRoles {

    /**
     * 需要校验的权限标识
     */
    String[] value() default {};

    /**
     * 校验权限逻辑标识
     */
    PermissionLogicEnum logic() default PermissionLogicEnum.AND;

}
