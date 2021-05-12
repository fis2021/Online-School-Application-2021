package org.fis.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.fis.project.Main;

public class TeacherSubjectsController {

    @FXML
    private Label helloMessage;

    private static String username;
    @FXML
    public void switchToLogIn() throws Exception {
        Main.setRoot("login");
    }

    public static void setUsername(String username) {
        TeacherSubjectsController.username = username;
    }

    @FXML
    public void switchToTeacher() throws Exception {

        Main.setRoot("teacher");

        TeacherController controller=Main.getPath().getController();
        controller.setHelloMessage("Welcome "+username);
        controller.populateDataFromLogIn(username);
    }

    @FXML
    public void setHelloMessage(String message) {
        helloMessage.setText(message);
    }
}
