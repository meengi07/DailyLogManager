package org.rest.api.restscheduler.annotation

import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ScheduledFuture

@Component
class ScheduleManager(
    private val taskScheduler: TaskScheduler
) {

    private val scheduledTasks = ConcurrentHashMap<String, ScheduledFuture<*>>()
    private val taskIntervals = ConcurrentHashMap<String, Long>()

    @Scheduled(fixedRate = 1000)
    fun scheduleTask(name: String, interval: Long, task: Runnable) {
        val future = taskScheduler.scheduleAtFixedRate(task, interval)
        scheduledTasks[name] = future
        taskIntervals[name] = interval
    }

    fun stopTask(name: String) {
        scheduledTasks[name]?.cancel(false)
        scheduledTasks.remove(name)
    }

    fun changeTaskInterval(name: String, newInterval: Long) {
        stopTask(name)
        val task = Runnable {
            println("Task $name executed at: ${LocalDateTime.now()}")
        }
        scheduleTask(name, newInterval, task)
    }

    fun isTaskRunning(name: String): Boolean {
        return scheduledTasks.containsKey(name)
    }

}