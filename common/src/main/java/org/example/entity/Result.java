package org.example.entity;

import lombok.Data;

/**
 * 表现层格式统一
 *
 * @param <T>
 */
@Data
public class Result<T> {

    /**
     * 成功状态码
     */
    public static final int CODE_SUCCESS = 200;
    /**
     * 错误状态码（服务器内部错误）
     */
    public static final int CODE_ERROR = 500;
    /**
     * 默认成功消息
     */
    private static final String DEFAULT_SUCCESS_MSG = "操作成功";


    private int code;
    private String message;
    private T data;


    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // ==================== 成功方法 ====================
    public static <T> Result<T> success() {
        return new Result<>(CODE_SUCCESS, DEFAULT_SUCCESS_MSG);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(CODE_SUCCESS, DEFAULT_SUCCESS_MSG, data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(CODE_SUCCESS, message, data);
    }

    // ==================== 错误方法 ====================
    public static <T> Result<T> error(String message) {
        return new Result<>(CODE_ERROR, message);
    }

    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message);
    }

}
