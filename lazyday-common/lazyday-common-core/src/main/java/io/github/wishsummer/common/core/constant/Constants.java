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
     * 登陆尝试错误等待时间。单位分钟
     */
    public static final Integer PASSWORD_ERROR_WITTING_TIME = 5;


}
