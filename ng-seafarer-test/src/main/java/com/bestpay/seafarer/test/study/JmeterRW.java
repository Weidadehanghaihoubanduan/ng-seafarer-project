package com.bestpay.seafarer.test.study;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.bestpay.seafarer.core.jmeter.bean.Loop;
import com.bestpay.seafarer.core.jmeter.bean.Sampler;
import com.bestpay.seafarer.core.jmeter.bean.Thread;
import com.bestpay.seafarer.core.jmeter.core.BackendSampler;
import com.bestpay.seafarer.core.jmeter.core.EngineExecute;
import com.bestpay.seafarer.core.jmeter.instance.LoopInstance;
import com.bestpay.seafarer.core.jmeter.instance.SamplerInstance;
import com.bestpay.seafarer.core.jmeter.instance.ThreadInstance;
import org.apache.jmeter.util.JMeterUtils;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dengyancan
 * @Date: 2023-12-29
 */
public class JmeterRW {

    static {
        //提升日志等级, 否则run的时候会默认为打印debug日志;
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        List<Logger> loggerList = loggerContext.getLoggerList();
        loggerList.forEach(logger -> {
            logger.setLevel(Level.INFO);
        });
    }

    public static void main(String[] arg) {

        URL resource = JmeterRW.class.getResource("/jmeter/jmeter.properties");
        JMeterUtils.loadJMeterProperties(resource != null ? resource.getPath() : null);
        JMeterUtils.initLocale();

        //https://mapi.test.bestpay.net/gapi/test/ep-province-center/ProxySaleService/proxyChannelOrder
        String phone = "189" + RandomUtil.randomString(RandomUtil.BASE_NUMBER, 8);
        String uuid = UUID.randomUUID(Boolean.FALSE).toString();
        Map<String, Object> targetParameter = new HashMap<>();
        targetParameter.put("traceLogId", "dj" + uuid);
        targetParameter.put("productNo", phone);
        targetParameter.put("fromChannelId", "XMLY");
        targetParameter.put("verifyCode", "0000");
        targetParameter.put("salesProductNo", "SPN202312071920388753132");
        targetParameter.put("outOrderNo", "OUT" + uuid);

        Sampler sampler = new Sampler();
        sampler.setName("代销下单").setProtocol("https").setDomain("mapi.test.bestpay.net")
                .setPort(443).setPath("/gapi/test/ep-province-center/ProxySaleService/proxyChannelOrder")
                .setArgument(targetParameter);
        SamplerInstance samplerInstance = new SamplerInstance(sampler).getSampler();

        Loop loop = new Loop();
        loop.setName("loopName").setLoops(1);
        LoopInstance loopInstance = new LoopInstance(loop).getLoop();

        Thread thread = new Thread();
        thread.setKey(uuid).setNumThreads(100).setRampUp(1).setNextSame(Boolean.TRUE)
                .setDuration(10L).setDelayedStart(Boolean.FALSE).setDelay(0L)
                .setContinueForever(Boolean.FALSE).setLoop(loopInstance);
        ThreadInstance threadInstance = new ThreadInstance(thread).getThread();

        new EngineExecute(threadInstance, samplerInstance, new BackendSampler()).start();
    }
}
