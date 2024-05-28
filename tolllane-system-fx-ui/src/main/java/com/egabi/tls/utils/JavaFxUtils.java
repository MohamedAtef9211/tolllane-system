package com.egabi.tls.utils;

import com.egabi.tls.controller.BaseFxController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class JavaFxUtils {

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("bundle",new Locale("ar","EG"));


    public static void navigateToController(BaseFxController controller, ActionEvent actionEvent, Map<String, Object> data) {

        try {
            FXMLLoader fxmlLoader = controller.getFxmlLoader();
            if (data != null && !data.isEmpty()) {
                data.put("nav","Hello");
                data.forEach((s, o) -> controller.additionalValues.put(s,o));
                controller.updateStageData();
            }
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getValueFromBundle(String key){
        return resourceBundle.getString(key);
    }
}
