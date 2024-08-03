package org.rest.api.restscheduler.annotation

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
class SchedulerConfig {

    @Bean
    fun taskScheduler(): TaskScheduler {
        val scheduler = ThreadPoolTaskScheduler()
        scheduler.poolSize = 10
        return scheduler
    }

    @Bean
    fun scheduleManager(taskScheduler: TaskScheduler): ScheduleManager {
        return ScheduleManager(taskScheduler)
    }

}