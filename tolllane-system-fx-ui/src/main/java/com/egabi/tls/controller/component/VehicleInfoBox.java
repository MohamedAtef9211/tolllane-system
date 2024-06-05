package com.egabi.tls.controller.component;

import com.egabi.tls.model.Vehicle;
import com.egabi.tls.model.VehicleTypeIcons;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Getter
public class VehicleInfoBox extends AnchorPane implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView deleteImage;

    @FXML
    private ImageView vehicleImage;

    @FXML
    private Label plateNumber;

    private Vehicle vehicle;

    private boolean isSelected = false;

    public VehicleInfoBox(Vehicle vehicle, boolean isSelected) {
        this.vehicle = vehicle;
        this.isSelected = isSelected;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/views/components/vehicle-info-box.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String imagePath = VehicleTypeIcons.getImagePath(vehicle.getVehicleType().toUpperCase());
//        String imagePath = VehicleTypeIcons.getImagePath("PRIVATE");
        Image image = new Image(imagePath);
        if (!isSelected) {
            ColorAdjust grayscaleEffect = new ColorAdjust();
            grayscaleEffect.setSaturation(-100);
            grayscaleEffect.setBrightness(-100.5);
            grayscaleEffect.setContrast(-100.0);
            vehicleImage.setEffect(grayscaleEffect);
            plateNumber.getStyleClass().clear();
            plateNumber.getStyleClass().add("vehicle-info-text");
            plateNumber.getStyleClass().clear();
            plateNumber.getStyleClass().add("vehicle-info-text");
            rootPane.getStyleClass().clear();
            rootPane.getStyleClass().add("vehicle-info-box");
            rootPane.getChildren().remove(deleteImage);
        }
        vehicleImage.setImage(image);
        plateNumber.setText(vehicle.getPlateNo());
    }
}
