package com.bestpay.seafarer.core.concurrent.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bestpay.seafarer.core.concurrent.constant.HttpConstant;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dengyancan
 * @Date: 2022/12/15
 */
public class RecursivelyParse {

    /**
     * 通过key查询value
     * @param obj 查找数据对象
     * @param findField 查找的key
     * @return dataObject value 对象值
     */
    public static Object getValByKey(Object obj, String findField) {

        Map<String, Object> findMap = new HashMap<>(HttpConstant.DEFAULT);
        getValForRecursively(obj, findField, findMap);
        return findMap.get(findField);
    }

    /**
     * 区分示例对象
     * @param obj 查找数据对象
     * @param findField 查找的key
     * @param findMap 数据对象集合
     */
    private static void getValForRecursively(Object obj, String findField, Map<String, Object> findMap) {

        if(obj instanceof JSONObject) { getValForObj((JSONObject)obj, findField, findMap); }

        if(obj instanceof JSONArray) { getValForArr((JSONArray)obj, findField, findMap); }
    }

    /**
     * 处理JsonObject数据对象
     * @param object 查找数据对象
     * @param findField 查找的key
     * @param findMap 数据对象集合
     */
    private static void getValForObj(JSONObject object, String findField, Map<String, Object> findMap) {

        Assert.hasLength(findField, "field cannot be empty");
        for(Map.Entry<String, Object> entry : object.entrySet()) {
            if(findField.equals(entry.getKey())) {
                findMap.put(entry.getKey(), entry.getValue());
            }
            getValForRecursively(entry.getValue(), findField, findMap);
        }
    }

    /**
     * 处理JsonArray数据对象
     * @param array 查找数据对象
     * @param findField 查找的key
     * @param findMap 数据对象集合
     */
    private static void getValForArr(JSONArray array, String findField, Map<String, Object> findMap) {

        array.forEach(arr -> getValForRecursively(arr, findField, findMap));  }
}
