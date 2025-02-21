package io.github.wishsummer.api.remote.factory;

import io.github.wishsummer.api.remote.RemoteLogService;
import io.github.wishsummer.common.core.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;


/**
 * Description:
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: RemoteLogFactory.java, 2023/12/25 14:32 $
 */
@Slf4j
@Component
public class RemoteLogServiceFallbackFactory implements FallbackFactory<RemoteLogService> {


    @Override
    public RemoteLogService create(Throwable cause) {
        log.error("记录日志调用失败：{}", cause.getMessage());
        return data -> Result.error("记录日志调用失败：{}" + cause.getMessage());
    }
}
