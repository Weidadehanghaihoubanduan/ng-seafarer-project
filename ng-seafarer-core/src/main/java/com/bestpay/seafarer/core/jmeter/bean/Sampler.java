package com.bestpay.seafarer.core.jmeter.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * @author dengyancan
 * @Date: 2023-11-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Sampler implements Serializable {

    /**
     * 请求名称
     */
    private String name;

    /**
     * 请求注释
     */
    private String comment;

    /**
     * 协议
     */
    private String protocol = "https";

    /**
     * 服务器名称/IP
     */
    private String domain;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 请求路径
     */
    private String path;

    /**
     * 请求类型
     */
    private String method = "POST";

    /**
     * 请求参数
     */
    private Map<String, Object> argument;

    /**
     * 连接超时时间
     */
    private String timeout = "3000";


    private static final long serialVersionUID = -6989576277721022727L;
}
