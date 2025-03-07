package io.github.wishsummer.common.security.aspect;

import io.github.wishsummer.common.core.constant.Constants;
import io.github.wishsummer.common.core.constant.UserConstants;
import io.github.wishsummer.common.core.utils.JwtUtils;
import io.github.wishsummer.common.core.utils.ServletUtils;
import io.github.wishsummer.common.security.annotation.PermissionLogicEnum;
import io.github.wishsummer.common.security.annotation.RequiresPermissions;
import io.github.wishsummer.common.security.annotation.RequiresRoles;
import io.github.wishsummer.common.security.utils.SecurityUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Description:
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: PermissionAspect.java, 2025/2/28 下午3:26 $
 */
@Aspect
@Component
public class PermissionAspect {

    public PermissionAspect() {
    }

    public static final String POINTCUT = " @annotation(com.github.wishsummer.common.security.annotation.RequiresPermissions " +
            "|| @annotation(com.github.wishsummer.common.security.annotation.RequiresRoles)";

    @Pointcut(POINTCUT)
    public void pointcut() {
    }


    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Method method = signature.getMethod();

        RequiresPermissions permissions = method.getAnnotation(RequiresPermissions.class);

        String[] permissionsValue = permissions.value();

        String token = SecurityUtil.getToken(ServletUtils.getRequest());
        String user_key = UserConstants.USER_KEY + JwtUtils.parseToken(token).get(UserConstants.USER_KEY).toString();


        if (PermissionLogicEnum.AND.equals(permissions.logic())) {


        } else {

        }

        RequiresRoles roles = method.getAnnotation(RequiresRoles.class);
        String[] roleValue = roles.value();

        if (PermissionLogicEnum.AND.equals(roles.logic())) {

        }else {

        }


        joinPoint.proceed();

        return null;
    }


}
