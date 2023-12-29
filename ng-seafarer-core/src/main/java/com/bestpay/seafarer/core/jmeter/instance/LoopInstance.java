package com.bestpay.seafarer.core.jmeter.instance;

import com.bestpay.seafarer.core.jmeter.bean.Loop;
import org.apache.jmeter.control.LoopController;

import java.io.Serializable;

/**
 * 构造循环控制器
 * @author dengyancan
 * @Date: 2023/10/17 16:57
 */
public class LoopInstance extends LoopController implements Serializable {

    private Loop loop;

    /**
     * 此方法无法使用 private 修饰
     * 预留给Jmeter反射调用的, 切勿使用！！！
     */
    @Deprecated
    public LoopInstance() {  }

    public LoopInstance(Loop loop) {  this.loop = loop;  }

    /**
     * 获取loop实例
     */
    public LoopInstance getLoop() {
        //此处不进行融合至ThreadInstance实例中, 以免后续对此类进行扩展时操作不便;
        this.setName(loop.getName());   this.setComment(loop.getComment());
        this.setLoops(loop.getLoops()); this.initialize();     return this;
    }

    private static final long serialVersionUID = -6422876911718878640L;
}
