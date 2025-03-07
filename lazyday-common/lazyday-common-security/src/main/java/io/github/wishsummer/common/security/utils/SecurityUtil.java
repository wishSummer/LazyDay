package io.github.wishsummer.common.security.utils;

import io.github.wishsummer.common.core.constant.Constants;
import io.github.wishsummer.common.core.exception.UnAuthcationException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 * Description: 针对 用户信息、token
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: SecurityUtil.java, 2025/3/3 下午2:58 $
 */
public class SecurityUtil {

    /**
     * 根据request获取请求token
     */
    public static String getToken(HttpServletRequest request) {
        if (request == null) {
            throw new UnAuthcationException("获取认证信息失败");
        }
        // 从header获取token标识
        String token = request.getHeader(Constants.AUTHENTICATION);
        return replaceTokenPrefix(token);
    }

    /**
     * 裁剪token前缀
     */
    public static String replaceTokenPrefix(String token) {
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.PREFIX)) {
            token = token.replaceFirst(Constants.PREFIX, "");
        }
        return token;
    }

}
