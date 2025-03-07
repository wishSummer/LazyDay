package io.github.wishsummer.common.security.config;

import feign.RequestInterceptor;
import io.github.wishsummer.common.security.intercept.FeignHeaderIntercept;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: FeignConfiguration.java, 2025/3/3 下午2:32 $
 */
@Configuration
public class FeignConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignHeaderIntercept();
    }

}
