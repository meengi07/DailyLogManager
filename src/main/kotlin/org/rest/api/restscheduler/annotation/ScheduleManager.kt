package org.rest.api.restscheduler.annotation

import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ScheduledFuture

@Component
class ScheduleManager(
    private val taskScheduler: TaskScheduler
) {

    private val scheduledTasks = ConcurrentHashMap<String, ScheduledFuture<*>>()
    private val taskIntervals = ConcurrentHashMap<String, Duration>()
    private val tasks = ConcurrentHashMap<String, Runnable>()

    fun scheduleTask(name: String, interval: Duration, task: Runnable) {
        val future = taskScheduler.scheduleAtFixedRate(task, interval)
        scheduledTasks[name] = future
        taskIntervals[name] = interval
        tasks[name] = task
    }

    fun stopTask(name: String) {
        if(checkScheduledStatus(name)) {
            val taskFuture = scheduledTasks[name]
            taskFuture?.cancel(true)
            scheduledTasks.remove(name)
            taskIntervals.remove(name)
            tasks.remove(name)
        } else {
            throw IllegalArgumentException("Task $name is not running")
        }
    }

    fun changeTaskInterval(name: String, newInterval: Duration) {
        stopTask(name)
        val task = tasks[name]
        if (task != null) {
            scheduleTask(name, newInterval, task)
        }
    }

    fun isTaskRunning(name: String): Boolean = scheduledTasks.containsKey(name)

    fun checkTaskInterval(name: String): Long? = taskIntervals[name].let { it?.toMillis() }

    fun checkScheduledTasks(): List<String> = scheduledTasks.keys.toList()

    fun checkScheduledStatus(name: String): Boolean = scheduledTasks[name] != null
}