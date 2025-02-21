package io.github.wishsummer.common.core.constant;

/**
 * Description: redis key值常量
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: CacheConstants.java, 2023/12/28 17:24 $
 */
public class CacheConstants {

    /**
     * 缓存有效期，默认720（分钟）
     */
    public final static long EXPIRATION = 720;

    /**
     * 权限缓存前缀
     */
    public final static String LOGIN_TOKEN_KEY = "login_tokens:";


    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PASSWORD_ERROR_COUNT_KEY = "password_error_count:";

    /**
     * IP 黑名单
     */
    public static  final String SYS_LOGIN_BLACKIPLIST = "SYS_LOGIN_BLACKIPLIST";

}
