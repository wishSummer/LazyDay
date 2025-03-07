package io.github.wishsummer.common.log.aspect;

import com.alibaba.fastjson2.JSON;
import io.github.wishsummer.common.log.annotation.WebLog;
import io.github.wishsummer.api.domain.SysLogObject;
import io.github.wishsummer.api.remote.RemoteLogService;
import io.github.wishsummer.common.core.utils.ServletUtils;
import io.github.wishsummer.common.log.filter.PropertyPreExcludeFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    private static final ThreadLocal<Long> TIME_THREADLOCAL = new NamedThreadLocal<>("Cost Time");

    private static final String[] EXCLUDE_PROPERTIES = {};

    public static final String POINTCUT_SIGN = "@annotation(webLog)";

    private RemoteLogService remoteLogService;


    @Pointcut(POINTCUT_SIGN)
    public void pointcut() {
    }

    @Before("@annotation(webLog)")
    public void doBefore(JoinPoint joinPoint, WebLog webLog) {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    @AfterReturning(pointcut = "@annotation(webLog)", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, WebLog webLog, Object result) {
        handleLog(joinPoint, webLog, null, result);
    }

    @AfterThrowing(value = "@annotation(webLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, WebLog webLog, Exception e) {
        handleLog(joinPoint, webLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, WebLog webLog, final Exception e, Object result) {
        try {
            SysLogObject sysLogObject = new SysLogObject();
//            操作访问结果状态
            sysLogObject.setStatus(0);
//            请求地址
            sysLogObject.setLogIp(StringUtils.substring(ServletUtils.getRequest().getRequestURI(), 0, 255));
            sysLogObject.setLogUrl(ServletUtils.getRequest().getRequestURI());
//            用户信息 TODO 用户名
            sysLogObject.setLogName("");
            //            异常信息
            if (e != null) {
                sysLogObject.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
                sysLogObject.setErrorMsg(e.getMessage());
                sysLogObject.setStatus(1);
            }

            // 设置请求方法相关信息
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            sysLogObject.setMethod(className + "." + methodName + "()");
            sysLogObject.setRequestMethod(ServletUtils.getRequest().getMethod());

            //设置注解信息
            setAnnotationParam(joinPoint, webLog, sysLogObject, result);

            //设置时间
            sysLogObject.setLogTime(LocalDateTime.now());
            sysLogObject.setCostTime(System.currentTimeMillis() - TIME_THREADLOCAL.get());

            log.info("日志记录：{}", sysLogObject);

            remoteLogService.saveLog(sysLogObject);
        } catch (Exception exe) {
            log.error("异常信息:{}", exe.getMessage());
        } finally {
            TIME_THREADLOCAL.remove();
        }
    }


    /**
     * 设置注解相关信息
     */
    private void setAnnotationParam(JoinPoint joinPoint, WebLog webLog, SysLogObject sysLogObject, Object result) {
        System.out.println(webLog);
//        获取注解
        String[] excludeParams = webLog.excludeParams();

//        设置数据
        sysLogObject.setTitle(webLog.title());
        sysLogObject.setBusinessType(webLog.businessType().getCode());

        if (webLog.isSaveRequestData()) {
            setRequestData(joinPoint, sysLogObject, excludeParams);
        }

        if (webLog.isSaveResponseData()) {
//            operLog.setJsonResult(StringUtils.substring(JSON.toJSONString(jsonResult), 0, 2000));
            sysLogObject.setJsonResult(JSON.toJSONString(result));
        }
    }


    /**
     * 设置请求参数
     * TODO
     */
    private void setRequestData(JoinPoint joinPoint, SysLogObject sysLogObject, String[] excludeParams) {
        String requestMethod = sysLogObject.getRequestMethod();
        Object[] args = joinPoint.getArgs();
        System.out.println(args);
        Map<String, String> paramMap = ServletUtils.getParamMap(ServletUtils.getRequest());
        if (paramMap.isEmpty() && requestMethod.equals(HttpMethod.PUT.name()) && requestMethod.equals(HttpMethod.POST.name())) {
            String params = argsArraryToString(joinPoint.getArgs(), excludeParams);
            sysLogObject.setLogParam(params);
        } else {
            sysLogObject.setLogParam(JSON.toJSONString(paramMap));
        }
    }

    /**
     * 格式化请求参数
     */
    private String argsArraryToString(Object[] params, String[] excludeParams) {
        StringBuilder stringBuilder = new StringBuilder();
        if (params != null && params.length > 0) {
            for (Object param : params) {
                if (param != null && isFile(param)) {
                    String jsonString = JSON.toJSONString(param, setExcludeParams(excludeParams));
                    log.info("jsonString:{}", jsonString);
                    stringBuilder.append(jsonString.toString() + " ");
                }
            }
        }
        return stringBuilder.toString().trim();
    }

    /**
     * 忽略敏感参数，设置过滤器
     */
    public PropertyPreExcludeFilter setExcludeParams(String[] excludeParams) {
        return new PropertyPreExcludeFilter().addExcludes(ArrayUtils.addAll(excludeParams, EXCLUDE_PROPERTIES));
    }

    /**
     * 判断请求参数是否是文件
     */
    private boolean isFile(Object o) {
        Class<?> aClass = o.getClass();
        if (aClass.isArray()) {
            return aClass.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collections.class.isAssignableFrom(aClass)) {
            Collection collection = (Collection) o;
            for (Object object : collection) {
                return object instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(aClass)) {
            Map map = (Map) o;
            for (Object object : map.entrySet()) {
                Map.Entry entry = (Map.Entry) object;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse || o instanceof BindingResult;
    }

    @Autowired
    public void setRemoteLogService(RemoteLogService remoteLogService) {
        this.remoteLogService = remoteLogService;
    }
}
