package com.bestpay.seafarer.core.jmeter.init;

import org.apache.jmeter.util.JMeterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.net.URL;

/**
 * 初始化jmt配置
 * @author dengyancan
 * @Date: 2023-11-08
 */
@Order(value = Integer.MIN_VALUE)
@Component(value = "jMeterProperties")
public class InitJMeterProperties implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitJMeterProperties.class);

    @Override
    @SuppressWarnings("ALL")
    public void run(String... args) throws Exception {

        if (!ObjectUtils.isEmpty(JMeterUtils.getJMeterProperties())) { return; }

        URL resource = InitJMeterProperties.class.getResource("/jmeter/jmeter.properties");
        JMeterUtils.loadJMeterProperties(resource != null ? resource.getPath() : null);
        JMeterUtils.initLocale();

        // update repeatedly, make sure response encoding is UTF-8
        JMeterUtils.setProperty("sampleresult.default.encoding", "UTF-8");
        logger.info("jmt初始化配置:"+ JMeterUtils.getJMeterProperties().stringPropertyNames());
    }
}
