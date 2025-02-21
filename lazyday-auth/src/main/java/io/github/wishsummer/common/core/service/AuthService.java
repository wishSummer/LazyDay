package io.github.wishsummer.service;

import io.github.wishsummer.constant.CacheConstants;
import io.github.wishsummer.constant.Constants;
import io.github.wishsummer.constant.HttpStatus;
import io.github.wishsummer.constant.UserConstants;
import io.github.wishsummer.controller.form.RegisterForm;
import io.github.wishsummer.domain.Result;
import io.github.wishsummer.domain.SysUserObject;
import io.github.wishsummer.exception.ServiceException;
import io.github.wishsummer.model.LoginUser;
import io.github.wishsummer.remote.RemoteLogService;
import io.github.wishsummer.remote.RemoteUserService;
import io.github.wishsummer.utils.FormatUtils;
import io.github.wishsummer.utils.IpUtils;
import io.github.wishsummer.utils.SecurityUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Service
public class AuthService {

    private RemoteLogService remoteLogService;

    private RemoteUserService remoteUserService;

    private PasswordService passwordService;

    private RedisService redisService;

    private final static Integer USER_NAME_MAX_LENGTH = 12;

    private final static Integer USER_NAME_MIN_LENGTH = 6;

    private final static Integer PASSWORD_MAX_LENGTH = 16;

    private final static Integer PASSWORD_MINLENGTH = 8;

    private final static String USERNAME_PATTERN = "^[A-Za-z][A-Za-z0-9]{5,11}$";

    private final static String PASSWORD_PATTERN = "^[A-Za-z0-9!@#$%^&*()_+=-]{8,16}$";

    private final static long expireTime = CacheConstants.EXPIRATION;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;


