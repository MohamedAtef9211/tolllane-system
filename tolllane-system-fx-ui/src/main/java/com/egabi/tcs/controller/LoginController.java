package com.egabi.tcs.controller;

import com.egabi.tcs.service.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class LoginController extends BaseFxController{
    @Autowired
    private LoginService loginService;

    @Autowired
    private CashCollectionController cashCollectionController;
    @FXML
    public TextField username;
    @FXML
    public AnchorPane panelForm;
    @FXML
    private PasswordField password;

    @FXML
    private Button button;

    @FXML
    private Text message;
    @Override
    public String getFxmlViewPath() {
        return "/ui/views/login.fxml";
    }

    @Override
    public String stageTitle() {
        return JAVA_FX_UTILS.getValueFromBundle("stage.login.title");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        panelForm.setLayoutX(SCREEN_UTILS.getCenteredWidth());
        panelForm.setLayoutY(SCREEN_UTILS.getCenteredHeight());
    }

    public void onSubmit(ActionEvent actionEvent) throws IOException {
//        if(StringUtils.isEmpty(username.getText()) || StringUtils.isEmpty(password.getText())) {
//            message.setText("please enter required data");
//            return;
//        }
//
//        boolean match = loginService.login(new User(username.getText(),password.getText()));
//
//        if(!match){
//            message.setText("username not found");
//            return;
//        }
//
//        message.setText("Login Successfully");
        JAVA_FX_UTILS.navigateToController(cashCollectionController,actionEvent,null);
    }

    @Override
    public void updateStageData() {
        super.updateStageData();
        //username.setText((String) additionalValues.get("usernameValue"));
    }

    public enum LoginAttributes{
        USERNAME,
        PASSWORD
    }

}
