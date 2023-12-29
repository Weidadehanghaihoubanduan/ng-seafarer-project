package com.bestpay.seafarer.test.study;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 梳理@Component的初始化流程
 * @author dengyancan
 *
 */
@Slf4j
@Component(value = "hangHaiJiaComponentRW")
public class ComponentRW {

    @SuppressWarnings("ALL")
    public BeanRW componentRW(){

        log.info("梳理@Component的初始化流程  -> " +
                "1.此处finishBeanFactoryInitialization(beanFactory), 处理实例化所有剩余非懒加载的bean;" +
                             "备注：此处可以理解为用户行为定义的bean都会用此方法来初始化实例;   " +
                "2.真正执行方法入口  ->  beanFactory.preInstantiateSingletons();" +
                "3.拿到事先填充好的this.beanDefinitionNames, 循环处理;" +
                "4.判断用户行为定义的bean是否为FactoryBean;" +
                "5.判断此bean是否在this.singletonObjects  备注：此处不可能存在, 此Map为最终可用的缓存Map;" +
                "6.判断当前bean是否为单例bean, 为true时开始创建;" +
                "7.开始创建bean -> AbstractAutowireCapableBeanFactory.doCreateBean();" +
                "8.首先从缓存bean实例工厂中移除此bean, 如果缓存bean实例工厂中无此bean, 则创建实例;" +
                "9.使用AbstractAutowireCapableBeanFactory.instantiateBean 开始实例化bean;" +
                "10.通过动态代理CglibSubclassingInstantiationStrategy.instantiate来构造对象;" +
                "11.通过beanUtils.instantiateClass来填充对象参数类型;" +
                              "备注：此处就是为了确定成员变量的数据类型, 就是八大基本参数类型的确认;" +
                "12.使用AbstractAutowireCapableBeanFactory.invokeInitMethods检查bean是否为InitializingBean;" +
                "13.开始将此bean填充至this.singletonObjects等其他Map;" +
                "14.至此类标注@Component注解的bean创建结束;"                                                    );

        return new BeanRW("you can always believe hanghaijia");
    }
}
