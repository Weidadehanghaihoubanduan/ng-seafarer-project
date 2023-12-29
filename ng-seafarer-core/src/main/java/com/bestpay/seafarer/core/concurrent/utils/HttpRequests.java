package com.bestpay.seafarer.core.concurrent.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import com.bestpay.seafarer.core.concurrent.constant.HttpConstant;

/**
 * @Author: dengyancan
 * @Date: 2022/12/15
 */
public class HttpRequests {

    /**
     * 执行方法
     * @param uri 接口
     * @param method 方法
     * @param headerKey header名称
     * @param headerValue header值
     * @param parameter 参数
     * @return HttpResponse
     */
    public static HttpResponse doMethod(String uri, String method, String headerKey, String headerValue, String parameter) {

        if(HttpConstant.REQUEST_METHOD.equals(method)) {
            return doPost(uri, headerKey, headerValue, parameter); }
        return doGet(uri, headerKey, headerValue, parameter);
    }

    /**
     * 执行get
     * @param uri 接口
     * @param headerKey header名称
     * @param headerValue header值
     * @param parameter 参数
     * @return HttpResponse
     */
    private static HttpResponse doGet(String uri, String headerKey, String headerValue, String parameter) {

        HttpRequest httpRequest = new HttpRequest(uri).method(Method.GET).header(headerKey, headerValue, true);
        return httpRequest.body(parameter, null).timeout(HttpConstant.CONNECTION_TIME_OUT).execute();
    }

    /**
     * 执行post
     * @param uri 接口
     * @param headerKey header名称
     * @param headerValue header值
     * @param parameter 参数
     * @return HttpResponse
     */
    private static HttpResponse doPost(String uri, String headerKey, String headerValue, String parameter) {

        HttpRequest httpRequest = new HttpRequest(uri).method(Method.POST).header(headerKey, headerValue, true);
        return httpRequest.body(parameter, null).timeout(HttpConstant.CONNECTION_TIME_OUT).execute();
    }
}
