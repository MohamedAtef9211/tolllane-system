package com.egabi.tls;

import com.egabi.tls.controller.BaseFxController;
import com.egabi.tls.controller.LoginController;
import com.egabi.tls.controller.model.ViewLoader;
import com.egabi.tls.service.FetchData;
import com.egabi.tls.utils.ScreenUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class TollaneJavaFxApplication extends Application {

    private static ConfigurableApplicationContext applicationContext;
    @Qualifier("scheduler")
    private FetchData fetchData;

    @Override
    public void init() throws Exception {
        this.applicationContext = SpringApplication.run(TollaneUISpringApplication.class);
        this.fetchData = this.applicationContext.getBean(FetchData.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ViewLoader loader = loadHomePage().loadView();
        stage = loader.getStage();
        stage.setHeight(ScreenUtils.SCREEN_HEIGHT);
        stage.setWidth(ScreenUtils.SCREEN_WIDTH);
        stage.setMaximized(true);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();
        Stage finalStage = stage;
        stage.setOnCloseRequest(event -> {
            event.consume();
            loadHomePage().closureAction(finalStage);
        });
//        BaseFxController controller = loader.getLoader().getController();
//        Map<String,Object> values = new HashMap<>();
//        values.put(LoginController.LoginAttributes.USERNAME.name(),"start");
//        fetchData.fetchAndUpdateDataScene(controller,values);
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
