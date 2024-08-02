package org.rest.api.restscheduler

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RestSchedulerApplication

fun main(args: Array<String>) {
    runApplication<RestSchedulerApplication>(*args)
}
