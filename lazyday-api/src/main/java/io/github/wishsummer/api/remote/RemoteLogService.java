package io.github.wishsummer.api.remote;

import io.github.wishsummer.api.remote.factory.RemoteLogServiceFallbackFactory;
import io.github.wishsummer.api.domain.SysLogObject;
import io.github.wishsummer.common.core.constant.ServiceNameConstants;
import io.github.wishsummer.common.core.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
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
    Result<String> saveLog(@RequestBody SysLogObject data);
}
