package com.bestpay.seafarer.core.jmeter.instance;

import com.bestpay.seafarer.core.jmeter.bean.Thread;
import org.apache.jmeter.testelement.property.BooleanProperty;
import org.apache.jmeter.threads.ThreadGroup;

import java.io.Serializable;

/**
 * 构造线程组
 * @author dengyancan
 * @Date: 2023/10/17 18:35
 */
public class ThreadInstance extends ThreadGroup implements Serializable {

    private Thread thread;

    /**
     * 此方法无法使用 private 修饰
     * 预留给Jmeter反射调用的, 切勿使用！！！
     */
    @Deprecated
    public ThreadInstance() {  }

    public ThreadInstance(Thread thread){  this.thread = thread;  }

    /**
     * 获取thread实例
     */
    public ThreadInstance getThread() {

        this.setName(thread.getKey());
        this.setComment(thread.getComment());
        this.setNumThreads(thread.getNumThreads());
        this.setRampUp(thread.getRampUp());
        this.setIsSameUserOnNextIteration(thread.getNextSame());
        this.setDuration(thread.getDuration());
        this.setScheduler(Boolean.TRUE);   //此处是对调度器的默认设置, 不推荐false 否则永远执行不会break;

        if (thread.getDelayedStart() && thread.getDelay() > 0L) {
            //此处默认不开启延迟启动标识, 若要延迟启动需要将thread.delayedStart == true && this.setDelay > 0
            this.setDelay(thread.getDelay());
            this.setProperty(new BooleanProperty(DELAYED_START, Boolean.TRUE));
        }

        //default set the sampler controller.
        this.setSamplerController(thread.getLoop());

        //determines whether the loop will return any samples if it is rerun.
        if (thread.getContinueForever()) { thread.getLoop().setContinueForever(Boolean.TRUE) ; }

        return this;
    }

    private static final long serialVersionUID = -6938662438941830696L;
}
