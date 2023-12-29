package com.bestpay.seafarer.common.utils;

import lombok.SneakyThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * @Author: dengyancan
 * @Date: 2023/12/15 16:52
 */
@SuppressWarnings("ALL")
public class SimpleDateUtil {

    /**
     * 时间类
     */
    private final SimpleDateFormat simpleDateFormat;


    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 构造器初始化
     */
    public SimpleDateUtil()
    {
        this.simpleDateFormat = new SimpleDateFormat(PATTERN);
    }

    /**
     * 获取当前时间
     * @return 当前时间
     */
    @SneakyThrows
    public Date currentTime() {
        try {
            String formatCreateTime = this.simpleDateFormat.format(new Date());
            return this.simpleDateFormat.parse(formatCreateTime);
        } catch (ParseException parseException) {
            throw new ParseException(parseException.getMessage(), parseException.getErrorOffset());
        }
    }

    /**
     * 下N个月
     * @param amount 月份
     * @return 下N个月
     */
    @SneakyThrows
    public Date nextMonths(int amount) {

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, amount);
            String formatEndTime = this.simpleDateFormat.format(calendar.getTime());
            return this.simpleDateFormat.parse(formatEndTime);
        } catch (ParseException parseException) {
            throw new ParseException(parseException.getMessage(), parseException.getErrorOffset());
        }
    }

    /**
     * 下N年
     * @return 下N个年
     */
    @SneakyThrows
    public Date nextYears(int amount) {

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, amount);
            String formatEndTimeYear = this.simpleDateFormat.format(calendar.getTime());
            return simpleDateFormat.parse(formatEndTimeYear);
        } catch (ParseException parseException) {
            throw new ParseException(parseException.getMessage(), parseException.getErrorOffset());
        }
    }
}