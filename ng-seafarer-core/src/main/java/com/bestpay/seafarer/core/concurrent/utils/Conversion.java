package com.bestpay.seafarer.core.concurrent.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dengyancan
 * @Date: 2022/12/15
 */
public class Conversion {

    /**
     * map转对象
     * @param map 源数据
     * @param clazz 目标对象
     * @param <T> 泛型
     * @return to obj
     * @throws IntrospectionException 内部异常
     * @throws InstantiationException 实例化异常
     * @throws IllegalAccessException 非法访问异常
     * @throws InvocationTargetException 调用目标异常
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> clazz) throws IntrospectionException,
            InstantiationException, IllegalAccessException, InvocationTargetException {

        if (map == null) return null;

        T t = clazz.newInstance();
        BeanInfo beanInfo = Introspector.getBeanInfo(t.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        for (PropertyDescriptor property : propertyDescriptors) {
            java.lang.reflect.Method setter = property.getWriteMethod();
            if (setter != null) setter.invoke(t, map.get(property.getName()));
        }
        return t;
    }

    /**
     * 对象转map
     * @param obj 源对象
     * @return to map
     * @throws IntrospectionException 内部异常
     * @throws InvocationTargetException 调用目标异常
     * @throws IllegalAccessException 非法访问异常
     */
    public static Map<String, Object> objectToMap(Object obj) throws IntrospectionException,
            InvocationTargetException, IllegalAccessException {

        if (obj == null) return null;

        Map<String, Object> map = new HashMap<>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) continue;
            java.lang.reflect.Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(obj) : null;
            map.put(key, value);
        }
        return map;
    }
}
