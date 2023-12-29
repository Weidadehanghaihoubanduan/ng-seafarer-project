package com.bestpay.seafarer.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bestpay.seafarer.common.exception.BusinessBaseEnum;
import com.bestpay.seafarer.common.exception.BusinessBaseException;
import com.bestpay.seafarer.common.result.RD;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * need to check
 * @Author: dengyancan
 * @Date: 2023/12/15 16:52
 */
@SuppressWarnings("ALL")
public class Validator {

    /**
     * 状态码
     */
    public static final Integer STATUS_CODE_NORMAL = 200;

    /**
     * db check
     * @param wildcard default rows
     * @param rows db rows
     * @return true or false
     */
    public static Boolean wildcard(Object wildcard, Object... rows) {

        try {
            Integer integerValue; Long longValue;

            if (wildcard instanceof Integer) { integerValue = (Integer) wildcard;
                List<Integer> integerCollects = Arrays.stream(rows).map((r) ->
                        Integer.valueOf(String.valueOf(r))).collect(Collectors.toList());
                if (integerValue == 1 || integerCollects.contains(integerValue)) { return true; } }

            if (wildcard instanceof Long) { longValue = (Long) wildcard;
                List<Long> longCollects = Arrays.stream(rows).map((r) ->
                        Long.parseLong(String.valueOf(r))).collect(Collectors.toList());
                if (longValue == 1 || longCollects.contains(longValue)) { return true; } }
        } catch (Exception exp) { throw new RuntimeException(exp.getMessage()); }

        throw new BusinessBaseException(BusinessBaseEnum.DB_ERROR);
    }

    /**
     * feign check
     * @param responseEntity 响应实体
     */
    public static void feignCheck(ResponseEntity<?> responseEntity) {

        String content = JSON.toJSONString(Objects.requireNonNull(responseEntity.getBody()));
        RD<?> rd = JSONObject.parseObject(content, RD.class);

        if(rd.getCode() != null && STATUS_CODE_NORMAL.equals(rd.getCode())) {
            throw new BusinessBaseException(rd.getCode(), rd.getMsg()); } }

    /**
     * to check
     * @param key 检查对象
     */
    public static <K> void toCheck(K key) {

        if(key instanceof String){
            if(StringUtils.isEmpty(key)){
                throw new BusinessBaseException(BusinessBaseEnum.INVALID_PARAMS);
            }
        }

        if(key instanceof Map){
            if(CollectionUtils.isEmpty((Map<?, ?>) key)) {
                throw new BusinessBaseException(BusinessBaseEnum.INVALID_PARAMS);
            }
        }

        if(key instanceof Collection){
            if(CollectionUtils.isEmpty((Collection<?>) key)) {
                throw new BusinessBaseException(BusinessBaseEnum.INVALID_PARAMS);
            }
        }

        if(ObjectUtils.isEmpty(key)) {
            throw new BusinessBaseException(BusinessBaseEnum.INVALID_PARAMS);
        }
    }
}
