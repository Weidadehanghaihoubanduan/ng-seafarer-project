package com.bestpay.seafarer.core.concurrent.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author dengyancan
 * @Date: 2023-12-29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TargetEntity {

    private String uri;
    private String method = "POST";
    private String headerKey;
    private String headerValue;
    private String parameter;
    private String traceLogId;

    /**
     * 标识位字段需要自行set
     */
    private String account = String.valueOf(Thread.currentThread().getId());

}
