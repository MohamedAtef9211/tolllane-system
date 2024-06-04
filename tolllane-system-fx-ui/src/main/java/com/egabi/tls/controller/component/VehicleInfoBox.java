package com.egabi.tls.controller.component;

import com.egabi.tls.model.Vehicle;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;


public class VehicleInfoBox extends HBox {
    @FXML
    private ImageView deleteImage;

    @FXML
    private ImageView vehicleImage;

    @FXML
    private Label plateNumber;

    private Vehicle vehicle;

    public VehicleInfoBox(Vehicle vehicle) {
        this.vehicle = vehicle;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/views/components/vehicle-info-box.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
