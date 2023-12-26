package io.github.domain;

import io.github.constant.HttpStatus;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private HttpStatus code;

    private String message;

    private T data;

    public static <T> Result<T> success() {
        return build(null, HttpStatus.SUCCESS, null);
    }

    public static <T> Result<T> success(String message) {
        return build(null, HttpStatus.SUCCESS, message);
    }

    public static <T> Result<T> success(T data, String message) {
        return build(data, null, message);
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
