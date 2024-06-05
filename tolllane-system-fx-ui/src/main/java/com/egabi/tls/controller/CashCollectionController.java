package com.egabi.tls.controller;

import com.egabi.tls.config.LaneDataConfig;
import com.egabi.tls.controller.component.VehicleInfoBox;
import com.egabi.tls.controller.component.VehicleTypeButton;
import com.egabi.tls.event.AddNewVehicleEvent;
import com.egabi.tls.model.Axes;
import com.egabi.tls.model.Vehicle;
import com.egabi.tls.model.VehicleType;
import com.egabi.tls.model.VehicleTypeIcons;
import com.egabi.tls.service.VehiclesTypesService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class CashCollectionController extends BaseFxController {
    @Autowired
    private VehiclesTypesService vehiclesTypesService;

    @Autowired
    private LaneDataConfig laneDataConfig;

    private Queue<Vehicle> vehicleQueue = new LinkedList<>();

    private Vehicle currentVehicle;

    private List<VehicleType> vehicleTypeList;
    @FXML
    private VBox vehicleTypeBox;
    @FXML
    private Button payInCashButton;
    @FXML
    private HBox queueBox = new HBox();

    @FXML
    private Label laneNumber;

    @FXML
    private Label gate;

    @FXML
    private Label collectorUser;

    @FXML
    private Label laneRoad;

    @FXML
    private Label laneType;

    @FXML
    private Label feesAmount;

    @FXML
    private ImageView vehicleTypeImage;

    @FXML
    private Label vehicleTypeInfo;

    private boolean isPageInitialized = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.err.println("inside initialize");
        isPageInitialized = true;
        vehicleTypeList = vehiclesTypesService.findAllVehicles();
        renderLaneData();
        checkRenderingVehicle();
//        renderVehicleTypes();
        if(queueBox != null && queueBox.getChildren().isEmpty()) {
            generateVehicleInfoBox();
        }
    }

    private void generateVehicleInfoBox() {
        queueBox.getChildren().clear();
        vehicleQueue.forEach(vehicle -> {
            Platform.runLater(() -> {
                VehicleInfoBox vehicleButton = new VehicleInfoBox(vehicle,  queueBox.getChildren().isEmpty());
                vehicleButton.getDeleteImage().setOnMouseClicked(event -> endProcess());
                queueBox.getChildren().add(vehicleButton);
            });
        });
    }

    private void renderLaneData() {
        laneNumber.setText(laneDataConfig.getLaneData().getLaneNumber());
        gate.setText(laneDataConfig.getLaneData().getGate());
        collectorUser.setText("TC002");
        laneRoad.setText(laneDataConfig.getLaneData().getLaneRoad());
        laneType.setText(laneDataConfig.getLaneData().getLaneType());
    }

    private void checkRenderingVehicle() {
        if (!vehicleQueue.isEmpty()) {
            renderPageWithVehicleData();
        } else {
            renderEmptyVehicleData();
        }
    }

    private void renderPageWithVehicleData() {
        System.err.println("inside printEventData and Queue size is " + vehicleQueue.size());
        currentVehicle = vehicleQueue.element();
        System.out.println("currentVehicle = " + currentVehicle);
        System.err.println("inside printEventData and Queue size is " + vehicleQueue.size());
        Platform.runLater(() -> {
            vehicleTypeBox.getChildren().clear();
            renderVehicleTypes();
            generateVehicleInfoBox();
            updateVehicleTypeWithData(currentVehicle);
        });
    }

    private void renderEmptyVehicleData() {
        System.err.println("inside renderEmptyVehicleData");
        currentVehicle = null;
        vehicleTypeBox.getChildren().clear();
        renderVehicleTypes();
        queueBox.getChildren().clear();
        feesAmount.setText("0");
        //TODO: Update fees image with new one
        vehicleTypeImage.setImage(new Image(VehicleTypeIcons.getImagePath("NaN")));
        vehicleTypeInfo.setText("لا يوجد");
    }

    private void renderVehicleTypes() {
        if (!vehicleTypeList.isEmpty()) {
            vehicleTypeList.forEach(vehicleType -> {
                VehicleTypeButton button = new VehicleTypeButton(vehicleType.getDesc(), vehicleType,(currentVehicle == null ? false
                        : currentVehicle.getVehicleType().equalsIgnoreCase(vehicleType.getCode())));
                button.setOnAction(event -> {
                    currentVehicle.setVehicleType(vehicleType.getCode());
                    onSelectVehicle(event,vehicleType);
                });
                vehicleTypeBox.getChildren().add(button);
            });
        }
    }

    private void onSelectVehicle(ActionEvent event, VehicleType vehicleType){
        vehicleTypeBox.getChildren().clear();
        renderVehicleTypes();
        generateVehicleInfoBox();
        updateVehicleTypeWithData(currentVehicle);
    }

    private void updateVehicleTypeWithData(Vehicle vehicle){
        VehicleType vehicleType = getVehicleByCode(vehicle.getVehicleType());
        //TODO Get new fees and isFeesCalculated
        feesAmount.setText(vehicleType.getFees() + "");
        //TODO: Update fees image with new one
        vehicleTypeImage.setImage(new Image(VehicleTypeIcons.getImagePath(vehicleType.getCode().toUpperCase())));
        vehicleTypeInfo.setText(vehicleType.getDesc());
    }

    @EventListener
    public void addVehicleToQueueListener(AddNewVehicleEvent event) {
        System.err.println("inside addVehicleToQueueListener");
        addVehicleToQueue(event.getVehicle());

        if (currentVehicle == null && isPageInitialized) {
            renderPageWithVehicleData();
        }
    }

    private void addVehicleToQueue(Vehicle vehicle) {
        vehicleQueue.add(vehicle);
        if(queueBox != null) {
            Platform.runLater(() -> {
                VehicleInfoBox vehicleButton = new VehicleInfoBox(vehicle,  queueBox.getChildren().isEmpty());
                    vehicleButton.getDeleteImage().setOnMouseClicked(event -> endProcess());
                queueBox.getChildren().add(vehicleButton);
            });
        }
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
        generateVehicleInfoBox();
        renderPageWithVehicleData();
    }

    public void payInCashAction(ActionEvent event) {
        endProcess();
    }

    public void endProcess() {
        if (vehicleQueue != null && !vehicleQueue.isEmpty()) {
            vehicleQueue.remove();
        }
        System.err.println("inside endPayment and Queue size is " + vehicleQueue.size());
        currentVehicle = null;
        checkRenderingVehicle();
    }

    public void addVehicleToFront(Vehicle vehicle) {
        ((LinkedList<Vehicle>) vehicleQueue).addFirst(vehicle);
    }

    public VehicleType getVehicleByCode(String code) {
        return vehicleTypeList.stream()
                .filter(vehicle -> vehicle.getCode().equalsIgnoreCase(code))
                .findFirst().orElse(null);
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
