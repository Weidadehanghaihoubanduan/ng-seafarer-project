package com.bestpay.seafarer.common.result;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: dengyancan
 * @Date: 2023/12/15 11:07
 */
@Slf4j
@SuppressWarnings("ALL")
public class R {

    /**
     * 返回中态数据
     * @param data 数据
     * @param <T> 泛型T对象
     * @return date
     */
    public static <T> RD<T> medium(T data){
        return new RD<T>(null, null, data);
    }

    /**
     * @param <T> 泛型T对象
     * @return 成功返回的对象信息
     */
    public static <T> RD<T> success(String msg){
        return new RD<T>(200, msg, null);
    }

    /**
     * @param msg  信息
     * @param data 响应数据
     * @param <T> 泛型T对象
     * @return 成功返回的对象信息
     */
    public static <T> RD<T> success(String msg, T data){
        return new RD<T>(200, msg, data);
    }

    /**
     * @param code 状态码
     * @param msg 信息
     * @param <T> 泛型T对象
     * @return 失败返回的对象信息
     */
    public static <T> RD<T> failure(Integer code, String msg){
        return new RD<T>(code, msg, null);
    }

    /**
     * @param code 状态码
     * @param msg 信息
     * @param <T> 泛型T对象
     * @return 用户认证失败返回的对象信息
     */
    public static <T> RD<T> authorizationFailed(Integer code, String msg){
        return new RD<T>(code, msg, null);
    }

    /**
     * @param code 状态码
     * @param msg 信息
     * @param <T> 泛型T对象
     * @return 用户认证失败返回的对象信息
     */
    public static <T> RD<T> authorizationFailed(Integer code, String msg, T data){
        return new RD<T>(code, msg, data);
    }

    /**
     * @param msg 信息
     * @param <T> 泛型T对象
     * @return 失败返回的对象信息
     */
    public static <T> RD<T> failure(String msg){
        return new RD<T>(500, msg, null);
    }
}
