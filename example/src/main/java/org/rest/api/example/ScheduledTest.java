package org.rest.api.example;

import static java.lang.Thread.sleep;

import org.rest.api.restscheduler.annotation.RestScheduled;
import org.rest.api.restscheduler.annotation.ScheduleManager;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTest {

    private final ScheduleManager scheduleManager;

    public ScheduledTest(ScheduleManager scheduleManager) {
        this.scheduleManager = scheduleManager;
    }


    @RestScheduled(name="test1", fixedRate = 1000)
    public void test() {
        System.out.println("Hello, World!");
    }

    public void test1() throws InterruptedException {
        System.out.println("Stopping...");
        sleep(5000);

        scheduleManager.stopTask("test1");
    }
}
