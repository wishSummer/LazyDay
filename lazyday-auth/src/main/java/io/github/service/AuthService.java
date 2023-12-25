package io.github.service;

import io.github.constant.UserConstants;
import org.apache.commons.lang3.StringUtils;

public class AuthService {

    public void login(String username, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password)) {
            //TODO 记录日志
//            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户/密码必须填写");
//            throw new ServiceException("用户/密码必须填写");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            //TODO 记录日志
//            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户密码不在指定范围");
//            throw new ServiceException("用户密码不在指定范围");
        }
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            //TODO 记录日志
//            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户名不在指定范围");
//            throw new ServiceException("用户名不在指定范围");
        }

        //TODO IP黑名单拦截

        //TODO 查询用户信息

        //TODO 验证用户信息
    }
}
