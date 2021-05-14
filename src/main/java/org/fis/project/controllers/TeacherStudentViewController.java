package org.fis.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.fis.project.Main;
import org.fis.project.model.SubjectInformation;
import org.fis.project.services.CatalogService;

public class TeacherStudentViewController {

    @FXML
    private Label helloMessage;
    @FXML
    private TextField agrade;
    @FXML
    private TextField aabsence;
    @FXML
    private TextField apresence;
    @FXML
    private Label finalgrade;
    @FXML
    private Label absences;
    @FXML
    private Label presences;

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

    public void handleAddGrade() {
        CatalogService.addGrade(teacherUsername, studentUsername ,subjectName, agrade.getText());
        finalgrade.setText(agrade.getText());
    }

    public void handleAddAbsence() {
        CatalogService.addAbsence(teacherUsername, studentUsername ,subjectName, aabsence.getText());
        absences.setText(aabsence.getText());
    }

    public void handleAddPresence() {
        CatalogService.addPresence(teacherUsername, studentUsername ,subjectName, apresence.getText());
        presences.setText(apresence.getText());
    }

    @FXML
    public void setGrade(String grade) {
        finalgrade.setText(grade);
    }

    @FXML
    public void setAbsence(String absence) {
        absences.setText(absence);
    }

    @FXML
    public void setPresence(String presence) {
        presences.setText(presence);
    }

}
