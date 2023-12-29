package com.bestpay.seafarer.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: dengyancan
 * @Date: 2023/12/15 11:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("ALL")
public class RD<T> implements Serializable {

    private static final long serialVersionUID = -2584318297306924375L;

    /**
     * 统一返回码
     */
    public Integer code;

    /**
     * 统一错误消息
     */
    public String msg;

    /**
     * 结果对象
     */
    public T data;

}

