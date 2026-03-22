package com.youtube.auth_service.internal.configs.port;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;


@Configuration
public class SchedulingConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(5);
        taskScheduler.setThreadNamePrefix("outbox-sched-");
        
        taskScheduler.setWaitForTasksToCompleteOnShutdown(true); 
        taskScheduler.setAwaitTerminationSeconds(20);
        
        taskScheduler.initialize();
        taskRegistrar.setTaskScheduler(taskScheduler);
    }
}