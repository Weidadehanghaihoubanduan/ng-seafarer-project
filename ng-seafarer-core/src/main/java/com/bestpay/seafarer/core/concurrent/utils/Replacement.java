package com.bestpay.seafarer.core.concurrent.utils;

import com.bestpay.seafarer.core.concurrent.constant.HttpConstant;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.ObjectUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: dengyancan
 * @Date: 2022/12/15
 */
@Slf4j
public class Replacement {

    /**
     * 解析占位符  解析规则 -> key:#{value}
     * @param field  查找的字段
     * @param source 需要解析的字符串
     * @param target 替换内容
     * @return 解析后的字符串
     */
    public static String substitution(@NonNull String field, String source, String target) {

        Pattern compile = Pattern.compile(HttpConstant.RULES);
        Matcher matcher = compile.matcher(source);

        while (matcher.find()) {

            String matched = matcher.group(HttpConstant.DEFAULT);
            matched = field.equals(matched) ? matched : Strings.EMPTY;

            if (ObjectUtils.isEmpty(matched) || target == null) { break; }
            String content = matcher.group();

            //目前只处理一次解析且只能解析最先匹配的值(从左往右),须处理多次 数据结构应是 target = List<DB -> K:V> or java.util.Map
            source = source.replace(content, target);                     target = null;    }            return source;
    }
}
