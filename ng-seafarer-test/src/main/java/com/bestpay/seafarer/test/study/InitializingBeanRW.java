package com.bestpay.seafarer.test.study;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 梳理实现自InitializingBean的初始化流程
 * @author dengyancan
 */
@Slf4j
@Component
public class InitializingBeanRW implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {

        log.info("spring 初始化实现自InitializingBean接口流程: " +
                "1.实现自InitializingBean接口的 bean是由finishBeanFactoryInitialization(beanFactory)方法来初始化" +
                "2.调用beanFactory.preInstantiateSingletons() 实例化所有剩余（非懒加载）单例bean " +
                "3.判断其bean是否为工厂bean (FactoryBean), 反则直接从DefaultSingletonBeanRegistry.singletonObjects获取" +
                "4.如果singletonFactories获取bean为null, 先检查bean是否在BeanFactory中, 如果都不在其中则开始创建bean的实例" +
                "5.创建之前再check singletonObjects.getBean is null, 如果为空则调用singletonFactory创建bean其最终调用路径为:" +
                "AbstractAutowireCapableBeanFactory.doCreateBean().initializeBean().invokeInitMethods()方法创建bean的实例" +
                "6.此时先判断当前bean是否实现自InitializingBean接口, 如果是则调用InitializingBean.afterPropertiesSet()方法" +
                "补充:此处会执行实现自BeanPostProcessor接口的postProcessBeforeInitialization,postProcessAfterInitialization方法" +
                "7.创建bean成功后, 将bean添加至DefaultSingletonBeanRegistry.singletonObjects容器中  -> done !!!"
        );
    }
}
