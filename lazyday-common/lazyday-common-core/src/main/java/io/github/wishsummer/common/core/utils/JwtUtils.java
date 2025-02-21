package io.github.wishsummer.common.core.utils;

import io.github.wishsummer.common.core.constant.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: TokenService.java, 2024/1/5 16:58 $
 */
@Component
public class JwtUtils {


    /**
     * 创建Token
     */
    public static String createToken(Claims claims) {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.ES512, Constants.SECRET).compact();
    }

    /**
     * 解析Token
     */
    public static Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(Constants.SECRET).parseClaimsJws(token).getBody();
    }

    /**
     * 获取Token含有键值对
     */
    public static String getValue(Claims claims, String key) {
        return StringUtils.defaultIfEmpty(claims.get(key).toString(), "");

    }

}
