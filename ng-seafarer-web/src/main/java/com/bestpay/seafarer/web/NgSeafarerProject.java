package com.bestpay.seafarer.web;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dengyancan
 * @Date: 2024-01-08
 */
@SuppressWarnings("ALL")
@EnableDubbo
@MapperScan(basePackages = {"com.bestpay.seafarer.dao"})
@SpringBootApplication(scanBasePackages = {"com.bestpay.seafarer.web"})
public class NgSeafarerProject {
    public static void main(String[] args) {
        SpringApplication.run(NgSeafarerProject.class, args);
    }
}
