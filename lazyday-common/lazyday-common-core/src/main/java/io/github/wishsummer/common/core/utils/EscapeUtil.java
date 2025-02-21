package io.github.wishsummer.common.core.utils;

import org.bouncycastle.i18n.filter.HTMLFilter;

/**
 * Description:
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: EscapeUtil.java, 2025/2/20 下午4:47 $
 */
public class EscapeUtil {

    /**
     * 过滤 xss
     */
    public static String clean(String str) {
        return new HTMLFilter().doFilter(str);
    }

}
