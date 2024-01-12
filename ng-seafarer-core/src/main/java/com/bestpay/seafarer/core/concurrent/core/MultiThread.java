package com.bestpay.seafarer.core.concurrent.core;

import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bestpay.seafarer.core.concurrent.bean.SourceEntity;
import com.bestpay.seafarer.core.concurrent.bean.TargetEntity;
import com.bestpay.seafarer.core.concurrent.utils.HttpRequests;
import com.bestpay.seafarer.core.concurrent.utils.RecursivelyParse;
import com.bestpay.seafarer.core.concurrent.utils.Replacement;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.CyclicBarrier;

/**
 * @Author: dengyancan
 * @Date: 2022/12/15
 */
@Slf4j
public class MultiThread extends Thread {

    /* 源数据实例 */
    private final SourceEntity sourceEntity;

    /* 目标数据实例 */
    private final TargetEntity targetEntity;

    /* 循环屏障实例 */
    private final CyclicBarrier cyclicBarrier;

    /**
     * 获取 MultiThread实例
     * @param cyclicBarrier 循环屏障实例
     * @param sourceEntity  源数据实例
     * @param targetEntity  目标数据实例
     */
    public MultiThread(@NonNull CyclicBarrier cyclicBarrier,
                       @NonNull SourceEntity sourceEntity,
                       @NonNull TargetEntity targetEntity) {

        this.sourceEntity = sourceEntity;
        this.targetEntity = targetEntity;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    @SneakyThrows
    public void run() {

        //        数据传递标识位
        Object content = null;
        HttpResponse response = null;

        //依赖数据处理
        if (sourceEntity.getDependency()) {
            try(HttpResponse sourceResponse = HttpRequests.doMethod(sourceEntity.getUri(), sourceEntity.getMethod(),
                    sourceEntity.getHeaderKey(), sourceEntity.getHeaderValue(), sourceEntity.getParameter())) {
                response = sourceResponse; JSONObject contentConspire = JSON.parseObject(response.body());
                content = RecursivelyParse.getValByKey(contentConspire, sourceEntity.getField());              }
        }

        if(sourceEntity.getDependency() && ObjectUtils.isEmpty(content)) {
            log.error("find   sourceField = {}",  sourceEntity.getField());
            log.error("source traceLogId  = {}",  sourceEntity.getTraceLogId());
            log.error("source response    = {}",  response.body());
            return;                               // 后续对外, 此处则直接throw e;
        }

        if(ObjectUtils.isEmpty(targetEntity.getUri())) {  log.error("targetUri cannot empty");   return;  }

        if (!ObjectUtils.isEmpty(content)) {
            targetEntity.setParameter( Replacement.substitution(
                    sourceEntity.getField(), targetEntity.getParameter(), content.toString()) );
            log.warn("target method after parse parameter = \n {}", targetEntity.getParameter());
        }

        //开始对齐, 模拟多线程测试
        Barrier.await(cyclicBarrier);

        //执行目标方法
        try(HttpResponse targetResponse = HttpRequests.doMethod(targetEntity.getUri(), targetEntity.getMethod(),
                targetEntity.getHeaderKey(), targetEntity.getHeaderValue(), targetEntity.getParameter())) {

            log.info("\n account = {} " + "            traceLogId = {} " + "\n result = {} ",
                    targetEntity.getAccount(), targetEntity.getTraceLogId(), targetResponse.body());      }
    }
}
