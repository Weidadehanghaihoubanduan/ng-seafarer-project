package com.bestpay.seafarer.common.exception;

import lombok.Getter;

/**
 * @Author: dengyancan
 * @Date: 2023/12/15 16:52
 */
@Getter
public class BusinessBaseException extends RuntimeException {

    private final Integer code;

    private static final long serialVersionUID = 6936253345670413869L;

    /**
     *  使用枚举传参
     * @param businessFailedBaseEnum 异常枚举
     */
    public BusinessBaseException(BusinessBaseEnum businessFailedBaseEnum) {
        super(businessFailedBaseEnum.getMsg());
        this.code = businessFailedBaseEnum.getCode();
    }

    /**
     *  使用自定义消息
     * @param code 值
     * @param msg 详情
     */
    @SuppressWarnings("ALL")
    public BusinessBaseException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }
}