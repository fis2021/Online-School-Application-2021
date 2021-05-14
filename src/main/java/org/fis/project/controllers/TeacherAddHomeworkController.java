package org.fis.project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.fis.project.services.CatalogService;

public class TeacherAddHomeworkController {

    @FXML
    private TextArea homeworkRequirements;
    @FXML
    private TextArea homeworkSolution;

    public void handleHmRequirements(){
        CatalogService.addHomework(teacherUsername,subjectName,homeworkRequirements.getText());
    }

    private String teacherUsername;
    private String subjectName;
    public void populateDataFromTeacher(String teacherUsername , String subjectName,String requirements) {
        this.teacherUsername = teacherUsername;
        this.subjectName = subjectName;
        homeworkRequirements.setText(requirements);
    }
}
