package org.fis.project.controllers;

import javafx.fxml.FXML;
import org.fis.project.Main;

public class TeacherStudentViewController {

    @FXML
    public void switchToLogIn() throws Exception {
        Main.setRoot("login");
    }

    @FXML
    public void switchToSubjectView() throws Exception {

        Main.setRoot("teacher2");

    }
}
