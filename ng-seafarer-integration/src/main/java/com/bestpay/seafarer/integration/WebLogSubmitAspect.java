package com.bestpay.seafarer.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author dengyancan
 * @Date: 2023-12-29
 */
@Aspect
@Component
@Order(value = 0)
public class WebLogSubmitAspect {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(WebLogSubmitAspect.class);

    @Pointcut(value = "execution(* com.bestpay.seafarer.service.*Impl.*(..))")
    public void pointCut() {  }

    /**
     *  切入点开始处切入内容
     * @param joinPoint 封装的切面方法信息
     */
    @Before(value = "pointCut()")
    public void doBefore(JoinPoint joinPoint){

        //接受到请求 记录请求内容
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Assert.notNull(requestAttributes, "requestAttributes can not null");
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();

        // 记录下请求内容
        logger.info("------------------------start request------------------------");
        logger.info("################URL : " + request.getRequestURL().toString());
        logger.info("################HTTP_METHOD : " + request.getMethod());
        logger.info("################IP : " + request.getRemoteAddr());
        logger.info("################TOKEN : " + request.getHeader("Authorization"));
        logger.info("################THE ARG_NAMES OF THE CONTROLLER : " + Arrays.toString(((MethodSignature) joinPoint.getSignature()).getParameterNames()));
        logger.info("################THE ARGS OF THE CONTROLLER : " + Arrays.toString(joinPoint.getArgs()));

        //下面这个getSignature().getDeclaringTypeName()是获取包+类名的   然后后面的joinPoint.getSignature.getName()获取了方法名
        logger.info("################CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }

    /**
     *  切入点return内容之后切入内容(报错不执行)
     * @param response 响应
     */
    @AfterReturning(returning = "response", pointcut = "pointCut()")
    public void doAfterReturning(Object response) throws JsonProcessingException {

        logger.info("------------------------end request------------------------");
        logger.info("################RESPONSE : " + OBJECT_MAPPER.writeValueAsString(response));
    }

    /**
     * 捕捉切入后内容报错
     * @param exception 异常信息
     */
    @AfterThrowing(throwing = "exception", pointcut = "pointCut()")
    public void doAfterThrowing (Exception exception) {

        logger.info("------------------------end exception------------------------");
        logger.info("################EXCEPTION : " + exception.toString());
    }
}
