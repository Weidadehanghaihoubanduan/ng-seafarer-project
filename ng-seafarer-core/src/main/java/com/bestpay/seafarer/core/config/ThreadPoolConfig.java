package com.bestpay.seafarer.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @author dengyancan
 * @Date: 2023-12-29
 */
@Configuration
public class ThreadPoolConfig {

    @Value("${}")
    private Integer corePoolSize;

    @Value("${}")
    private Integer maximumPoolSize;

    @Value("${}")
    private Long keepAliveTime;

    @Value("${}")
    private Integer capacity;

    /**
     * 线程池配置
     * @return 线程池执行器
     */
    @Bean(name = "runWithExecutor")
    public Executor executor() {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(capacity),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    }
}