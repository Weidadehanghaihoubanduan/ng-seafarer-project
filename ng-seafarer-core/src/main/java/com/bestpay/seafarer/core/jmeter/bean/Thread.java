package com.bestpay.seafarer.core.jmeter.bean;

import com.bestpay.seafarer.core.jmeter.instance.LoopInstance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author dengyancan
 * @Date: 2023-11-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Thread implements Serializable {

    /**
     * 将此name 作为上下文 唯一key,
     * 也可以将此key设置在testPlan上, 此处不在赘述
     */
    private String key;

    /**
     * 线程注释
     */
    private String comment;

    /**
     * 线程数
     */
    private Integer numThreads;

    /**
     * N秒内所有设置线程数全部启动
     */
    private Integer rampUp;

    /**
     * next = prev
     */
    private Boolean nextSame;

    /**
     * 持续时间
     */
    private Long duration;

    /**
     * 是否延迟启动
     */
    private Boolean delayedStart;

    /**
     * 延迟启动时间
     */
    private Long delay;

    /**
     * 是否永远继续
     */
    private Boolean continueForever;

    /**
     * 循环控制器
     * 此处约定使用自定义的LoopInstance;
     */
    private LoopInstance loop;


    private static final long serialVersionUID = -6843928341146948413L;
}
