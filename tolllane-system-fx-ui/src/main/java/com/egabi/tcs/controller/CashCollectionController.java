package com.egabi.tcs.controller;

import com.egabi.tcs.config.LaneDataConfig;
import com.egabi.tcs.controller.component.VehicleInfoBox;
import com.egabi.tcs.controller.component.VehicleTypeButton;
import com.egabi.tcs.event.AddNewVehicleEvent;
import com.egabi.tcs.model.Vehicle;
import com.egabi.tcs.model.VehicleType;
import com.egabi.tcs.model.VehicleTypeIcons;
import com.egabi.tcs.service.VehiclesTypesService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import javax.annotation.PreDestroy;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
@Controller
public class CashCollectionController extends BaseFxController {
    @Autowired
    private VehiclesTypesService vehiclesTypesService;

    @Autowired
    private LaneDataConfig laneDataConfig;

    private ScheduledExecutorService scheduledExecutorService;


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
    private ImageView vehicleImageView;

    @FXML
    private Label vehicleTypeInfo;

    @FXML
    private Label currentTime;

    @FXML
    private Label currentDate;

    @FXML
    private TextField plateNumberText;

    @FXML
    private TextField tagIdText;
    private boolean isPageInitialized = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isPageInitialized = true;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduleTimeAndUpdateFields();
        vehicleTypeList = vehiclesTypesService.findAllVehicles();
        renderLaneData();
        checkRenderingVehicle();
//        if(queueBox != null && queueBox.getChildren().isEmpty()) {
//            generateVehicleInfoBox();
//        }
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
        currentVehicle = vehicleQueue.element();
        plateNumberText.setText(currentVehicle.getPlateNo());
        Platform.runLater(() -> {
            if(currentVehicle.getANBRImage() != null){
                try {
                    String newImageString = "data:image/PNG;base64," + currentVehicle.getANBRImage();
                    vehicleImageView.setImage(new Image(newImageString));
                } catch (Exception e) {
                    e.printStackTrace();
                    loadDefaultImage();
                }
            }
            vehicleTypeBox.getChildren().clear();
            renderVehicleTypes();
            generateVehicleInfoBox();
            updateVehicleTypeWithData(currentVehicle);
        });
    }

    private void renderEmptyVehicleData() {
        currentVehicle = null;
        plateNumberText.setText(null);
        loadDefaultImage();
        vehicleTypeBox.getChildren().clear();
        renderVehicleTypes();
        queueBox.getChildren().clear();
        feesAmount.setText("0");
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
        feesAmount.setText(vehicleType.getFees() + "");
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

    public void createManualVehicleRecord(ActionEvent actionEvent) {
//        currentVehicle = new Vehicle(
//                new Axes(10, 50),
//                List.of(1500, 2300),
//                40000,
//                "abc123",
//                "BUS",
//                "ط ص و - 0943",
//                "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAYjPcPAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAASUVORK5CYII="
//        );
        currentVehicle = new Vehicle();
        currentVehicle.setVehicleType("PRIVATE");
        addVehicleToFirstOfQueue(currentVehicle);
    }

    private void addVehicleToFirstOfQueue(Vehicle vehicle) {
        addVehicleToFront(vehicle);
        generateVehicleInfoBox();
        plateNumberText.setDisable(false);
        renderPageWithVehicleData();
    }

    public void payInCashAction(ActionEvent event) {
        Alert alert;
        ButtonType cancel = new ButtonType(JAVA_FX_UTILS.getValueFromBundle("dialog.button.cancel.label"), ButtonBar.ButtonData.CANCEL_CLOSE);
        if(feesAmount.getText() == null || (feesAmount.getText() != null && feesAmount.getText().equalsIgnoreCase("0"))){
            alert = new Alert(Alert.AlertType.ERROR,
                    "Amount must ",
                    cancel);
            alert.show();
        }
        else {
            ButtonType ok = new ButtonType("Print", ButtonBar.ButtonData.OK_DONE);
            alert = new Alert(Alert.AlertType.INFORMATION,
                    "Do you want to Pay in cash with amount " + feesAmount.getText(),
                    ok,
                    cancel);
            alert.setTitle("Pay In Cash");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ok) {
                printReceipt();
                endProcess();
            }
        }
    }

    private void printReceipt() {
        System.err.println("PRINTING RECEIPT");
    }

    public void checkSubscription(ActionEvent actionEvent) {
        ButtonType cancel = new ButtonType(JAVA_FX_UTILS.getValueFromBundle("dialog.button.cancel.label"), ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "This user is already subscribed",
                cancel);
        alert.setTitle("Check Subscription");
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.get() == ok) {
//            printReceipt();
//            endProcess();
//        }
        if(!checkUserSubscription()){
            alert.setContentText("This user is not subscribed");
            alert.setAlertType(Alert.AlertType.ERROR);
        }
        alert.show();
    }

    //TODO: add check user sub from backend
    private boolean checkUserSubscription() {
        return true;
    }

    public void emergencyAction(ActionEvent event) {
    }

    public void endProcess() {
        if (vehicleQueue != null && !vehicleQueue.isEmpty()) {
            vehicleQueue.remove();
        }
        currentVehicle = null;
        checkRenderingVehicle();
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



    private void scheduleTimeAndUpdateFields() {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yy");
                LocalDate today = LocalDate.now();
                String formattedDate = dateFormatter.format(today);
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalTime now = LocalTime.now();
                String formattedTime = timeFormatter.format(now);
                javafx.application.Platform.runLater(() -> {
                    currentDate.setText(formattedDate);
                    currentTime.setText(formattedTime);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void addVehicleToFront(Vehicle vehicle) {
        ((LinkedList<Vehicle>) vehicleQueue).addFirst(vehicle);
    }

    public VehicleType getVehicleByCode(String code) {
        return vehicleTypeList.stream()
                .filter(vehicle -> vehicle.getCode().equalsIgnoreCase(code))
                .findFirst().orElse(null);
    }

    private void loadDefaultImage(){
        vehicleImageView.setImage(new Image("/ui/images/default-car.png"));
    }

    @Override
    public String getFxmlViewPath() {
        return "/ui/views/cash-collection.fxml";
    }

    @Override
    public String stageTitle() {
        return JAVA_FX_UTILS.getValueFromBundle("stage.cash-collection.title");
    }

    @PreDestroy
    public void destroySchedule(){
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdownNow();
        }
    }
}
