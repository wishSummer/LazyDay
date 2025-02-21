package io.github.wishsummer.gateway.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: XssProperties.java, 2025/2/20 下午4:02 $
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "security.xss")
public class XssProperties {

    /**
     * xss 开关
     */
    private Boolean enabled = true;

    /**
     * 过滤排除名单
     */
    private List<String> excludes = new ArrayList<>();

}
