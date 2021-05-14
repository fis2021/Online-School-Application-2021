package org.fis.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.fis.project.Main;

public class StudentInformationViewController {

    private static String student;

    @FXML
    private Label helloMessage;

    @FXML
    private Label subject;

    @FXML
    private Label finalgrade;

    @FXML
    private Label presences;

    @FXML
    private Label absences;

    public void setHelloMessage() {
        helloMessage.setText(student);
    }

    public static void setUsername(String username) {
        StudentInformationViewController.student = username;
    }
    @FXML
    public void switchToLogIn() throws Exception {
        Main.setRoot("login");
    }

    @FXML
    public void switchToSubjects() throws Exception {
        Main.setRoot("student");
        StudentController controller=Main.getPath().getController();
        controller.setHelloMessage("Welcome "+student);
        controller.populateDataFromLogInStudent(student);
    }

    public void populateDataToStudentInformationView(String subjects,String grade,String presence,String absence){
        subject.setText(subjects);
        finalgrade.setText(grade);
        presences.setText(presence);
        absences.setText(absence);
    }
}
