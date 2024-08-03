package org.rest.api.example;

import org.rest.api.restscheduler.annotation.ScheduleManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestController {

    private final ScheduleManager scheduleManager;

    public TestController(ScheduleManager scheduleManager) {
        this.scheduleManager = scheduleManager;
    }

    @RequestMapping("/stop")
    public void stopTest() {
        System.out.println("Stopping...");
        scheduleManager.stopTask("test1");
    }
}
