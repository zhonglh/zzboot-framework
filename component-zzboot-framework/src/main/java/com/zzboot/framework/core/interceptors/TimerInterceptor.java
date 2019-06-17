package com.zzboot.framework.core.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/5/2.
 */

@Slf4j
@Component
@Aspect
public class TimerInterceptor{



    @Around("execution(* com..*Controller.*(..))")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String method = joinPoint.getSignature().toString();

        Object result = joinPoint.proceed(joinPoint.getArgs());

        long end = System.currentTimeMillis();
        long use = end - start;
        if(use > 500) {
            log.info("\r\n use time:" + method + " Used:" + use);
        }
        return result;
        
    }
    
}
