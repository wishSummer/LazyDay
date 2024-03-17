package io.github.wishsummer.remote;

import io.github.wishsummer.constant.ServiceNameConstants;
import io.github.wishsummer.domain.Result;
import io.github.wishsummer.model.LoginUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Description:
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: RemoteUserService.java, 2023/12/27 9:44 $
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.SYSTEM_SERVICE)
public interface RemoteUserService {

    @GetMapping("/user/{username}")
    public Result<LoginUser> getUserInfo(@PathVariable("username") String username);
}
