package com.zzboot.framework.manage;


import com.zzboot.util.spring.SpringUtil;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 异步任务管理器
 */
public class AsyncManager {

    /**
     * 操作延迟10毫秒
     */
    private static final int OPERATE_DELAY_TIME = 10;

    /**
     * 异步操作任务调度线程池
     */
    private static ScheduledExecutorService executorService;

    /**
     * 单例模式
     */
    private static AsyncManager me = new AsyncManager();

    public static AsyncManager me() {
        executorService = (ScheduledExecutorService)SpringUtil.getBean("scheduledExecutorService");
        return me;
    }

    /**
     * 执行任务
     *
     * @param task 任务
     */
    public void execute(TimerTask task) {
        executorService.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }
}
