package org.fis.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class StudentCheckMaterialsController {
    @FXML
    private TextArea courseMaterials;

    private String teacherUsername;
    private String subjectName;
    public void populateDataFromStudent(String teacherUsername , String subjectName,String materials) {
        this.teacherUsername = teacherUsername;
        this.subjectName = subjectName;
        courseMaterials.setText(materials);
    }
}
