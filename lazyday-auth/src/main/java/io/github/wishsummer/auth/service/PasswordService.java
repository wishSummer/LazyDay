package io.github.wishsummer.auth.service;

import io.github.wishsummer.api.domain.SysLogObject;
import io.github.wishsummer.api.domain.SysUserObject;
import io.github.wishsummer.api.remote.RemoteLogService;
import io.github.wishsummer.common.core.constant.CacheConstants;
import io.github.wishsummer.common.core.constant.Constants;
import io.github.wishsummer.common.core.exception.ServiceException;
import io.github.wishsummer.common.core.service.RedisService;
import io.github.wishsummer.common.log.enums.BusinessTypeEnum;
import io.github.wishsummer.auth.utils.SecurityUtils;
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
        if (retryCount >= Constants.LOGIN_ERROR_RETRY_COUNT) {
            String errMsg = String.format("密码输入错误%S次，账户锁定%s分钟", Constants.LOGIN_ERROR_RETRY_COUNT, Constants.PASSWORD_ERROR_WITTING_TIME);
            remoteLogService.saveLog(getSysLogObject(username, 1, errMsg));
            throw new ServiceException("密码输入错误" + Constants.LOGIN_ERROR_RETRY_COUNT + "次，账户锁定" + Constants.PASSWORD_ERROR_WITTING_TIME + "分钟");
        }
    }

    /**
     * 验证用户登录信息
     * 更新用户登录失败次数
     *
     * @param sysUserObject 用户信息
     * @param password  登陆输入的明文密码
     */
    public void VerifyLogin(SysUserObject sysUserObject, String password) {
        Integer retryCount = getRetryCount(sysUserObject.getPassword());
        if (!SecurityUtils.matchesPassword(password, sysUserObject.getPassword())) {
            retryCount = retryCount + 1;
            String errMsg = String.format("密码输入错误%S次", retryCount);
            remoteLogService.saveLog(getSysLogObject(sysUserObject.getUsername(), 1, errMsg));
            redisService.setCacheObject(getCacheKey(sysUserObject.getUsername()), retryCount, Constants.PASSWORD_ERROR_WITTING_TIME.longValue(), TimeUnit.MINUTES);
            throw new ServiceException("用户不存在或密码错误");
        }
        clearLoginRetryCache(sysUserObject.getUsername());
    }

    /**
     * 生成用户缓存键值
     * @param username 用户名
     */
    public String getCacheKey(String username) {
        return CacheConstants.PASSWORD_ERROR_COUNT_KEY + username;
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
    public SysLogObject getSysLogObject(String username, int logStatus, String logMessage) {
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
