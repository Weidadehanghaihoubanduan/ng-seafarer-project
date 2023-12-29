package com.bestpay.seafarer.core.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengyancan
 * @Date: 2023-12-29
 */
@EnableKafka
@Configurable
public class KafkaConsumerConfig {

    @Value("${}")
    private String kafkaConsumerServers;

    @Value("${}")
    private String groupId;

    @Value("${}")
    private Integer maxPollRecords;

    /**
     * 获取kafka监听器工厂
     * @return kafka listener
     */
    @Bean("kafkaListenerFactory")
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String>
                samplerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        samplerFactory.setConsumerFactory(kafkaConsumerFactory());
        samplerFactory.setConcurrency(2);
        samplerFactory.getContainerProperties().setPollTimeout(2L);
        return samplerFactory;
    }

    /**
     * 获取默认的kafka消费工厂
     * @return default consumer factory
     */
    private ConsumerFactory<String, String> kafkaConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(kafkaConsumerProperties());
    }

    /**
     * 组装消费者kafka配置
     * @return kafka consumer properties
     */
    private Map<String, Object> kafkaConsumerProperties() {
        Map<String, Object> properties = new HashMap<>(8);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConsumerServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return properties;
    }
}
