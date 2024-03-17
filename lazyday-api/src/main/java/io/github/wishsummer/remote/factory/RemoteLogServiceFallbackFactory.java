package io.github.wishsummer.remote.factory;

import io.github.wishsummer.domain.Result;
import io.github.wishsummer.remote.RemoteLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;


/**
 * Description:
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: RemoteLogFactory.java, 2023/12/25 14:32 $
 */
@Component
public class RemoteLogServiceFallbackFactory implements FallbackFactory<RemoteLogService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteLogServiceFallbackFactory.class);

    @Override
    public RemoteLogService create(Throwable cause) {
        log.error("记录日志调用失败：{}", cause.getMessage());
        return data -> Result.error("记录日志调用失败：{}" + cause.getMessage());
    }
}
