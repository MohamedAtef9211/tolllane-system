package com.egabi.tls.service;

import com.egabi.tls.event.AddNewVehicleEvent;
import com.egabi.tls.model.BaseResponse;
import com.egabi.tls.model.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ITSService {

    private final ApplicationEventPublisher applicationEventPublisher;

    public BaseResponse addVehicleToQueue(Vehicle vehicle){
        try {
            applicationEventPublisher.publishEvent(
                    new AddNewVehicleEvent(this, vehicle));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new BaseResponse("Data submitted successfully!");
    }
}
