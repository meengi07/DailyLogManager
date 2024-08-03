package org.rest.api.example;

import org.rest.api.restscheduler.annotation.RestScheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTest {

    @RestScheduled(name="test1", fixedRate = 1000)
    public void test() {
        System.out.println("Hello, World!");
    }

}
