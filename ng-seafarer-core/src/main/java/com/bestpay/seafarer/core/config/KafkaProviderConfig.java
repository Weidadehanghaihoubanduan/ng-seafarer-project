package com.bestpay.seafarer.core.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengyancan
 * @Date: 2023-12-29
 */
@Configurable
public class KafkaProviderConfig {

    @Value("${}")
    private String kafkaProviderServers;

    @Value("${}")
    private String ack;

    @Value("${}")
    private String retries;

    @Value("${}")
    private String timeout;

    @Value("${}")
    private String block;

    @Value("${}")
    private String batchSize;

    @Value("${}")
    private String linger;

    /**
     * 此处需要注意: kafka的自动配置与redis不同,
     *             redis -> @ConditionalOnMissingBean(name = "redisTemplate")  bean name
     *             kafka -> @ConditionalOnMissingBean(KafkaTemplate.class)     bean type
     *
     * @return KafkaTemplate实例
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        ProducerFactory<String, String> kafkaFactory =
                new DefaultKafkaProducerFactory<>(kafkaProviderProperties(kafkaProviderServers));
        return new KafkaTemplate<>(kafkaFactory, Boolean.FALSE);
    }

    /**
     * 组装提供者kafka配置
     * @param kafkaProviderServers kafka提供者服务地址
     * @return kafka provider properties
     */
    private Map<String, Object> kafkaProviderProperties(String kafkaProviderServers) {
        HashMap<String, Object> properties = new HashMap<>(16);
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProviderServers);
        properties.put(ProducerConfig.ACKS_CONFIG, ack);
        // retries表示重试次数，如果配置重试请保证消费端具有业务上幂等，根据业务需求配置
        properties.put(ProducerConfig.RETRIES_CONFIG, retries);
        // 发送消息请求的超时时间
        properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, timeout);
        // 如果发送方buffer满或者获取不到元数据时最大阻塞时间
        properties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, block);
        // 发送每批消息大小，建议65536，单位bytes
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        // 批量发送等待时间，建议5，单位ms
        properties.put(ProducerConfig.LINGER_MS_CONFIG, linger);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return properties;
    }
}
