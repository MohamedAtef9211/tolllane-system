package com.egabi.tcs.service;

import com.egabi.tcs.controller.BaseFxController;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//@Service("scheduler")
public class SchedulerFetch {
    private ScheduledExecutorService scheduledExecutorService;

    //@PostConstruct
    public void init(){
        System.err.println("inside Init SchedulerFetch");
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void fetchAndUpdateDataScene(BaseFxController controller, Map<String, Object> values) {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
        }, 0, 2000, TimeUnit.MILLISECONDS);
    }

    //@PreDestroy
    public void destroySchedule(){
        System.err.println("inside Destroy SchedulerFetch");
        scheduledExecutorService.shutdownNow();
    }
}
