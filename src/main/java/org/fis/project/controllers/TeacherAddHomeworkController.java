package org.fis.project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.fis.project.services.CatalogService;

public class TeacherAddHomeworkController {

    @FXML
    private TextArea homeworkRequirements;
    @FXML
    private TextArea homeworkSolution;
    @FXML
    private Button addHmButton;
    @FXML
    private Button submitButton;


    public void handleHmRequirements(){
        CatalogService.addHomework(teacherUsername,subjectName,homeworkRequirements.getText());
    }

    private String teacherUsername;
    private String subjectName;
    private String studentUsername;
    public void populateDataFromTeacher(String teacherUsername , String subjectName,String requirements,String solution) {
        this.teacherUsername = teacherUsername;
        this.subjectName = subjectName;
        homeworkRequirements.setText(requirements);
        homeworkRequirements.setEditable(true);
        homeworkSolution.setText(solution);
        homeworkSolution.setEditable(false);
        addHmButton.setDisable(false);
        submitButton.setDisable(true);
    }

    public void handleHmSolution(){
        CatalogService.addHomeworkSolution(teacherUsername,studentUsername,subjectName,homeworkSolution.getText());
    }
    public void populateDataFromStudent(String teacherUsername,String studentUsername,String subjectName,String requirements,String solution){
        this.teacherUsername=teacherUsername;
        this.studentUsername=studentUsername;
        this.subjectName=subjectName;
        homeworkRequirements.setText(requirements);
        homeworkRequirements.setEditable(false);
        homeworkSolution.setText(solution);
        homeworkSolution.setEditable(true);
        addHmButton.setDisable(true);
        submitButton.setDisable(false);
    }
}
