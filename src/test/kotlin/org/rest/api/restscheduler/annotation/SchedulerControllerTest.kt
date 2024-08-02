package org.rest.api.restscheduler.annotation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchedulerControllerTest {

    @LocalServerPort
    private val port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private fun baseUrl(path: String): String = "http://localhost:$port/scheduler$path"

    @Test
    fun `scheduler_start_test` () {
        val startResponse: ResponseEntity<String> =
            restTemplate.postForEntity(baseUrl("/start?name=task1&interval=1000"), null, String::class.java)
        assertEquals(HttpStatus.OK, startResponse.statusCode)
    }

    @Test
    fun `scheduler_stop_test` () {
        val stopResponse: ResponseEntity<String> =
            restTemplate.postForEntity(baseUrl("/stop?name=task1"), null, String::class.java)
        assertEquals(HttpStatus.OK, stopResponse.statusCode)
    }

    @Test
    fun `scheduler_change_interval_test` () {
        val changeIntervalResponse: ResponseEntity<String> =
            restTemplate.postForEntity(baseUrl("/change?name=task1&interval=2000"), null, String::class.java)
        assertEquals(HttpStatus.OK, changeIntervalResponse.statusCode)
    }

    @Test
    fun `scheduler_status_test` () {
        val statusResponse: ResponseEntity<String> =
            restTemplate.getForEntity(baseUrl("/status?name=task1"), String::class.java)
        assertEquals(HttpStatus.OK, statusResponse.statusCode)
    }

    @Test
    fun `scheduler_start_and_change_interval_test`() {

        restTemplate.postForEntity(baseUrl("/start?name=task2&interval=2000"), null, String::class.java)

        val changeResponse: ResponseEntity<String> = restTemplate.postForEntity(baseUrl("/change?name=task2&interval=5000"), null, String::class.java)
        assertEquals(HttpStatus.OK, changeResponse.statusCode)
        assertTrue(changeResponse.body?.contains("Task task2 interval changed") == true)
    }

}