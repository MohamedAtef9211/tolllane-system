package com.egabi.tls.controller.component;

import com.egabi.tls.model.VehicleType;
import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class VehicleTypeButton extends Button {
    private String name;
    private VehicleType vehicleType;

    public VehicleTypeButton(String name, VehicleType vehicleType) {
        super(name);
        this.name = name;
        this.vehicleType = vehicleType;
    }
}
