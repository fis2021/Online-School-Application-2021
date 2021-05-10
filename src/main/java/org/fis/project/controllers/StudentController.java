package org.fis.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.fis.project.Main;

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
