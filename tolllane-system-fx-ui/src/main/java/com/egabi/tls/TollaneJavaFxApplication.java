package com.egabi.tls;

import com.egabi.tls.controller.BaseFxController;
import com.egabi.tls.controller.CashCollectionController;
import com.egabi.tls.controller.LoginController;
import com.egabi.tls.model.ViewLoader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class TollaneJavaFxApplication extends Application {

    private static ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        this.applicationContext = SpringApplication.run(TollaneUISpringApplication.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BaseFxController homePage = loadHomePage();
        ViewLoader loader = homePage.loadView();
        stage = loader.getStage();
//        stage.setHeight(ScreenUtils.SCREEN_HEIGHT);
//        stage.setWidth(ScreenUtils.SCREEN_WIDTH);
        stage.setMaximized(true);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();
        Stage finalStage = stage;
        stage.setOnCloseRequest(event -> {
            event.consume();
            homePage.closureAction(finalStage);
        });
    }

    @Override
    public void stop() throws Exception {
        applicationContext.stop();
        Platform.exit();
    }

    public static ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }

    private BaseFxController loadHomePage(){
        return new CashCollectionController();
    }
}
