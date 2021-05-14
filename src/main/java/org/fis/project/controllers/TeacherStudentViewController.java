package org.fis.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.fis.project.Main;

public class TeacherStudentViewController {

    @FXML
    private Label helloMessage;
    @FXML
    public void setHelloMessage(String message) {
        helloMessage.setText(message);
    }

    @FXML
    public void switchToLogIn() throws Exception {
        Main.setRoot("login");
    }

    @FXML
    public void switchToSubjectView() throws Exception {

        Main.setRoot("teacher2");
        TeacherSubjectsController controller=Main.getPath().getController();
        controller.setHelloMessage(subjectName);
        controller.populateDataFromDashboard(teacherUsername,subjectName);
    }

    private String teacherUsername;
    private String studentUsername;
    private String subjectName;
    public void populateDataFromTeacherSubjects(String teacherUsername,String studentUsername,String subjectName){
        this.teacherUsername=teacherUsername;
        this.studentUsername=studentUsername;
        this.subjectName=subjectName;
    }
}
