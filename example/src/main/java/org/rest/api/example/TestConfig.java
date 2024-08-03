package org.rest.api.example;

import org.rest.api.restscheduler.annotation.ScheduleManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

@Configuration
public class TestConfig {

    private final TaskScheduler taskScheduler;

    public TestConfig(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @Bean
    public ScheduleManager scheduleManager() {
        return new ScheduleManager(taskScheduler);
    }

}
