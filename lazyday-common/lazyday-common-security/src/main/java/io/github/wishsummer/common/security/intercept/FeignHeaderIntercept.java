package io.github.wishsummer.common.security.intercept;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.github.wishsummer.common.core.constant.UserConstants;
import io.github.wishsummer.common.core.utils.IpUtils;
import io.github.wishsummer.common.core.utils.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 * Description:
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: FeingHeaderIntercept.java, 2025/3/3 下午2:32 $
 */
public class FeignHeaderIntercept implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = ServletUtils.getRequest();
        if (request != null) {
            String userKey = request.getHeader(UserConstants.USER_KEY);
            if (StringUtils.isNotBlank(userKey)) {
                requestTemplate.header(UserConstants.USER_KEY, userKey);
            }

            String username = request.getHeader(UserConstants.DETAILS_USERNAME);
            if (StringUtils.isNotBlank(username)) {
                requestTemplate.header(UserConstants.DETAILS_USERNAME, username);
            }

            String userId = request.getHeader(UserConstants.DETAILS_USER_ID);
            if (StringUtils.isNotBlank(userId)) {
                requestTemplate.header(UserConstants.DETAILS_USER_ID, userId);
            }
        }
        requestTemplate.header("X-Forwarded-For", IpUtils.getIpAddr());
    }
}
