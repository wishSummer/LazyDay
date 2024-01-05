package io.github.wishsummer.service;

import io.github.wishsummer.constant.CacheConstants;
import io.github.wishsummer.enums.BusinessTypeEnum;
import io.github.wishsummer.exception.ServiceException;
import io.github.wishsummer.utils.SecurityUtils;
import io.github.wishsummer.wishsummer.domain.SysLogObject;
import io.github.wishsummer.wishsummer.model.UserInfo;
import io.github.wishsummer.wishsummer.remote.RemoteLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: PasswordService.java, 2024/1/5 10:44 $
 */
@Component
public class PasswordService {

    private RedisService redisService;

    private RemoteLogService remoteLogService;


    /**
     * 验证用户登陆次数
     */
    public void checkRetryCount(String username) {
        Integer retryCount = getRetryCount(username);
        if (retryCount >= CacheConstants.LOGIN_ERROR_RETRY_COUNT) {
            String errMsg = String.format("密码输入错误%S次，账户锁定%s分钟", CacheConstants.LOGIN_ERROR_RETRY_COUNT, CacheConstants.PASSWORD_ERROR_WITTING_TIME);
            remoteLogService.saveLog(setSysLogObject(username, 1, errMsg));
            throw new ServiceException("密码输入错误" + CacheConstants.LOGIN_ERROR_RETRY_COUNT + "次，账户锁定" + CacheConstants.PASSWORD_ERROR_WITTING_TIME + "分钟");
        }
    }

    /**
     * 验证用户登录信息
     * 更新用户登录失败次数
     * @param userInfo 用户信息
     * @param password 登陆输入的明文密码
     */
    public void updateUserRetryCache(UserInfo userInfo, String password) {
        Integer retryCount = getRetryCount(userInfo.getUsername());
        if (!SecurityUtils.matchesPassword(password, userInfo.getPassword())) {
            retryCount = retryCount + 1;
            String errMsg = String.format("密码输入错误%S次", retryCount);
            remoteLogService.saveLog(setSysLogObject(userInfo.getUsername(), 1, errMsg));
            redisService.setCacheObject(getCacheKey(userInfo.getUsername()), retryCount, CacheConstants.PASSWORD_ERROR_WITTING_TIME.longValue(), TimeUnit.MINUTES);
            throw new ServiceException("用户不存在或密码错误");
        }
        clearLoginRetryCache(userInfo.getUsername());
    }

    /**
     * 生成用户缓存键值
     *
     * @param username 用户名
     */
    public String getCacheKey(String username) {
        return CacheConstants.PASSWORD_ERROR_CONT_KEY + username;
    }

    /**
     * 获取用户登录次数
     */
    private Integer getRetryCount(String username) {
        Integer retryCount = redisService.getCacheObject(getCacheKey(username));
        if (retryCount == null) {
            retryCount = 0;
        }
        return retryCount;
    }

    /**
     * 清除用户登录次数缓存
     *
     * @param username
     */
    public void clearLoginRetryCache(String username) {
        if (redisService.hasKey(getCacheKey(username))) {
            redisService.deleteObject(getCacheKey(username));
        }
    }

    /**
     * @param username   用户登录名
     * @param logStatus  状态码 1异常；0正常；
     * @param logMessage
     */
    public SysLogObject setSysLogObject(String username, int logStatus, String logMessage) {
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
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    @Autowired
    public void setRemoteLogService(RemoteLogService remoteLogService) {
        this.remoteLogService = remoteLogService;
    }
}
