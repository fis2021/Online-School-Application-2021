package org.fis.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.fis.project.Main;
import org.fis.project.exceptions.*;
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
    private Label warningMessage;

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
        try {
            CatalogService.addGrade(teacherUsername, studentUsername, subjectName, agrade.getText());
            finalgrade.setText(agrade.getText());
            warningMessage.setText(studentUsername + " evaluted with grade " + agrade.getText() +" at subject " + subjectName + "!");
        } catch (AddGradeEmpty e) {
            warningMessage.setText("Please type grade to add!");
        } catch (GradeNotAccepted e) {
            warningMessage.setText("Grade must be grater than 1 and larger than 10!");
        }
    }

    public void handleAddAbsence() {
        try {
        //Integer.parseInt(apresence.getText());

        CatalogService.addAbsence(teacherUsername, studentUsername ,subjectName, aabsence.getText());
        absences.setText(aabsence.getText());

        warningMessage.setText("Absence added successfully!");
        } catch (AddAbsenceEmpty e) {
            warningMessage.setText("Please type absence to add!");
        } catch (AbsenceNotAccepted e) {
            warningMessage.setText("Number of absences must be positive integer!");
        } catch (Exception e) {
            warningMessage.setText("Number of absences must be positive integer!");
        }

    }

    public void handleAddPresence() {
        try {
            //Integer.parseInt(apresence.getText());

            CatalogService.addPresence(teacherUsername, studentUsername, subjectName, apresence.getText());
            presences.setText(apresence.getText());

            warningMessage.setText("Presence added successsfully!");
        } catch (AddPresenceEmpty e) {
            warningMessage.setText("Please type presence to add!");
        } catch (PresenceNotAccepted e) {
            warningMessage.setText("Number of presences must be positive integer!");
        } catch (Exception e) {
            warningMessage.setText("Number of presences must be positive integer!");
        }
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
