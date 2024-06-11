package com.egabi.tcs;

import com.egabi.tcs.controller.BaseFxController;
import com.egabi.tcs.controller.CashCollectionController;
import com.egabi.tcs.controller.LoginController;
import com.egabi.tcs.model.ViewLoader;
import com.egabi.tcs.utils.ScreenUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class TollaneJavaFxApplication extends Application {

    private static ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
//        this.applicationContext = SpringApplication.run(TollaneUISpringApplication.class);
        this.applicationContext = new SpringApplicationBuilder(TollaneUISpringApplication.class)
                .web(WebApplicationType.SERVLET)
                .run();
        for(String s : this.applicationContext.getBeanDefinitionNames()){
            if (s.startsWith("com.egabi.")) {
                System.out.println("s = " + s);
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        BaseFxController homePage = loadHomePage();
        ViewLoader loader = homePage.loadView();
        stage = loader.getStage();
        stage.setHeight(ScreenUtils.SCREEN_HEIGHT);
        stage.setWidth(ScreenUtils.SCREEN_WIDTH);
        stage.setMaximized(true);
 //       stage.setFullScreen(true);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();
//        Stage finalStage = stage;
//        stage.setOnCloseRequest(event -> {
//            event.consume();
//            //homePage.closureAction(finalStage);
//            Alert alert = new Alert(Alert.AlertType.WARNING,"yOU CANT LOGOUT BITCH");
//            alert.show();
//        });
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
        return new LoginController();
    }
}
