package com.zzboot.framework.config;

import com.zzboot.util.base.java.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 *
 */
@Slf4j
@Configuration
public class ThreadPoolConfig {

    /**
     * 空闲线程存活时间，单位：s 默认2000
     */
    @Value("${thread.pool.keepAliveSeconds}")
    private int keepAliveSeconds;

    /**
     * 线程池维护线程最大数量 默认100
     */
    @Value("${thread.pool.maxPoolSize}")
    private int maxPoolSize;

    /**
     * 线程池维护线程最小数量 默认10
     */
    @Value("${thread.pool.corePoolSize}")
    private int corePoolSize;

    @Bean(name = "publicThreadPool")
    protected ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        return executor;
    }

    @Bean(name = "scheduledExecutorService")
    protected ScheduledExecutorService scheduleTaskExuctor() {
        return new ScheduledThreadPoolExecutor(corePoolSize,
                new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build()){
            @Override
            protected void afterExecute(Runnable r, Throwable t){
                super.afterExecute(r, t);
                ThreadUtil.printException(r, t);
            }
        };
    }

}