    public Map<String, Object> login(String username, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password)) {
            remoteLogService.saveLog(passwordService.getSysLogObject(username, HttpStatus.FORBIDDEN.getCode(), "用户/密码必须填写"));
            throw new ServiceException("用户/密码必须填写");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            remoteLogService.saveLog(passwordService.getSysLogObject(username, HttpStatus.FORBIDDEN.getCode(), "用户密码不在指定范围"));
            throw new ServiceException("用户密码不在指定范围");
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            remoteLogService.saveLog(passwordService.getSysLogObject(username, HttpStatus.FORBIDDEN.getCode(), "用户名不在指定范围"));
            throw new ServiceException("用户名不在指定范围");
        }

        //TODO IP黑名单拦截 未选择黑名单存放方式
        IpUtils.getIpAddr();
        Object blackStr = redisService.getCacheObject(CacheConstants.SYS_LOGIN_BLACKIPLIST);
        if (blackStr instanceof String && IpUtils.isMatchedIp((String) blackStr, IpUtils.getIpAddr())) {
            remoteLogService.saveLog(passwordService.getSysLogObject(username, HttpStatus.FORBIDDEN.getCode(), "很遗憾，访问IP已被列入系统黑名单"));
            throw new ServiceException("很遗憾，访问IP已被列入系统黑名单");
        }


        // redis验证登录次数
        passwordService.checkRetryCount(username);

        // 查询用户信息
        Result<LoginUser> userInfoResult = remoteUserService.getUserInfo(username);
        if (userInfoResult == null || userInfoResult.getData() == null) {
            remoteLogService.saveLog(passwordService.getSysLogObject(null, 0, "登录用户不存在"));
            throw new ServiceException("登录用户" + username + "不存在");
        }
        if (Result.SUCCESS != userInfoResult.getCode()) {
            throw new ServiceException(userInfoResult.getMessage());
        }
        LoginUser loginUser = userInfoResult.getData();

        // 验证账号密码，更新用户登录尝试次数
        passwordService.VerifyLogin(loginUser, password);

        remoteLogService.saveLog(passwordService.getSysLogObject(username, HttpStatus.SUCCESS.getCode(), "用户登录成功"));

        // 生成用户token
        return createToken(loginUser);

    }


    /**
     * 注销用户登陆状态
     */
    public Result logout(HttpServletRequest request) {
        String entoken = SecurityUtils.getToken(request);
        if (StringUtils.isNotBlank(entoken)) {
            Claims claims = SecurityUtils.parseToken(entoken);
            redisService.deleteObject(CacheConstants.LOGIN_TOKEN_KEY + FormatUtils.convert(claims.get(Constants.USER_KEY)));
            remoteLogService.saveLog(passwordService.getSysLogObject(FormatUtils.convert(claims.get(Constants.DETAILS_USERNAME)), HttpStatus.SUCCESS.getCode(), "用户正常下线"));
        }
        return Result.success();
    }


    public Result<?> register(RegisterForm registerBody) {
        Result<LoginUser> userInfo = remoteUserService.getUserInfo(registerBody.getUsername());
        if (userInfo != null) {
            throw new ServiceException("当前用户已存在");
        }
        // TODO 正则匹配规范
        if (registerBody.getUsername().length() > USER_NAME_MAX_LENGTH || registerBody.getUsername().length() < USER_NAME_MIN_LENGTH || Pattern.matches(USERNAME_PATTERN, registerBody.getUsername())) {
            throw new ServiceException("用户名不符合规范");
        }
        if (registerBody.getPassword().length() > PASSWORD_MAX_LENGTH || registerBody.getPassword().length() < PASSWORD_MINLENGTH || Pattern.matches(PASSWORD_PATTERN, registerBody.getPassword())) {
            throw new ServiceException("用户密码不符合规范");
        }
        //TODO 完善注册流程
        SysUserObject sysUserObject = new SysUserObject();
        sysUserObject.setUsername(registerBody.getUsername());
        sysUserObject.setPassword(registerBody.getPassword());
        sysUserObject.setNickName(StringUtils.defaultIfBlank(registerBody.getNickName(), registerBody.getUsername()));
        if (!remoteUserService.registerUser(sysUserObject).getCode().equals(HttpStatus.SUCCESS)) {
            throw new ServiceException("注册失败");
        }

        remoteLogService.saveLog(passwordService.getSysLogObject(registerBody.getNickName(), HttpStatus.INTERNAL_SERVER_ERROR.getCode(), "用户注册失败"));
        return Result.success();
    }


    /**
     * 刷新token
     */
    public Result refreshToken(HttpServletRequest request) {
        String entoken = SecurityUtils.getToken(request);
        if (StringUtils.isNotBlank(entoken)) {
            Claims claims = SecurityUtils.parseToken(entoken);
            LoginUser loginUser = redisService.getCacheObject(CacheConstants.LOGIN_TOKEN_KEY + claims.get(Constants.USER_KEY));
            if (loginUser != null) {
                refreshToken(loginUser);
                return Result.success();
            }
        }
        return Result.error("登录状态异常");
    }

    private Map<String, Object> createToken(LoginUser loginUser) {

        String token = UUID.randomUUID().toString();
        Integer userId = loginUser.getSysUserObject().getUserId();
        String userName = loginUser.getSysUserObject().getUsername();
        loginUser.setToken(token);
        refreshToken(loginUser);

        // Jwt存储信息
        Map<String, Object> claimsMap = new HashMap<String, Object>();
        claimsMap.put(Constants.USER_KEY, token);
        claimsMap.put(Constants.DETAILS_USER_ID, userId);
        claimsMap.put(Constants.DETAILS_USERNAME, userName);

        // 接口返回信息
        Map<String, Object> rspMap = new HashMap<String, Object>();
        rspMap.put("access_token", createToken(claimsMap));
        rspMap.put("expires_in", expireTime);
        return rspMap;

    }


    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    public static String createToken(Map<String, Object> claims) {
        String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, Constants.SECRET).compact();
        return token;
    }


    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = CacheConstants.LOGIN_TOKEN_KEY + loginUser.getToken();
        redisService.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
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
