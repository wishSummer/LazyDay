package io.github.wishsummer.gateway.filter;

import io.github.wishsummer.common.core.utils.EscapeUtil;
import io.github.wishsummer.gateway.config.properties.XssProperties;
import io.netty.buffer.ByteBufAllocator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: XssFilter.java, 2025/2/20 下午3:57 $
 */
@Component
@ConditionalOnProperty(value = "security.xss.enabled", havingValue = "true")
public class XssFilter implements GlobalFilter, Ordered {

    private XssProperties xssProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (!xssProperties.getEnabled()) {
            return chain.filter(exchange);
        }

        HttpMethod method = request.getMethod();
        if (method == HttpMethod.GET || method == HttpMethod.DELETE) {
            return chain.filter(exchange);
        }

        if (!isJsonRequest(exchange)) {
            return chain.filter(exchange);
        }
        String url = request.getURI().getPath();
        AntPathMatcher matcher = new AntPathMatcher();
        String string = xssProperties.getExcludes().stream().filter(item -> matcher.match(item, url)).findAny().orElseGet(null);
        if (StringUtils.isNotBlank(string)) {
            return  chain.filter(exchange);
        }

        return null;
    }


    private ServerHttpRequestDecorator requestDecorator(ServerWebExchange exchange) {

        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public Flux<DataBuffer> getBody() {
                Flux<DataBuffer> body = super.getBody();

                return body.buffer().map(dataBuffers -> {
                    // 解析body数据
                    DefaultDataBufferFactory defaultDataBufferFactory = new DefaultDataBufferFactory();
                    DefaultDataBuffer join = defaultDataBufferFactory.join(dataBuffers);
                    byte[] bodyByte = new byte[join.readableByteCount()];
                    join.read(bodyByte);
                    DataBufferUtils.release(join);
                    String content = new String(bodyByte, StandardCharsets.UTF_8);

                    // 过滤body xss
                    EscapeUtil.clean(content);

                    //转译
                    byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
                    NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
                    NettyDataBuffer nettyDataBuffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
                    nettyDataBuffer.write(bytes);
                    return nettyDataBuffer;
                });
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                // 修改请求体body后，body长度发生改变。
                httpHeaders.remove(HttpHeaders.CONTENT_LENGTH);
                httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                return httpHeaders;
            }
        };
    }


    private Boolean isJsonRequest(ServerWebExchange exchange) {
        String type = exchange.getRequest().getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
        return StringUtils.startsWithIgnoreCase(type, MediaType.APPLICATION_JSON_VALUE);

    }

    @Autowired
    public void setXssProperties(XssProperties xssProperties) {
        this.xssProperties = xssProperties;
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
