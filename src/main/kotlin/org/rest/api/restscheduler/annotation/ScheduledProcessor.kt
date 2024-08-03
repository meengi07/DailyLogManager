package org.rest.api.restscheduler.annotation

import org.springframework.beans.factory.InitializingBean
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import org.springframework.util.ReflectionUtils
import java.lang.reflect.Method
import java.time.Duration

@Component
class ScheduledProcessor (
    private val scheduleManager: ScheduleManager
) : ApplicationContextAware, InitializingBean {

    private lateinit var applicationContext: ApplicationContext

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
    }

    override fun afterPropertiesSet() {
        val beans = applicationContext.getBeansWithAnnotation(Component::class.java)
        beans.values.forEach { bean ->
            val methods = ReflectionUtils.getAllDeclaredMethods(bean::class.java)

            methods.forEach { method ->
                val restScheduled = method.getAnnotation(RestScheduled::class.java)

                if (restScheduled != null) {
                    registerScheduledTask(bean, method, restScheduled)
                }
            }
        }
    }

    private fun registerScheduledTask(bean: Any, method: Method, restScheduled: RestScheduled) {
        val taskName = restScheduled.name
        val interval: Duration = if (restScheduled.fixedRate > 0) {
            Duration.ofMillis(restScheduled.fixedRate)
        } else {
            Duration.ofMillis(1000) // 기본 인터벌을 1초로 설정
        }

        val task = Runnable {
            ReflectionUtils.makeAccessible(method)
            method.invoke(bean)
        }

        scheduleManager.scheduleTask(taskName, interval, task)
    }
}