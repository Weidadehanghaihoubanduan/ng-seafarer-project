package com.bestpay.seafarer.integration.annotation;

import java.lang.annotation.*;

/**
 * @author dengyancan
 * @Date: 2023-12-29
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoRepeatSubmit {

    /**
     * 设置请求锁定时间(默认三秒)
     */
    int lockTime() default 3 * 1000;
}