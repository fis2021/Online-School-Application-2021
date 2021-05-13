package org.fis.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;
import org.fis.project.Main;
import org.fis.project.model.StudentTable;
import org.fis.project.model.TeacherSubjects;
import org.fis.project.services.CatalogService;
import org.fis.project.services.UserService;

import java.util.LinkedList;

public class StudentController {
    @FXML
    private Label helloMessage;
    @FXML
    private TableView<StudentTable> tableView;
    @FXML
    private TableColumn<StudentTable,String> subject;
    @FXML
    private TableColumn<StudentTable,String> teacher;

    public void setHelloMessage(String username) {
        helloMessage.setText(username);
    }

    @FXML
    public void switchToLogIn() throws Exception {
        Main.setRoot("login");
    }

    private String studentUsername;
    public void populateDataFromLogInStudent(String username){
        studentUsername=username;

        subject.setCellValueFactory(new PropertyValueFactory<>("StudentSubjects"));
        teacher.setCellValueFactory(new PropertyValueFactory<>("StudentTeachers"));

        LinkedList<Pair<String,String>> subejcts_teachers= CatalogService.studentSubjectsTeachers(studentUsername);

        for(Pair<String,String> subjects:subejcts_teachers){
            tableView.getItems().add(new StudentTable(subjects.getKey(),subjects.getValue()));
        }
    }
}
