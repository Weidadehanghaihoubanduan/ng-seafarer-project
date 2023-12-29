package com.bestpay.seafarer.core.kafka;

import lombok.NonNull;

/**
 * @author dengyancan
 * @Date: 2023-12-29
 */
public interface KafkaManager {

    /**
     * @param topic The topic the record will be appended to
     * @param message The record contents
     * @param traceLogId The Tracing id
     */
    void sendMessage(String topic, String message, @NonNull String traceLogId);
}
