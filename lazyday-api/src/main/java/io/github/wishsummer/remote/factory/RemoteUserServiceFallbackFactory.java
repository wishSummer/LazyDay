package io.github.wishsummer.remote.factory;

import io.github.wishsummer.domain.Result;
import io.github.wishsummer.remote.RemoteUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * Description:
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: RemoteUserServiceFallbackFactory.java, 2023/12/27 9:47 $
 */
public class RemoteUserServiceFallbackFactory implements FallbackFactory<RemoteUserService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteUserServiceFallbackFactory.class);

    @Override
    public RemoteUserService create(Throwable cause) {
        log.error("初始化用户信息调用失败：{}", cause.getMessage());
        return username -> Result.error("初始化用户信息调用失败" + cause.getMessage());
    }
}
