package io.github.wishsummer.service;

import io.github.wishsummer.constant.CacheConstants;
import io.github.wishsummer.constant.HttpStatus;
import io.github.wishsummer.constant.UserConstants;
import io.github.wishsummer.domain.Result;
import io.github.wishsummer.exception.ServiceException;
import io.github.wishsummer.model.LoginUser;
import io.github.wishsummer.remote.RemoteLogService;
import io.github.wishsummer.remote.RemoteUserService;
import io.github.wishsummer.utils.IpUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private RemoteLogService remoteLogService;

    private RemoteUserService remoteUserService;

    private PasswordService passwordService;
    private RedisService redisService;


    public void login(String username, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password)) {
            remoteLogService.saveLog(passwordService.setSysLogObject(username, HttpStatus.FORBIDDEN.getCode(), "用户/密码必须填写"));
            throw new ServiceException("用户/密码必须填写");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            remoteLogService.saveLog(passwordService.setSysLogObject(username, HttpStatus.FORBIDDEN.getCode(), "用户密码不在指定范围"));
            throw new ServiceException("用户密码不在指定范围");
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            remoteLogService.saveLog(passwordService.setSysLogObject(username, HttpStatus.FORBIDDEN.getCode(), "用户名不在指定范围"));
            throw new ServiceException("用户名不在指定范围");
        }

        //TODO IP黑名单拦截 未选择白名单存放方式
        IpUtils.getIpAddr();
        Object blackStr = redisService.getCacheObject(CacheConstants.SYS_LOGIN_BLACKIPLIST);
        if (blackStr instanceof String && IpUtils.isMatchedIp((String) blackStr, IpUtils.getIpAddr())) {
            remoteLogService.saveLog(passwordService.setSysLogObject(username, HttpStatus.FORBIDDEN.getCode(), "很遗憾，访问IP已被列入系统黑名单"));
            throw new ServiceException("很遗憾，访问IP已被列入系统黑名单");
        }


        // redis验证登录次数
        passwordService.checkRetryCount(username);

        // 查询用户信息
        Result<LoginUser> userInfoResult = remoteUserService.getUserInfo(username);
        if (userInfoResult == null || userInfoResult.getData() == null) {
            remoteLogService.saveLog(passwordService.setSysLogObject(null, 0, "登录用户不存在"));
            throw new ServiceException("登录用户" + username + "不存在");
        }
        if (Result.SUCCESS != userInfoResult.getCode()) {
            throw new ServiceException(userInfoResult.getMessage());
        }
        LoginUser loginUser = userInfoResult.getData();

        // 验证账号密码，更新用户登录尝试次数
        passwordService.VerifyLogin(loginUser, password);

        remoteLogService.saveLog(passwordService.setSysLogObject(username, HttpStatus.SUCCESS.getCode(), "用户登录成功"));

        // TODO 生成用户token


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

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }
}
