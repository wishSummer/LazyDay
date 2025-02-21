package io.github.wishsummer.utils;

import io.github.wishsummer.constant.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
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

    /**
     * 获取请求头 token 加密状态
     */
    public static String getToken(HttpServletRequest request) {
        String enToken = request.getHeader(Constants.AUTHENTICATION);
        return replaceTokenPrefix(enToken);
    }


    /**
     * 若token存在前缀则去除
     */
    private static String replaceTokenPrefix(String token) {
        if (StringUtils.isNotBlank(token) && token.startsWith(Constants.PREFIX)) {
            return token.replaceFirst(Constants.PREFIX, "");
        }
        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public static Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(Constants.SECRET).parseClaimsJws(token).getBody();
    }

}
