package io.github.wishsummer.service;


import io.github.wishsummer.domain.Result;
import io.github.wishsummer.domain.SysUserObject;
import io.github.wishsummer.model.LoginUser;

import java.util.Map;

/**
 * 用户相关操作
 */
public interface SysUserService {

    Result<LoginUser> getUser(String username);

    Result register(SysUserObject userObjectt);

    Result<Map<String, Object>> getUserInfo();
}
