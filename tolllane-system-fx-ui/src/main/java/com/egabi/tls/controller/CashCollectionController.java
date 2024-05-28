package com.egabi.tls.controller;

import org.springframework.stereotype.Controller;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
@Controller
public class CashCollectionController extends BaseFxController{

    @FXML
    private HBox navbar;

    @FXML
    private HBox carQueueBox;

    @FXML
    private VBox gateIdentificationBox;

    @FXML
    private VBox carDetailsBox;

    @FXML
    private VBox carTypeBox;

    @Override
    public String getFxmlViewPath() {
        return "/ui/views/cash-collection.fxml";
    }

    @Override
    public String stageTitle() {
        return JAVA_FX_UTILS.getValueFromBundle("stage.cash-collection.title");
    }
}
