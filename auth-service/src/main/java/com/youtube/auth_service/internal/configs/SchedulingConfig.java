package com.youtube.auth_service.internal.configs;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

public class SchedulingConfig implements SchedulingConfigurer {
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar){
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(5);
        taskScheduler.setThreadNamePrefix("outbox-sched-");

        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        taskScheduler.setAwaitTerminationSeconds(20);

        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        taskScheduler.setAwaitTerminationSeconds(20);

        taskScheduler.initialize();
        taskRegistrar.setTaskScheduler(taskScheduler);
    }
}
