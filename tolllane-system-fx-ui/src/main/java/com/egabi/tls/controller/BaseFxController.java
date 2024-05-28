package com.egabi.tls.controller;

import com.egabi.tls.TollaneJavaFxApplication;
import com.egabi.tls.controller.model.ViewLoader;
import com.egabi.tls.utils.ClassUtils;
import com.egabi.tls.utils.JavaFxUtils;
import com.egabi.tls.utils.ScreenUtils;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.*;

public abstract class BaseFxController implements Initializable {

    protected static final ScreenUtils SCREEN_UTILS = new ScreenUtils();
    protected static final JavaFxUtils JAVA_FX_UTILS = new JavaFxUtils();

    public Map<String, Object> additionalValues = new HashMap<>();

    private final List<Class<? extends Annotation>> annotationClassList = List.of(Component.class, Controller.class, Service.class);

    public abstract String getFxmlViewPath();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public ViewLoader loadView() {
        return loadView(getFxmlLoader());
    }

    public FXMLLoader getFxmlLoader() {
        Locale locale = new Locale("ar", "EG");
        ResourceBundle bundle = ResourceBundle.getBundle("bundle", locale);
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(getFxmlViewPath()), bundle);
        if (isControllerAnnotationPresent()) {
            loader.setControllerFactory(applicationContext()::getBean);
        }
        return loader;
    }

    private ViewLoader loadView(FXMLLoader fxmlLoader) {
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            String css = this.getClass().getResource("/ui/css/style.css").toExternalForm();
            scene.getStylesheets().add(css);
            Stage stage = new Stage();
            stage.setTitle(stageTitle());
            stage.setScene(scene);
            return new ViewLoader(fxmlLoader, stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String stageTitle() {
        return "الوطنية للطرق";
    }

    public void updateStageData() {
        additionalValues.forEach((k, v) -> System.err.println("Key is " + k + " and value is " + v));
    }

    protected ConfigurableApplicationContext applicationContext() {
        return TollaneJavaFxApplication.getApplicationContext();
    }

    private boolean isControllerAnnotationPresent() {
        return annotationClassList.stream().anyMatch(annotationClass -> ClassUtils.isAnnotationPresent(this.getClass(), annotationClass));
    }

    public final void closureAction(Stage stage) {
        ButtonType ok = new ButtonType(JAVA_FX_UTILS.getValueFromBundle("dialog.button.ok.label"), ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType(JAVA_FX_UTILS.getValueFromBundle("dialog.button.cancel.label"), ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Do you want to save before exiting",
                ok,
                cancel);
        alert.setTitle("logout");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ok) {
            stage.close();
        }
    }

}
