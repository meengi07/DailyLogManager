package org.rest.api.restscheduler.annotation

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.scheduling.TaskScheduler
import java.time.Duration
import java.util.concurrent.ScheduledFuture

class ScheduleManagerTest {

    private lateinit var taskScheduler: TaskScheduler
    private lateinit var scheduleManager: ScheduleManager
    private lateinit var scheduledFuture: ScheduledFuture<*>

    @BeforeEach
    fun setUp() {
        taskScheduler = mock(TaskScheduler::class.java)
        scheduleManager = ScheduleManager(taskScheduler)
        scheduledFuture = mock(ScheduledFuture::class.java)
    }

    @Test
    fun `schedule_start_task_test`() {
        `when`(
            taskScheduler.scheduleAtFixedRate(
                any(Runnable::class.java),
                Duration.ofMillis(anyLong()))
        ).thenReturn(scheduledFuture)

        val millis = Duration.ofMillis(1000L)
        scheduleManager.scheduleTask("task1", millis, {
            println("Task task1 executed")
        })

        verify(taskScheduler, times(1)).scheduleAtFixedRate(any(Runnable::class.java), anyLong())
        assertTrue(scheduleManager.isTaskRunning("task1"))
    }

    @Test
    fun `schedule_stop_test`() {
        `when`(
            taskScheduler.scheduleAtFixedRate(
                any(Runnable::class.java),
                Duration.ofMillis(anyLong()))
        ).thenReturn(scheduledFuture)
        `when`(scheduledFuture.cancel(false)).thenReturn(true)

        val millis = Duration.ofMillis(1000L)
        scheduleManager.scheduleTask("task1", millis) {
            println("Task executed")
        }
        scheduleManager.stopTask("task1")

        verify(scheduledFuture, times(1)).cancel(false)
        assertFalse(scheduleManager.isTaskRunning("task1"))
    }

    @Test
    fun `schedule_change_interval_test`() {
        `when`(
            taskScheduler.scheduleAtFixedRate(
                any(Runnable::class.java),
                Duration.ofMillis(anyLong()))
        ).thenReturn(scheduledFuture)

        scheduleManager.scheduleTask("task2", Duration.ofMillis(2000L)) {
            println("Task executed")
        }
        scheduleManager.changeTaskInterval("task2", Duration.ofMillis(5000L))

        verify(scheduledFuture, times(1)).cancel(false)
        verify(taskScheduler, times(1)).scheduleAtFixedRate(any(Runnable::class.java), eq(2000L))
    }

}