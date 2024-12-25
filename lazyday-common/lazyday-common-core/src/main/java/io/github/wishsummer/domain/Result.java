package io.github.wishsummer.domain;

import io.github.wishsummer.constant.HttpStatus;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final HttpStatus SUCCESS = HttpStatus.SUCCESS;

    private HttpStatus code;

    private String message;

    private T data;

    public static <T> Result<T> success() {
        return build(null, SUCCESS, null);
    }

    public static <T> Result<T> success(String message) {
        return build(null, SUCCESS, message);
    }

    public static <T> Result<T> success(T data, String message) {
        return build(data, null, message);
    }

    public static <T> Result<T> success(T data) {
        return build(data, null, null);
    }

    public static <T> Result<T> error() {
        return build(null, HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    public static <T> Result<T> error(HttpStatus code) {
        return build(null, code, null);
    }

    public static <T> Result<T> error(String message) {
        return build(null, HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public static <T> Result<T> error(HttpStatus code, String message) {
        return build(null, code, message);
    }


    private static <T> Result<T> build(T data, HttpStatus code, String message) {
        Result<T> result = new Result<>();
        result.setData(data);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
