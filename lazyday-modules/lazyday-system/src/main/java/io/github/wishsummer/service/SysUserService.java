package io.github.wishsummer.service;


import io.github.wishsummer.domain.Result;
import io.github.wishsummer.domain.SysUserObject;
import io.github.wishsummer.model.LoginUser;

/**
 * 用户相关操作
 */
public interface SysUserService {

    Result<LoginUser> getUserInfo(String username);

    Result register(SysUserObject userObjectt);
}
