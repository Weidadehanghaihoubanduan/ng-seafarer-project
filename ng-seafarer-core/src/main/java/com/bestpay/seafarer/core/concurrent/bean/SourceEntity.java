package com.bestpay.seafarer.core.concurrent.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author dengyancan
 * @Date: 2023-12-29
 */
@Data
@Accessors(chain = true)
public class SourceEntity {

    private String uri;
    private String method = "POST";
    private String headerKey;
    private String headerValue;
    private String parameter;
    private String field;

    /**
     * 默认字段需要自行set
     */
    private Boolean dependency = false;

    public SourceEntity() { }

    public SourceEntity(String uri, String method, String headerKey,
                        String headerValue, String parameter, String field) {
        this.uri = uri;
        this.method = method;
        this.headerKey = headerKey;
        this.headerValue = headerValue;
        this.parameter = parameter;
        this.field = field; }}
