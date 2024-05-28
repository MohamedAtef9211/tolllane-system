package com.egabi.tls.controller.model;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class ViewLoader {

    private FXMLLoader loader;
    private Stage stage;

    public ViewLoader(FXMLLoader loader , Stage stage) {
        this.loader = loader;
        this.stage = stage;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public Stage getStage() {
        return stage;
    }
}
