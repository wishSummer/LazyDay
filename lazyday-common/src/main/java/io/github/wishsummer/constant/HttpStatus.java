package io.github.wishsummer.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 响应状态码
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public enum HttpStatus {

    SUCCESS(200, "成功"),

    WARN(400, "请求错误"),

    UNAUTHORIZED(401, "未认证"),

    FORBIDDEN(403, "拒绝访问"),

    NOT_FOUND(404, "未找到服务"),

    REQUEST_TIMEOUT(408, "访问超时"),

    INTERNAL_SERVER_ERROR(500, "服务器错误");

    private final int code;

    private final String info;

}
