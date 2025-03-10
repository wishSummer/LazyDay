package io.github.wishsummer.common.core.utils;

import com.alibaba.fastjson2.JSON;
import io.github.wishsummer.common.core.constant.Constants;
import io.github.wishsummer.common.core.domain.Result;
import io.github.wishsummer.common.core.exception.ServiceException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: 针对 HttpServlet 请求、响应
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: ServletUtils.java, 2025/3/3 下午2:58 $
 */
@Slf4j
public class ServletUtils {


    public static ServletRequestAttributes getRequestAttributes() {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            return (ServletRequestAttributes) requestAttributes;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        try {
            ServletRequestAttributes requestAttributes = getRequestAttributes();
            return requestAttributes == null ? null : requestAttributes.getRequest();
        } catch (Exception e) {
            log.error("getRequestError:", e);
            return null;
        }
    }

    public static Map<String, Object> getTokenInfo() {
        try {
            return Jwts.parser().setSigningKey(Constants.SECRET).parseClaimsJws(ServletUtils.getRequest().getHeader(Constants.AUTHENTICATION)).getBody();
        } catch (Exception e) {
            throw new ServiceException("登陆状态失效，请重新登录");
        }
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse() {
        try {
            return getRequestAttributes().getResponse();
        } catch (Exception e) {
            log.error("getResponseError:{}", e);
            return null;
        }
    }

    /**
     * 获取请求参数
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * 获得所有请求参数
     *
     * @param request 请求对象{@link ServletRequest}
     * @return Map
     */
    public static Map<String, String[]> getParams(ServletRequest request) {
        final Map<String, String[]> map = request.getParameterMap();
        return Collections.unmodifiableMap(map);
    }

    /**
     * 获取所有请求参数
     */
    public static Map<String, String> getParamMap(ServletRequest request) {
        if (request == null) {
            return new HashMap<>();
        }
        Map<String, String> params = new HashMap<>();
        for (Map.Entry<String, String[]> entry : getParams(request).entrySet()) {
            params.put(entry.getKey(), StringUtils.join(entry.getValue(), ","));
        }
        return params;
    }

    /**
     * 获取请求头
     */
    public static String getHeader(HttpServletRequest request, String name) {
        String value = request.getHeader(name);
        if (StringUtils.isEmpty(value)) {
            return StringUtils.EMPTY;
        }
        return urlDecode(value);
    }

    /**
     * 内容解码
     *
     * @param str 内容
     * @return 解码后的内容
     */
    public static String urlDecode(String str) {
        try {
            return URLDecoder.decode(str, Constants.UTF8);
        } catch (UnsupportedEncodingException e) {
            return StringUtils.EMPTY;
        }
    }

    /**
     * 内容编码
     *
     * @param str 内容
     * @return 编码后的内容
     */
    public static String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, Constants.UTF8);
        } catch (UnsupportedEncodingException e) {
            return StringUtils.EMPTY;
        }
    }

    public static Mono<Void> webFluxResponseWrite(ServerHttpResponse response, Object value, int code) {
        return webFluxResponseWrite(response, HttpStatus.OK, value, code);
    }

    public static Mono<Void> webFluxResponseWrite(ServerHttpResponse response, HttpStatus status, Object value, int code) {
        return webFluxResponseWrite(response, MediaType.APPLICATION_JSON_VALUE, status, value, code);
    }


    /**
     * web flux 响应模板
     */
    public static Mono<Void> webFluxResponseWrite(ServerHttpResponse response, String contentType, HttpStatus status, Object value, int code) {
        response.setStatusCode(status);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, contentType);
        Result<Object> result = Result.error(code, value.toString());
        DataBuffer wrap = response.bufferFactory().wrap(JSON.toJSONString(result).getBytes());
        return response.writeWith(Mono.just(wrap));

    }


}
