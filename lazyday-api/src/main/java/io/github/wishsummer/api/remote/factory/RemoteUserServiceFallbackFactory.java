package io.github.wishsummer.api.remote.factory;

import io.github.wishsummer.api.model.LoginUser;
import io.github.wishsummer.api.domain.SysUserObject;
import io.github.wishsummer.api.remote.RemoteUserService;
import io.github.wishsummer.common.core.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: RemoteUserServiceFallbackFactory.java, 2023/12/27 9:47 $
 */
@Slf4j
@Component
public class RemoteUserServiceFallbackFactory implements FallbackFactory<RemoteUserService> {

    @Override
    public RemoteUserService create(Throwable cause) {
        log.error("初始化用户信息调用失败：{}", cause.getMessage());
        return new RemoteUserService() {
            @Override
            public Result<LoginUser> getUserInfo(String username) {
                return Result.error("初始化用户信息调用失败" + cause.getMessage());
            }

            @Override
            public Result registerUser(SysUserObject sysUserObject) {
                return Result.error("初始化用户信息调用失败" + cause.getMessage());
            }
        };
    }
}
