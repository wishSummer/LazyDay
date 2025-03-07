package io.github.wishsummer.common.security.context;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.Map;

/**
 * Description:
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: SecurityContext.java, 2025/3/3 下午3:10 $
 */
public class SecurityContext {

    private static final TransmittableThreadLocal<Map<String, Object>> THREAD_LOCAL = new TransmittableThreadLocal<>();


}
