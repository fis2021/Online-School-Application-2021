package org.fis.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import org.fis.project.Main;
import org.fis.project.services.UserService;

public class StudentController {
    @FXML
    private Label helloMessage;

    public void setHelloMessage(String username) {
        helloMessage.setText(username);
    }

    @FXML
    public void switchToLogIn() throws Exception {
        Main.setRoot("login");
    }
}
