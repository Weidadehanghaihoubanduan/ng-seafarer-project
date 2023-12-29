package com.bestpay.seafarer.integration;

import com.bestpay.seafarer.common.exception.BusinessBaseEnum;
import com.bestpay.seafarer.common.exception.BusinessBaseException;
import com.bestpay.seafarer.core.redis.RedisManager;
import com.bestpay.seafarer.integration.annotation.NoRepeatSubmit;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author dengyancan
 * @Date: 2023-12-29
 */
@Aspect
@Component
public class RepeatSubmitAspect {

    @Resource private RedisManager redisTemplate;

    /**
     * JWT令牌前缀
     */
    private static final String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * 认证信息Http请求头
     */
    private static final String JWT_TOKEN_HEADER = "Authorization";

    /**
     * 日志工厂
     */
    private static final Logger logger = LoggerFactory.getLogger(RepeatSubmitAspect.class);

    /**
     * 拦截重复提交环绕通知
     * @param pjp 切入点对象
     * @param noRepeatSubmit 自定义的注解对象
     * @return java.lang.Object
     */
    @Around(value = "@annotation(noRepeatSubmit)")
    public Object doAround(ProceedingJoinPoint pjp, NoRepeatSubmit noRepeatSubmit) throws Throwable {

        //接受到请求 记录请求内容
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Assert.notNull(requestAttributes, "requestAttributes can not null");
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        // 拿到ip地址、请求路径、token(token后缀)
        String ip = request.getRemoteAddr();
        String url = request.getRequestURL().toString();
        String token = request.getHeader(JWT_TOKEN_HEADER);
        if(ObjectUtils.isEmpty(token)) { throw new BusinessBaseException(BusinessBaseEnum.INVALID_PARAMS); }
        String realToken = token.replace(JWT_TOKEN_PREFIX, Strings.EMPTY);

        long now = System.currentTimeMillis();
        String key = "seafarer_platform_" + ip + "_" + url + "_" + realToken;

        if (redisTemplate.redisHasKey(key)){
            // 上次表单提交时间
            long lastTime = Long.parseLong(redisTemplate.redisKeyGet(key));
            // 如果现在距离上次提交时间小于设置的默认时间 则 判断为重复提交  否则 正常提交 -> 进入业务处理
            if ((now - lastTime) > noRepeatSubmit.lockTime()) {
                // 非重复提交操作 - 重新记录操作时间
                redisTemplate.redisKeySet(key, String.valueOf(now),
                        redisTemplate.redisKeyGetExpire(key, TimeUnit.SECONDS), TimeUnit.SECONDS);
                //日志记录
                logger.info("key = {}", key);
                logger.info("key validPeriod = {}s", redisTemplate.redisKeyGetExpire(key, TimeUnit.SECONDS));
                // 进入处理业务
                return pjp.proceed();
            } else {
                //抛出重复提交异常
                throw new BusinessBaseException(BusinessBaseEnum.REPEAT_NOT_SUBMISSION);
            }
        } else {
            //key为空或过期时算第一次提交, key有效期为一天
            redisTemplate.redisKeySet(key, String.valueOf(now), 60 * 60 * 24, TimeUnit.SECONDS);
            //执行进程
            return pjp.proceed();
        }
    }
}
