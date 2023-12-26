package io.github.remote.factory;

import io.github.domain.Result;
import io.github.domain.SysLogObject;
import io.github.remote.RemoteLogService;
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

    @Override
    public RemoteLogService create(Throwable cause) {
        return data -> null;
    }
}
