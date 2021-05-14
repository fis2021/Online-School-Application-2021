package org.fis.project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.fis.project.services.CatalogService;

public class TeacherAddMaterialsController {
    @FXML
    private TextArea courseMaterials;

    @FXML
    private void addCourseMaterials() {
        CatalogService.addCourseMaterials(teacherUsername,subjectName,courseMaterials.getText());
    }

    private String teacherUsername;
    private String subjectName;
    public void populateDataFromTeacher(String teacherUsername , String subjectName,String materials) {
        this.teacherUsername = teacherUsername;
        this.subjectName = subjectName;
        courseMaterials.setText(materials);
    }
}
