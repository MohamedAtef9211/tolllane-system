package com.egabi.tcs.event;

import com.egabi.tcs.model.Vehicle;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class AddNewVehicleEvent extends ApplicationEvent {

    private Vehicle vehicle;
    public AddNewVehicleEvent(Object source , Vehicle vehicle) {
        super(source);
        this.vehicle = vehicle;
    }
}
