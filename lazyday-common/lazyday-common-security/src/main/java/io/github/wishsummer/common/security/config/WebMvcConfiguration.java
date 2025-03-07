package io.github.wishsummer.common.security.config;


import io.github.wishsummer.common.security.intercept.SecurityIntercept;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Description:
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: WebMvcConfigurer.java, 2025/3/3 上午11:14 $
 */
public class WebMvcConfiguration implements WebMvcConfigurer {

    public static final String[] excludePath = {"/login", "/register", "/logout", "/refresh"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getInterceptor()).addPathPatterns("/**").excludePathPatterns(excludePath).order(10);
    }

    public HandlerInterceptor getInterceptor() {
        return new SecurityIntercept();
    }

}
