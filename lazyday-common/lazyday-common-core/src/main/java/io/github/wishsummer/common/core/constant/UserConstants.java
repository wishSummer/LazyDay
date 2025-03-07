package io.github.wishsummer.common.core.constant;

public class UserConstants {

    /**
     * 密码长度限制
     */
    public static final int PASSWORD_MIN_LENGTH = 6;

    public static final int PASSWORD_MAX_LENGTH = 10;

    public static final int USERNAME_MIN_LENGTH = 5;

    public static final int USERNAME_MAX_LENGTH = 12;

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
