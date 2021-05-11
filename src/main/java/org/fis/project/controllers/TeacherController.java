package org.fis.project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.fis.project.Main;
import org.fis.project.services.CatalogService;

public class TeacherController extends Exception{

    @FXML
    private Label helloMessage;

    @FXML
    public void setHelloMessage(String username) {
        helloMessage.setText(username);
    }

    @FXML
    public void switchToLogIn() throws Exception {
        Main.setRoot("login");
    }


}
