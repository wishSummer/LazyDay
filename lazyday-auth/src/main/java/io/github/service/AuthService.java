package io.github.service;

import io.github.constant.UserConstants;
import io.github.domain.SysLogObject;
import io.github.enums.BusinessTypeEnum;
import io.github.exception.ServiceException;
import io.github.remote.RemoteLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private RemoteLogService remoteLogService;


    public void login(String username, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password)) {
            remoteLogService.saveLog(setSysLogObject(username, 1, "用户/密码必须填写"));
            throw new ServiceException("用户/密码必须填写");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            remoteLogService.saveLog(setSysLogObject(username, 1, "用户密码不在指定范围"));
            throw new ServiceException("用户密码不在指定范围");
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            remoteLogService.saveLog(setSysLogObject(username, 1, "用户名不在指定范围"));
            throw new ServiceException("用户名不在指定范围");
        }

        //TODO IP黑名单拦截

        //TODO 查询用户信息

        //TODO 验证用户信息
    }

    private SysLogObject setSysLogObject(String username, int logStatus, String logMessage) {
        SysLogObject sysLogObject = new SysLogObject();
        sysLogObject.setMethod("AuthService.login()");
        sysLogObject.setRequestMethod("POST");
        sysLogObject.setBusinessType(BusinessTypeEnum.GRANT.getCode());
        sysLogObject.setLogName(username);
        sysLogObject.setStatus(logStatus);
        sysLogObject.setErrorMsg(logMessage);
        sysLogObject.setLogTime(LocalDateTime.now());
        return sysLogObject;
    }

    @Autowired
    public void setRemoteLogService(RemoteLogService remoteLogService) {
        this.remoteLogService = remoteLogService;
    }
}
