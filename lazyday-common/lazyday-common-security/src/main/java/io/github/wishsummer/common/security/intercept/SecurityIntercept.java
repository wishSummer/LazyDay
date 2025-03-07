package io.github.wishsummer.common.security.intercept;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

/**
 * Description: 自定义拦截器：将当前请求用户信息封装到当前线程变量中
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: SecurityIntercept.java, 2025/3/3 上午11:09 $
 */
public class SecurityIntercept implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
