package io.github.wishsummer.common.core.service;


import io.github.wishsummer.api.domain.SysUserObject;
import io.github.wishsummer.api.model.LoginUser;
import io.github.wishsummer.common.core.domain.Result;

/**
 * 用户相关操作
 */
public interface SysUserService {

    Result<LoginUser> getUserInfo(String username);

    Result register(SysUserObject userObjectt);
}
