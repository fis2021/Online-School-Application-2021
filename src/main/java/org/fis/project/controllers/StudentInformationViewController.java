package org.fis.project.controllers;

import javafx.fxml.FXML;
import org.fis.project.Main;

public class StudentInformationViewController {

    @FXML
    public void switchToLogIn() throws Exception {
        Main.setRoot("login");
    }

    @FXML
    public void switchToSubjects() throws Exception {
        Main.setRoot("student");
    }
}
