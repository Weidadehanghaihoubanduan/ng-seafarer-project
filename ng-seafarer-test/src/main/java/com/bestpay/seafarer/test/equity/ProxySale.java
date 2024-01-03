package com.bestpay.seafarer.test.equity;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.bestpay.seafarer.core.concurrent.bean.SourceEntity;
import com.bestpay.seafarer.core.concurrent.bean.TargetEntity;
import com.bestpay.seafarer.core.concurrent.core.Barrier;
import com.bestpay.seafarer.core.concurrent.core.MultiThread;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;

/**
 * @author dengyancan
 */
@Slf4j
@SuppressWarnings("ALL")
public class ProxySale {

    private static final String PROXY_VERIFY_CODE
            = "https://mapi.test.bestpay.net/gapi/test/ep-province-center/ProxySaleService/proxySendVerifyCode";
    private static final String PROXY_CHANNEL_ORDER
            = "https://mapi.test.bestpay.net/gapi/test/ep-province-center/ProxySaleService/proxyChannelOrder";

    /**
     * @param arg args   权益
     *      SPN202312200927557770120 长期方案-集团代销new
     *      SPN202312141112507851048 长期方案-福建代销正式编码
     *      SPN202312071920388753132 长期方案-宁夏代销
     *      SPN202312021544340459862 长期方案-湖北代销
     *      SPN202312021648476002593 长期方案-云南代销
     *      SPN202312011507091949260 长期方案-山西代销
     */
    @SneakyThrows
    public static void main(String[] arg) {

        Integer threadNum = 1;
        SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CyclicBarrier barrier = Barrier.getCyclicBarrier(threadNum,
                () -> log.warn("      ------  do start  ------ " + data.format(new Date())));

        for ( int i = 0; i < threadNum; ++i ) {

            String phone = "189" + new RandomUtil().randomString("0123456789", 8);
            String uuid = UUID.randomUUID(Boolean.FALSE).toString();
            SourceEntity source = new SourceEntity();
            Map<String, Object> sourceParameter = new HashMap<>();
            sourceParameter.put("productNo", "19970972097");
            sourceParameter.put("strategyNo", "SPN202312071920388753132");
            sourceParameter.put("traceLogId", "dx" + uuid);
            String sourceTraceLogId = sourceParameter.get("traceLogId").toString();
            SourceEntity sourceEntity =
                    source.setUri(PROXY_VERIFY_CODE).setDependency(Boolean.TRUE).setField("verifyNo").
                            setParameter(JSONObject.toJSONString(sourceParameter)).setTraceLogId(sourceTraceLogId);

            TargetEntity target = new TargetEntity();
            Map<String, Object> targetParameter = new HashMap<>();
            targetParameter.put("traceLogId", "dj" + uuid);
            targetParameter.put("productNo", "19970972097");
            targetParameter.put("fromChannelId", "XMLY");
            targetParameter.put("verifyCode", "#{verifyNo}");
            targetParameter.put("salesProductNo", "SPN202312071920388753132");
            targetParameter.put("outOrderNo", "OUT" + uuid);
            String targetTraceLogId = targetParameter.get("traceLogId").toString();
            target.setUri(PROXY_CHANNEL_ORDER).setTraceLogId(targetTraceLogId).setAccount(phone)
                    .setParameter(JSONObject.toJSONString(targetParameter));

            new MultiThread(barrier, source, target).start();
        }
    }
}
