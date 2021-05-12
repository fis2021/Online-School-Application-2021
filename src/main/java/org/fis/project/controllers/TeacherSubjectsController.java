package org.fis.project.controllers;

import javafx.fxml.FXML;
import org.fis.project.Main;

public class TeacherSubjectsController {
    @FXML
    public void switchToLogIn() throws Exception {
        Main.setRoot("login");
    }

    @FXML
    public void switchToTeacher() throws Exception {
        Main.setRoot("teacher");
    }
}
