package com.bestpay.seafarer.common.handler;

import com.bestpay.seafarer.common.result.R;
import com.bestpay.seafarer.common.result.RD;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.StringJoiner;

/**
 * @Author: dengyancan
 * @Date: 2020-12-29
 */
@ControllerAdvice
public class HandlerException {

    /**
     * @param req 请求
     * @param exp 错误类型
     * @param <T> 泛型T对象
     * @return 验证异常
     */
    @ResponseBody
    @SuppressWarnings("ALL")
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public <T> RD<T> unexpectedFailedException(HttpServletRequest req,
                                               MethodArgumentNotValidException exp) {

        StringJoiner sj = new StringJoiner(", ", "[", "]");
        List<FieldError> fieldErrors = exp.getBindingResult().getFieldErrors();
        fieldErrors.stream().forEach(fe -> sj.add(fe.getField() + ":" + fe.getDefaultMessage()));
        return R.failure(401, sj.toString());
    }

    /**
     * @param req 请求
     * @param exp 错误类型
     * @param <T> 泛型T对象
     * @return 空指针异常
     */
    @ResponseBody
    @SuppressWarnings("ALL")
    @ExceptionHandler(value = {NullPointerException.class})
    public <T> RD<T> nullPointerFailedException(HttpServletRequest req,
                                                NullPointerException exp) {

        if (StringUtils.isEmpty(exp.getMessage())){
            exp.printStackTrace();
            return R.failure(402, "Data null pointer error");
        }
        exp.printStackTrace();
        return R.failure(402, exp.getMessage());
    }

    /**
     * @param req 请求
     * @param exp 错误类型
     * @param <T> 泛型T对象
     * @return 运行时异常
     */
    @ResponseBody
    @SuppressWarnings("ALL")
    @ExceptionHandler(value = {RuntimeException.class})
    public <T> RD<T> runtimeFailedException(HttpServletRequest req,
                                            RuntimeException exp) {
        exp.printStackTrace();
        return R.failure(400, exp.getMessage());
    }

    /**
     * @param req 请求
     * @param exp 错误类型
     * @param <T> 泛型T对象
     * @return 未知异常
     */
    @ResponseBody
    @SuppressWarnings("ALL")
    @ExceptionHandler(value = {Exception.class})
    public <T> RD<T> failedBaseException(HttpServletRequest req,
                                         Exception exp) {
        exp.printStackTrace();
        return R.failure(500, exp.getMessage());
    }
}