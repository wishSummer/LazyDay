package io.github.wishsummer.service;

import io.github.wishsummer.constant.UserConstants;
import io.github.wishsummer.domain.Result;
import io.github.wishsummer.exception.ServiceException;
import io.github.wishsummer.model.LoginUser;
import io.github.wishsummer.wishsummer.remote.RemoteLogService;
import io.github.wishsummer.wishsummer.remote.RemoteUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private RemoteLogService remoteLogService;

    private RemoteUserService remoteUserService;

    private PasswordService passwordService;


    public void login(String username, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password)) {
            remoteLogService.saveLog(passwordService.setSysLogObject(username, 1, "用户/密码必须填写"));
            throw new ServiceException("用户/密码必须填写");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            remoteLogService.saveLog(passwordService.setSysLogObject(username, 1, "用户密码不在指定范围"));
            throw new ServiceException("用户密码不在指定范围");
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            remoteLogService.saveLog(passwordService.setSysLogObject(username, 1, "用户名不在指定范围"));
            throw new ServiceException("用户名不在指定范围");
        }

        //TODO IP黑名单拦截

        //TODO redis验证
        passwordService.checkRetryCount(username);

        //TODO 查询用户信息
        Result<LoginUser> userInfoResult = remoteUserService.getUserInfo(username);
        if (userInfoResult == null || userInfoResult.getData() == null) {
            remoteLogService.saveLog(passwordService.setSysLogObject(null, 0, "登录用户不存在"));
            throw new ServiceException("登录用户" + username + "不存在");
        }
        if (Result.SUCCESS != userInfoResult.getCode()) {
            throw new ServiceException(userInfoResult.getMessage());
        }
        LoginUser loginUser = userInfoResult.getData();

        // 验证用户登录信息，更新用户登录尝试次数 TODO 临时挂参loginUser，后续修改为内部user对象参数
        passwordService.updateUserRetryCache(loginUser, password);


        //TODO 验证用户信息
    }


    @Autowired
    public void setRemoteLogService(RemoteLogService remoteLogService) {
        this.remoteLogService = remoteLogService;
    }

    @Autowired
    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Autowired
    public void setRemoteUserService(RemoteUserService remoteUserService) {
        this.remoteUserService = remoteUserService;
    }
}
