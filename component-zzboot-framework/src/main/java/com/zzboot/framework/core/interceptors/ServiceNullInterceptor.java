package com.zzboot.framework.core.interceptors;


import com.zzboot.framework.core.enums.EnumErrorMsg;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * DAO findById 拦截
 */
@Component
@Aspect
public class ServiceNullInterceptor {




    @Around("execution(* com..*Service*.findById(..))")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {

        Object result = joinPoint.proceed(joinPoint.getArgs());
        if(result == null) {
            throw EnumErrorMsg.no_auth.toException();
        }else{
            return result;
        }

    }

}
