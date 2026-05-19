package com.ai.learning.common;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.ERROR);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> unauthorized() {
        return error(ResultCode.UNAUTHORIZED, "未授权，请先登录");
    }

    public static <T> Result<T> forbidden() {
        return error(ResultCode.FORBIDDEN, "没有权限访问");
    }

    public static <T> Result<T> notFound() {
        return error(ResultCode.NOT_FOUND, "资源不存在");
    }

    public static <T> Result<T> badRequest(String message) {
        return error(ResultCode.BAD_REQUEST, message);
    }
}
