package com.zzboot.framework.aspect;


import com.zzboot.framework.core.db.entity.BaseEntity;
import com.zzboot.framework.core.db.entity.EntityUtil;
import com.zzboot.framework.core.db.entity.ILoginUserEntity;
import com.zzboot.framework.shiro.utils.ShiroUtils;
import com.zzboot.util.config.Global;
import com.zzboot.util.config.annotaions.EntityAnnotation;
import javafx.print.Collation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

/**
 * 租户ID处理
 * @author Administrator
 */
@Slf4j
@Aspect
@Order(2)
public class OrganIntercept {



    @Around("execution(* com..*Service*.*(..))")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();

        if(Global.getBusinessConfig().getUseOrgan() && Global.getBusinessConfig().getUseTenant()) {
            ILoginUserEntity loginUser = ((ILoginUserEntity) ShiroUtils.getUserEntity());
            if(loginUser != null) {
                for (Object arg : args) {

                    if (arg == null) {
                        continue;
                    }else if (arg instanceof BaseEntity) {
                        EntityAnnotation ea = arg.getClass().getAnnotation(EntityAnnotation.class);
                        if(ea == null){
                            ea = arg.getClass().getSuperclass().getAnnotation(EntityAnnotation.class);
                        }
                        if(ea != null && ea.haveOrgan()) {
                            Serializable organId = loginUser.getOrganId();
                            BaseEntity be = (BaseEntity) arg;
                            if (EntityUtil.isEmpty(be.getOrganId())) {
                                be.setOrganId(organId);
                            }
                        }
                    }else if (arg instanceof Collation || arg.getClass().isArray()) {
                        Collection cs = null;
                        if(arg.getClass().isArray()){
                            cs = Arrays.asList(arg);
                        }else {
                            cs =  (Collection)arg;
                        }
                        if(cs != null && !cs.isEmpty()) {
                            Serializable organId = loginUser.getOrganId();
                            Object first = cs.toArray()[0];
                            if(first != null && first instanceof BaseEntity) {
                                EntityAnnotation ea = first.getClass().getAnnotation(EntityAnnotation.class);
                                if(ea == null){
                                    ea = first.getClass().getSuperclass().getAnnotation(EntityAnnotation.class);
                                }
                                if (ea != null && ea.haveOrgan()) {
                                    for (Object obj : cs) {
                                        BaseEntity be = (BaseEntity) obj;
                                        if (be != null && EntityUtil.isEmpty(be.getOrganId())) {
                                            be.setOrganId(organId);
                                        }
                                    }
                                }
                            }
                        }

                    } else {
                        continue;
                    }
                }


            }

        }

        return joinPoint.proceed(args);
    }


}
