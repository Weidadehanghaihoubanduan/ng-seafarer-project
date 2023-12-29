package com.bestpay.seafarer.core.jmeter.core;

import com.bestpay.seafarer.core.kafka.KafkaManager;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.visualizers.backend.BackendListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author dengyancan
 * @Date: 2023/10/18 15:47
 */
@Slf4j
@Component
@NoArgsConstructor
public class BackendSampler extends BackendListener {

    @Resource private KafkaManager kafkaTemplate;

    private static final long serialVersionUID = 3400272172694936675L;

    /**
     * executed at start, see here for details
     * StandardJMeterEngine.notifyTestListenersOfStart(testListeners);
     */
    @Override
    public void testStarted() { /* no processing is done here */ }

    /**
     * 获取执行结果
     * 聚合报告实例 Summariser
     * @param e
     *            the sample event that was received
     */
    @Override
    public void sampleOccurred(SampleEvent e) {

        if( e == null || e.getResult() == null ) { return; }
        SampleResult result = e.getResult();

        /*           todo 此处应引入kafka对数据进行异步处理;   目前未配置！！！
        *   kafkaTemplate.sendMessage(String topic, String message, @NonNull String traceLogId);   */


        log.info("\n  status: " + result.isSuccessful()            +
                 "\n  name: " + result.getSampleLabel()            +
                 "\n  data: " + result.getSamplerData()            +
                 "\n  start: " + result.getStartTime()             +
                 "\n  end: " + result.getEndTime()                 +
                 "\n  result: " + result.getResponseDataAsString());
    }

    /**
     * executed at end , see here for details
     * StandardJMeterEngine.notifyTestListenersOfEnd(testListeners);
     */
    @Override
    public void testEnded() { /* no processing is done here */ }
}
