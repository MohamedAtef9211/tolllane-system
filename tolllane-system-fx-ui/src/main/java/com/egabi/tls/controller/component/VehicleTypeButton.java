package com.egabi.tls.controller.component;

import com.egabi.tls.model.VehicleType;
import com.egabi.tls.model.VehicleTypeIcons;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Data;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VehicleTypeButton extends Button implements Initializable {

    @FXML
    private Button rootButton;
    @FXML
    private Separator seperator;

    @FXML
    private ImageView vehicleImage;

    @FXML
    private Label vehicleLabel;

    private String name;
    private VehicleType vehicleType;

    private boolean isSelected = false;

    public VehicleTypeButton(String name, VehicleType vehicleType , boolean isSelected) {
        super(name);
        this.name = name;
        this.vehicleType = vehicleType;
        this.isSelected = isSelected;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/views/components/vehicle-type-button.fxml"));
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
        vehicleLabel.setText(vehicleType.getDesc());
        if (isSelected) {
            ColorAdjust grayscaleEffect = new ColorAdjust();
            grayscaleEffect.setSaturation(-100);
            grayscaleEffect.setBrightness(-100.5);
            grayscaleEffect.setContrast(-100.0);
            vehicleImage.setEffect(grayscaleEffect);
            rootButton.getStyleClass().removeAll();
            rootButton.getStyleClass().add("selected-button");
        }
        vehicleImage.setImage(new Image(VehicleTypeIcons.getImagePath(vehicleType.getCode().toUpperCase())));
    }
}
