package com.egabi.tls.service;

import com.egabi.tls.controller.BaseFxController;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service("scheduler")
@Primary
public class SchedulerFetch implements FetchData{
    private ScheduledExecutorService scheduledExecutorService;

    private final UpdateService updateService;

    public SchedulerFetch(UpdateService updateService) {
        this.updateService = updateService;
    }

    @PostConstruct
    public void init(){
        System.err.println("inside Init SchedulerFetch");
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void fetchAndUpdateDataScene(BaseFxController controller, Map<String, Object> values) {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            updateService.updateControllerData(controller,values);
        }, 0, 2000, TimeUnit.MILLISECONDS);
    }

    @PreDestroy
    public void destroySchedule(){
        System.err.println("inside Destroy SchedulerFetch");
        scheduledExecutorService.shutdownNow();
    }
}
