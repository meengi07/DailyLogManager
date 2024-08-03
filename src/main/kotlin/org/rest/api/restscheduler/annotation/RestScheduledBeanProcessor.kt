package org.rest.api.restscheduler.annotation

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import org.springframework.util.ReflectionUtils
import java.lang.reflect.Method

@Component
class RestScheduledBeanProcessor(
    private val scheduleManager: ScheduleManager
): BeanPostProcessor {
    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
        val methods = ReflectionUtils.getAllDeclaredMethods(bean::class.java)

        for (method in methods) {
            val restScheduled = method.getAnnotation(RestScheduled::class.java)
            if (restScheduled != null) {
                registerScheduledTask(bean, method, restScheduled)
            }
        }

        return bean
    }

    private fun registerScheduledTask(bean: Any, method: Method, restScheduled: RestScheduled) {
        val task = Runnable {
            ReflectionUtils.invokeMethod(method, bean)
        }

        if (restScheduled.fixedRate > 0) {
            scheduleManager.scheduleTask(restScheduled.name, restScheduled.fixedRate, task)
        } else if (restScheduled.fixedDelay > 0) {
            scheduleManager.scheduleTask(restScheduled.name, restScheduled.fixedDelay, task)
        }
    }
}