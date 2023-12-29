package com.bestpay.seafarer.core.kafka.impl;

import com.bestpay.seafarer.core.kafka.KafkaManager;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author dengyancan
 * @Date: 2023-12-29
 */
@Slf4j
@Service
public class KafkaManagerImpl implements KafkaManager {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaManagerImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message, @NonNull String traceLogId) {

        try {

            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                /**
                 * 发送成功后处理
                 * @param result  结果
                 */
                @Override
                public void onSuccess(@Nullable SendResult<String, String> result) {
                    log.info("Sampler result send success ---- topic : {}, " +
                            "traceLogId : {}, value : {} ", topic, traceLogId, message);
                }

                /**
                 * 发送失败后处理
                 * @param ex  异常
                 */
                @Override
                public void onFailure(@Nullable Throwable ex) {
                    log.error("Sampler result send message error ---- topic : {}, " +
                            "traceLogId : {}, value : {} ", topic, traceLogId, message, ex);
                }
            });
        } catch (Exception e) {
            log.error("kafka message send exception ---- Exception: ", e);
        }
    }
}
