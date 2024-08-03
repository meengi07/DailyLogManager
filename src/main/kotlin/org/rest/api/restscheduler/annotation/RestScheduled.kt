package org.rest.api.restscheduler.annotation

import org.springframework.core.annotation.AliasFor
import org.springframework.scheduling.annotation.Scheduled

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Scheduled
annotation class RestScheduled(
    @get:AliasFor(annotation = Scheduled::class, attribute = "cron")
    val cron: String = "",

    @get:AliasFor(annotation = Scheduled::class, attribute = "fixedDelay")
    val fixedDelay: Long = -1L,

    @get:AliasFor(annotation = Scheduled::class, attribute = "fixedRate")
    val fixedRate: Long = -1L,

    val name: String = ""
)
