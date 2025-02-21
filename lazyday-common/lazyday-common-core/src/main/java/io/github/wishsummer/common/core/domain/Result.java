package io.github.wishsummer.common.core.domain;

import io.github.wishsummer.common.core.constant.HttpStatus;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;


    private int code;

    private String message;

    private T data;

    public static <T> Result<T> success() {
        return build(null, HttpStatus.SUCCESS.getCode(), null);
    }

    public static <T> Result<T> success(String message) {
        return build(null, HttpStatus.SUCCESS.getCode(), message);
    }

    public static <T> Result<T> success(T data, String message) {
        return build(data, HttpStatus.SUCCESS.getCode(), message);
    }

    public static <T> Result<T> success(T data) {
        return build(data, HttpStatus.SUCCESS.getCode(), null);
    }

    public static <T> Result<T> error() {
        return build(null, HttpStatus.INTERNAL_SERVER_ERROR.getCode(), null);
    }

    public static <T> Result<T> error(int code) {
        return build(null, code, null);
    }

    public static <T> Result<T> error(String message) {
        return build(null, HttpStatus.INTERNAL_SERVER_ERROR.getCode(), message);
    }

    public static <T> Result<T> error(int code, String message) {
        return build(null, code, message);
    }


    private static <T> Result<T> build(T data, int code, String message) {
        Result<T> result = new Result<>();
        result.setData(data);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
