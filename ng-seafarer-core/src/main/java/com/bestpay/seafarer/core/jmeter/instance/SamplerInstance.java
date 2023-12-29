package com.bestpay.seafarer.core.jmeter.instance;

import com.alibaba.fastjson.JSONObject;
import com.bestpay.seafarer.core.jmeter.bean.Sampler;
import com.bestpay.seafarer.core.jmeter.constant.SamplerConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.jmeter.config.Argument;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.testelement.property.PropertyIterator;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * 构造请求实体
 * @author dengyancan
 * @Date: 2023/10/17 17:07
 */
@Slf4j
@SuppressWarnings("ALL")
public class SamplerInstance extends HTTPSampler {

    private Sampler sampler;

    /**
     * 此方法无法使用 private 修饰
     * 预留给Jmeter反射调用的, 切勿使用！！！
     */
    @Deprecated
    public SamplerInstance() {  }

    public SamplerInstance(Sampler sampler) {  this.sampler = sampler;  }

    /**
     * 获取sampler实例
     */
    public SamplerInstance getSampler() {

        this.setName(sampler.getName());
        this.setComment(sampler.getComment());
        this.setProtocol(sampler.getProtocol());
        this.setDomain(sampler.getDomain());
        this.setPort(sampler.getPort());
        this.setPath(sampler.getPath());
        this.setMethod(HTTPSampler.POST);
        this.setUseKeepAlive(Boolean.TRUE);
        this.setContentEncoding(SamplerConstant.ENCODING);

        // 此处不处理其他请求, 其他请求则默认均为POST
        if (HTTPSampler.GET.equals(sampler.getMethod())) { this.setMethod(HTTPSampler.GET); }

        /**
         * 由于请求头默认只支持Json模式, 故参数组装默认用META_DATA;
         * @see SamplerInstance#clearTestElementChildren()
         */
        Arguments arguments = new Arguments();
        Iterator<Map.Entry<String, Object>> iterator = sampler.getArgument().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            HTTPArgument arg = new HTTPArgument( JSONObject.toJSONString(entry.getKey()),
                                                 JSONObject.toJSONString(entry.getValue()) );
            arg.setMetaData(SamplerConstant.META_DATA);
            arguments.addArgument(arg);
        }

        this.setArguments(arguments);
        this.setConnectTimeout(sampler.getTimeout());
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Clears the Header Manager property so subsequent loops don't keep merging more elements
     */
    @Override
    public void clearTestElementChildren() {

        this.removeProperty(HEADER_MANAGER);

        //unified management, mode used json by default
        HeaderManager manager = new HeaderManager();
        manager.add(new Header(
                SamplerConstant.CONTENT_TYPE_KEY, SamplerConstant.CONTENT_TYPE_VALUE));
        this.setHeaderManager(manager);
        return;
    }

    /**
     * Gets the QueryString attribute of the UrlConfig object, using the
     * specified encoding to encode the parameter values put into the URL
     *
     * @param contentEncoding the encoding to use for encoding parameter values
     * @return the QueryString value
     */
    @Override
    public String getQueryString(final String contentEncoding) {

        CollectionProperty arguments = getArguments().getArguments();

        // Optimisation : avoid building useless objects if empty arguments
        if (arguments.isEmpty()) {
            return Strings.EMPTY;
        }

        StringBuilder buf = new StringBuilder(arguments.size() * 15).append(SamplerConstant.BODY_PREFIX);
        PropertyIterator iterator = arguments.iterator();
        while (iterator.hasNext()) {
            HTTPArgument item = null;
            /*
             * N.B. Revision 323346 introduced the ClassCast check, but then used iter.next()
             * to fetch the item to be cast, thus skipping the element that did not cast.
             * Reverted to work more like the original code, but with the check in place.
             * Added a warning message so can track whether it is necessary
             */
            Object objectValue = iterator.next().getObjectValue();

            try {
                item = (HTTPArgument) objectValue;
            } catch (ClassCastException e) { // NOSONAR
                log.warn("Unexpected argument type: {} cannot be cast to HTTPArgument", objectValue.getClass().getName());
                item = new HTTPArgument((Argument) objectValue);
            }

            if (StringUtils.isEmpty(item.getName())) {
                continue;
            }
            buf.append(item.getName());
            if (item.getMetaData() == null) {
                buf.append(SamplerConstant.BODY_CONNECT);
            } else {
                buf.append(item.getMetaData());
            }
            buf.append(item.getValue());
            if (iterator.hasNext()) {
                buf.append(SamplerConstant.BODY_PARTITION);
            }
        }
        buf.append(SamplerConstant.BODY_SUFFIX);
        return buf.toString();
    }
}
