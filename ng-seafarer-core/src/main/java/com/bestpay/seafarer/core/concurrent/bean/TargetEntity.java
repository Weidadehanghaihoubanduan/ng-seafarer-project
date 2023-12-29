package com.bestpay.seafarer.core.concurrent.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author dengyancan
 * @Date: 2023-12-29
 */
@Data
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

    public TargetEntity() { }

    public TargetEntity(String uri, String method, String headerKey,
                        String headerValue, String parameter, String traceLogId) {
        this.uri = uri;
        this.method = method;
        this.headerKey = headerKey;
        this.headerValue = headerValue;
        this.parameter = parameter;
        this.traceLogId = traceLogId; }}
