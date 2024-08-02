package org.rest.api.restscheduler.rest

import org.rest.api.restscheduler.annotation.ScheduleManager
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/scheduler")
class SchedulerController(
    private val scheduleManager: ScheduleManager
) {
    @PostMapping("/start")
    fun startTask(
        @RequestParam name: String,
        @RequestParam interval: Long
    ): String {
        val task = Runnable {
            println("Task $name executed at: ${LocalDateTime.now()}")
        }
        scheduleManager.scheduleTask(name, interval, task)
        return "Task $name scheduled with interval $interval milliseconds"
    }

    @PostMapping("/stop")
    fun stopTask(@RequestParam name: String): String {
        scheduleManager.stopTask(name)
        return "Task $name stopped"
    }

    @PostMapping("/change")
    fun changeInterval(
        @RequestParam name: String,
        @RequestParam interval: Long
    ): String {
        scheduleManager.changeTaskInterval(name, interval)
        return "Task $name interval changed to $interval milliseconds"
    }

    @GetMapping("/status")
    fun getStatus(@RequestParam name: String): String {
        return if (scheduleManager.isTaskRunning(name)) {
            "Task $name is running"
        } else {
            "Task $name is not running"
        }
    }
}