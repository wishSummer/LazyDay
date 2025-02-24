package io.github.wishsummer.system.service;


import io.github.wishsummer.api.domain.SysUserObject;
import io.github.wishsummer.api.model.LoginUser;
import io.github.wishsummer.common.core.domain.Result;
import io.github.wishsummer.system.domain.vo.UserInfoVo;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * 用户相关操作
 */
public interface SysUserService {

    Result<LoginUser> login(String username);

    Result<String> register(SysUserObject userObjectt);

    Result<UserInfoVo> getUserInfo();

    Result<?> editUser(SysUserObject userObject);
}
