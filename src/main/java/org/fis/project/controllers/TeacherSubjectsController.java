package org.fis.project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.fis.project.Main;
import org.fis.project.model.SubjectInformation;
import org.fis.project.model.TeacherSubjects;
import org.fis.project.services.CatalogService;

import java.util.LinkedList;

public class TeacherSubjectsController {

    @FXML
    private TextField addStudent;
    @FXML
    private Label helloMessage;
    @FXML
    private TableView<SubjectInformation> tableView;
    @FXML
    private TableColumn<SubjectInformation,String> students;

    @FXML
    public void setHelloMessage(String message) {
        helloMessage.setText(message);
    }

    @FXML
    public void switchToLogIn() throws Exception {
        Main.setRoot("login");
    }

    private static String username;
    public static void setUsername(String username) {
        TeacherSubjectsController.username = username;
    }

    @FXML
    public void switchToTeacher() throws Exception {

        Main.setRoot("teacher");

        TeacherController controller=Main.getPath().getController();
        controller.setHelloMessage("Welcome "+username);
        controller.populateDataFromLogIn(username);
    }

    public void handleAddStudents() {
        CatalogService.addTeacher_Student_Subject(teacherUsername,addStudent.getText(),subjectName);
        tableView.getItems().add(new SubjectInformation(addStudent.getText()));
    }

    private String teacherUsername;
    private String subjectName;
    public void populateDataFromDashboard(String teacherUsername , String subjectName){

        this.teacherUsername=teacherUsername;
        this.subjectName=subjectName;

        students.setCellValueFactory(new PropertyValueFactory<>("StudentName"));

        LinkedList<String> students=CatalogService.studentsSubject(teacherUsername,subjectName);

        for(String student:students){
            tableView.getItems().add(new SubjectInformation(student));
        }
    }
}
