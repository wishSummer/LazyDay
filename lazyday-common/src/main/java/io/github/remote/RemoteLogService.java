package io.github.remote;

import io.github.constant.ServiceNameConstants;
import io.github.domain.Result;
import io.github.domain.SysLogObject;
import io.github.remote.factory.RemoteLogServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Description:
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: LogRemote.java, 2023/12/25 14:30 $
 */
@FeignClient(contextId = "remoteLogService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteLogServiceFallbackFactory.class)
public interface RemoteLogService {

    @PostMapping("/log/insert")
    Result saveLog(@RequestBody SysLogObject data);
}
