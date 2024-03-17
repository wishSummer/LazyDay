package io.github.wishsummer.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Description:
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: SecurityUtils.java, 2024/1/5 11:10 $
 */
public class SecurityUtils {

    /**
     * 判断密码是否匹配
     */
    public static boolean matchesPassword(String password, String encodePassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(password, encodePassword);
    }

}
