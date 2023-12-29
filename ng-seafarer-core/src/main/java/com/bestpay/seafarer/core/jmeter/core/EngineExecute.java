package com.bestpay.seafarer.core.jmeter.core;

import com.bestpay.seafarer.core.jmeter.instance.SamplerInstance;
import com.bestpay.seafarer.core.jmeter.instance.ThreadInstance;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author dengyancan
 * @Date: 2023-11-08
 */
@Slf4j
public class EngineExecute extends Thread {

    /* 线程组实例 */
    private final ThreadInstance threadInstance;

    /* 取样器实例 */
    private final SamplerInstance samplerInstance;

    /* 监听器实例 */
    private final BackendSampler backendSampler;

    /* 同步锁实例 */
    private final ReentrantLock engineLock = new ReentrantLock();

    /**
     * 获取 EngineExecute实例
     * @param threadInstance 线程组实例
     * @param samplerInstance 采样器实例
     * @param backendSampler 监听器实例
     */
    public EngineExecute(@NonNull ThreadInstance threadInstance,
                         @NonNull SamplerInstance samplerInstance,
                         @NonNull BackendSampler backendSampler) {

        this.threadInstance = threadInstance;
        this.samplerInstance = samplerInstance;
        this.backendSampler = backendSampler;
    }

    @Override
    @SneakyThrows
    public void run() {

        /*  此处默认只执行一次, 且仅仅是单接口的压力测试.
                 并非自动化 || 多线程并发测试 , 且待后续实现.
                    ->  推荐使用CyclicBarrier 更接近多线程并发测试 !!!
                    ->  自动化实现方式众多, 此处不在赘述 !!!

          // todo        故无需实现以下方法:
          前置处理器  ->  JMeterThread#runPreProcessors(pack.getPreProcessors());
          后置处理器  ->  JMeterThread#runPostProcessors(pack.getPostProcessors());
          断言处理器  ->  JMeterThread#checkAssertions(pack.getAssertions(), result, threadContext);
                                                                                                    */

        //构造子节点
        HashTree subTree = new ListedHashTree();
        subTree.add(this.threadInstance);
        subTree.add(this.samplerInstance);
        subTree.add(this.backendSampler);

        //构造父节点
        HashTree parentTree = new ListedHashTree();

        //数据整合
        parentTree.add(new TestPlan(), subTree);

        /*      此处使用单机锁也称之为Jvm锁或同步锁 即可满足当前需求;
                       若后续存在多节点再引入RedissonClient;      */
        final ReentrantLock lock = this.engineLock;
        lock.lock();
        try {
            //开始执行
            StandardJMeterEngine engine = new StandardJMeterEngine();
            engine.configure(parentTree);           engine.runTest();
        } finally {
            lock.unlock();
        }
    }
}
