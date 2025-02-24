package io.github.wishsummer.gateway.filter;

import io.github.wishsummer.common.core.constant.CacheConstants;
import io.github.wishsummer.common.core.constant.Constants;
import io.github.wishsummer.common.core.service.RedisService;
import io.github.wishsummer.common.core.utils.JwtUtils;
import io.github.wishsummer.common.core.utils.ServletUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Description: 用户认证令牌Token过滤
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: AuthFilter.java, 2025/2/20 上午10:09 $
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    public RedisService redisService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutate = request.mutate();

        // 验证用户登录状态
        String token = getToken(request);
        if (StringUtils.isBlank(token)) {
            return unauthorizedResponse(exchange, "令牌不能为空");
        }
        Claims claims = JwtUtils.parseToken(token);
        if (claims == null) {
            return unauthorizedResponse(exchange, "令牌已过期或验证不正确!");
        }

        String userKey = JwtUtils.getValue(claims, Constants.USER_KEY);
        if (redisService.hasKey(userKey)) {
            return unauthorizedResponse(exchange, "登录状态已过期");
        }
        String userId = JwtUtils.getValue(claims, Constants.DETAILS_USER_ID);
        String username = JwtUtils.getValue(claims, Constants.DETAILS_USERNAME);
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(username)) {
            return unauthorizedResponse(exchange, "令牌验证失败");
        }

        // 添加用户信息到请求头
        mutate.header(Constants.USER_KEY, ServletUtils.urlEncode(userKey));
        mutate.header(Constants.DETAILS_USER_ID, ServletUtils.urlEncode(userId));
        mutate.header(Constants.DETAILS_USERNAME, ServletUtils.urlEncode(username));


        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }

    private String getToken(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst(Constants.AUTHENTICATION);

        if (StringUtils.isNotBlank(token) && token.startsWith(Constants.PREFIX)) {
            token = token.replace(Constants.PREFIX, "");
        }
        return token;
    }


    /**
     * 异常响应结果
     */
    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String msg) {
        log.error("[鉴权异常处理]请求路径:{},错误信息:{}", exchange.getRequest().getPath(), msg);
        return ServletUtils.webFluxResponseWrite(exchange.getResponse(), msg, HttpStatus.UNAUTHORIZED.value());
    }

    @Autowired
    public void setRedisTemplate(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
