package com.egabi.tls.controller;

import com.egabi.tls.service.CustomService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class LoginController extends BaseFxController{
    @Autowired
    private CustomService customService;
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
        return "/ui/login-view.fxml";
    }

    @Override
    public String stageTitle() {
        return "تسجيل الدخول";
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        panelForm.setLayoutX(SCREEN_UTILS.getCenteredWidth());
        panelForm.setLayoutY(SCREEN_UTILS.getCenteredHeight());
    }

    public void onSubmit(ActionEvent actionEvent) throws IOException {
        if(StringUtils.isEmpty(username.getText())) {
            message.setText("please enter username");
            return;
        }

        boolean match = customService.appUsers().stream().anyMatch(user -> user.getUsername().toLowerCase().equalsIgnoreCase(username.getText()));

        if(!match){
            message.setText("username not found");
            return;
        }

        message.setText("Login Successfully");
        //JAVA_FX_UTILS.navigateToController(testView,actionEvent,null);
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
