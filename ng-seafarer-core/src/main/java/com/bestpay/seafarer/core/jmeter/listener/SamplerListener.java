package com.bestpay.seafarer.core.jmeter.listener;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author dengyancan
 * @Date: 2023-12-29
 */
@Slf4j
@Component
public class SamplerListener {

    @KafkaListener(topics = {"${topic}"}, containerFactory = "kafkaListenerFactory")
    public void samplerOnMessage(ConsumerRecord<String, String> record) {

        log.info("收到采样器结果数据: {}", record.value());
        JSONObject.parseObject(record.value(), SampleResult.class);

        // todo 此处应引入JDBC进行数据收集, 方便后续计算 !!!
    }
}
