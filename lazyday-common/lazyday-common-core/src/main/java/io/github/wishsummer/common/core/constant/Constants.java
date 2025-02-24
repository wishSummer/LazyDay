package io.github.wishsummer.common.core.constant;

public class Constants {

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 令牌前缀
     */
    public static final String PREFIX = "Bearer ";

    /**
     * 令牌自定义标识
     */
    public static final String AUTHENTICATION = "Authorization";

    /**
     * Token 加密字符
     */
    public final static String SECRET = "abcdefghijklmnopqrstuvwxyz";


    /**
     * 尝试登录失败次数 仅作为尝试次数上线，用于判断
     */
    public static final Integer LOGIN_ERROR_RETRY_COUNT = 5;

    /**
     * 单位分钟
     */
    public static final Integer PASSWORD_ERROR_WITTING_TIME = 5;

    /**
     * 标识token的uuid，redis存储用户登录信息 redis key = LOGIN_TOKEN_KEY + token.get(USER_KEY)
     */
    public static final String USER_KEY = "user_key";

    /**
     * 用户ID字段
     */
    public static final String DETAILS_USER_ID = "user_id";

    /**
     * 登录用户
     */
    public static final String LOGIN_USER = "login_user";

    /**
     * 用户名字段
     */
    public static final String DETAILS_USERNAME = "username";
}
