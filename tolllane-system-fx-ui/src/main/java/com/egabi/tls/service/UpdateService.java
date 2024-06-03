package com.egabi.tls.service;

import com.egabi.tls.controller.BaseFxController;
import javafx.application.Platform;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UpdateService {

    public void updateControllerData(BaseFxController controller , Map<String,Object> values){
        Platform.runLater(() -> {
            if (values != null && !values.isEmpty()) {
                values.forEach((s, o) -> controller.additionalValues.put(s,o));
            }
            controller.updateStageData();
        });
    }
}
