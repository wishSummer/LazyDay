package io.github.wishsummer.api.remote;

import io.github.wishsummer.api.model.LoginUser;
import io.github.wishsummer.api.domain.SysUserObject;
import io.github.wishsummer.common.core.constant.ServiceNameConstants;
import io.github.wishsummer.common.core.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Description:
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: RemoteUserService.java, 2023/12/27 9:44 $
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.SYSTEM_SERVICE)
public interface RemoteUserService {

    @GetMapping("/user/{username}")
    Result<LoginUser> getUserInfo(@PathVariable("username") String username);

    @PostMapping("/user/register")
    Result registerUser(SysUserObject sysUserObject);
}
