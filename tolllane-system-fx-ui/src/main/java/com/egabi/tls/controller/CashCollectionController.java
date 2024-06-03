package com.egabi.tls.controller;

import com.egabi.tls.controller.component.VehicleTypeButton;
import com.egabi.tls.event.AddNewVehicleEvent;
import com.egabi.tls.model.Axes;
import com.egabi.tls.model.Vehicle;
import com.egabi.tls.model.VehicleType;
import com.egabi.tls.service.VehiclesTypesService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ResourceBundle;

@Controller
public class CashCollectionController extends BaseFxController {
    @Autowired
    private VehiclesTypesService vehiclesTypesService;

    private Queue<Vehicle> vehicleQueue = new LinkedList<>();

    private Vehicle currentVehicle;

    private List<VehicleType> vehicleTypeList;
    @FXML
    private VBox vehicleTypeBox;
    @FXML
    private TextField plateNumberText;
    @FXML
    private Button payInCashButton;
    @FXML
    private HBox queueBox;

    public void payInCashAction(ActionEvent event) {
        endProcess();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        vehicleTypeList = vehiclesTypesService.findAllVehicles();
        renderVehicleTypes();
        checkRenderingVehicle();

    }

    @EventListener
    public void addVehicleToQueueListener(AddNewVehicleEvent event) {
        System.err.println("inside addVehicleToQueueListener");
        addVehicleToQueue(event.getVehicle());

        if (currentVehicle == null) {
            renderPageWithVehicleData();
        }
    }

    private void addVehicleToQueue(Vehicle vehicle) {
        vehicleQueue.add(vehicle);
        Platform.runLater(() -> {
            Button vehicleButton = new Button(vehicle.getPlateNo());
            queueBox.getChildren().add(vehicleButton);
        });
    }

    public void createManualVehicleRecord(ActionEvent event) {
        currentVehicle = new Vehicle(
                new Axes(10, 50),
                List.of(1500, 2300),
                40000,
                "abc123",
                "BUS",
                "ط ص و - 0943",
                "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAYjPcPAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAASUVORK5CYII="
        );
        addVehicleToFirstOfQueue(currentVehicle);

    }

    private void addVehicleToFirstOfQueue(Vehicle vehicle) {
        addVehicleToFront(vehicle);
        queueBox.getChildren().clear();
        vehicleQueue.forEach(vehicleObj -> {
            Platform.runLater(() -> {
                Button vehicleButton = new Button(vehicleObj.getPlateNo());
                queueBox.getChildren().add(vehicleButton);
            });
        });
        renderPageWithVehicleData();
    }

    private void renderPageWithVehicleData() {
        System.err.println("inside printEventData and Queue size is " + vehicleQueue.size());
        currentVehicle = vehicleQueue.element();
        System.out.println("currentVehicle = " + currentVehicle);
        System.err.println("inside printEventData and Queue size is " + vehicleQueue.size());
        plateNumberText.setText(currentVehicle.getPlateNo());
    }

    private void checkRenderingVehicle() {
        if (!vehicleQueue.isEmpty()) {
            renderPageWithVehicleData();
        } else {
            renderEmptyVehicleData();
        }
    }

    private void renderVehicleTypes() {
        //vehicleTypeBox.getChildren().removeAll();
        if (!vehicleTypeList.isEmpty()) {
            vehicleTypeList.forEach(vehicleType -> {
                VehicleTypeButton button = new VehicleTypeButton(vehicleType.getDesc(), vehicleType);
                button.setOnAction(event -> {
                    vehicleTypeBox.getChildren().clear();
                    currentVehicle.setVehicleType(button.getVehicleType().getCode());
                    plateNumberText.setText(vehicleType.getCode());
                });
                if (currentVehicle != null && vehicleType.getCode().equalsIgnoreCase(currentVehicle.getVehicleType())) {
                    button.getStyleClass().removeAll();
                    button.getStyleClass().add("selected-button");
                }
                vehicleTypeBox.getChildren().add(button);
            });
        }
    }

    public void endProcess() {
        if (vehicleQueue != null && !vehicleQueue.isEmpty()) {
            vehicleQueue.remove();
            queueBox.getChildren().clear();
            vehicleQueue.forEach(vehicle -> {
                Button vehicleButton = new Button(vehicle.getPlateNo());
                queueBox.getChildren().add(vehicleButton);
            });
        }
        System.err.println("inside endPayment and Queue size is " + vehicleQueue.size());
        currentVehicle = null;
        checkRenderingVehicle();
    }

    private void renderEmptyVehicleData() {
        System.err.println("inside renderEmptyVehicleData");
        plateNumberText.setText("NAN");
    }

    public void addVehicleToFront(Vehicle vehicle) {
        ((LinkedList<Vehicle>) vehicleQueue).addFirst(vehicle);
    }

    @Override
    public String getFxmlViewPath() {
        return "/ui/views/cash-collection.fxml";
    }

    @Override
    public String stageTitle() {
        return JAVA_FX_UTILS.getValueFromBundle("stage.cash-collection.title");
    }
}
