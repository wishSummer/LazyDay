package io.github.wishsummer.service;


import io.github.wishsummer.model.LoginUser;

/**
 * 用户相关操作
 */
public interface SysUserService {

    LoginUser getUserInfo(String username);
}
