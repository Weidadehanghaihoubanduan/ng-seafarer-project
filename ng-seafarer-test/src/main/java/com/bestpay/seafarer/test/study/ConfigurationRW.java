package com.bestpay.seafarer.test.study;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * 梳理@Bean的初始化流程
 * @author dengyancan
 */
@Configurable
public class ConfigurationRW {

    @SuppressWarnings("ALL")
    @Bean(name = "hangHaiJiaConfigurableRW")
    public BeanRW configurationRW() {
        return new BeanRW("you can always believe hanghaijia");
    }
}
