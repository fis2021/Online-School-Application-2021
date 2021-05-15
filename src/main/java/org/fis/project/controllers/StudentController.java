package org.fis.project.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    private Label warningMessage;
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

    @FXML
    public void switchToInformationView() throws Exception {
        try {
            ObservableList<StudentTable> subject;
            ObservableList<StudentTable> teacher;
            subject = tableView.getSelectionModel().getSelectedItems();
            teacher = tableView.getSelectionModel().getSelectedItems();

            if(teacher.get(0).getStudentTeachers()!=null) {
                Main.setRoot("studentInformationView");
                StudentInformationViewController controller = Main.getPath().getController();


                String grade = CatalogService.searchGrade(teacher.get(0).getStudentTeachers(), studentUsername, subject.get(0).getStudentSubjects());
                String presence = CatalogService.searchPresence(teacher.get(0).getStudentTeachers(), studentUsername, subject.get(0).getStudentSubjects());
                String absence = CatalogService.searchAbsence(teacher.get(0).getStudentTeachers(), studentUsername, subject.get(0).getStudentSubjects());
                controller.setHelloMessage();
                controller.populateDataToStudentInformationView(subject.get(0).getStudentSubjects(), grade, presence, absence);
            }
        } catch (Exception e) {
            warningMessage.setText("Please select subject first!");
        }
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

    private static FXMLLoader path;
    public void addSolution() {
        try {
            Stage stage3 = new Stage();
            stage3.setTitle("Homework add");

            Scene scene3 = new Scene(loadFXML("teacher4"), 600, 400);

            stage3.getIcons().add(new Image(this.getClass().getResourceAsStream("/9.png")));

            stage3.setScene(scene3);
            stage3.setResizable(false);
            stage3.setMaximized(false);
            stage3.centerOnScreen();
            stage3.initModality(Modality.WINDOW_MODAL);

            TeacherAddHomeworkController controller=path.getController();
            ObservableList<StudentTable>subject;
            ObservableList<StudentTable>teacher;
            subject=tableView.getSelectionModel().getSelectedItems();
            teacher=tableView.getSelectionModel().getSelectedItems();
            String requirements=CatalogService.searchHomeworkRequirements(teacher.get(0).getStudentTeachers(),subject.get(0).getStudentSubjects());
            String solution=CatalogService.searchHomeworkSolution(teacher.get(0).getStudentTeachers(),studentUsername,subject.get(0).getStudentSubjects());
            controller.populateDataFromStudent(teacher.get(0).getStudentTeachers(),studentUsername,subject.get(0).getStudentSubjects(),requirements,solution);
            stage3.show();
        }
        catch (Exception e) {
            warningMessage.setText("Please select subject first!");
        }
    }
    private static Parent loadFXML(String fxml) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getClassLoader().getResource(fxml + ".fxml"));
        path=fxmlLoader;
        return fxmlLoader.load();
    }

    public void handleCourseMaterial(){

        try {
            Stage stage2 = new Stage();
            stage2.setTitle("Course Material");

            Scene scene2 = new Scene(loadFXML("studentCourseMaterial"), 600, 400);

            stage2.getIcons().add(new Image(this.getClass().getResourceAsStream("/9.png")));

            stage2.setScene(scene2);
            stage2.setResizable(false);
            stage2.setMaximized(false);
            stage2.centerOnScreen();
            stage2.initModality(Modality.WINDOW_MODAL);

            StudentCheckMaterialsController controller=path.getController();
            ObservableList<StudentTable>subject;
            ObservableList<StudentTable>teacher;
            subject=tableView.getSelectionModel().getSelectedItems();
            teacher=tableView.getSelectionModel().getSelectedItems();
            String materials=CatalogService.searchCourseMaterials(teacher.get(0).getStudentTeachers(),subject.get(0).getStudentSubjects());
            controller.populateDataFromStudent(teacher.get(0).getStudentTeachers(),subject.get(0).getStudentSubjects(),materials);
            stage2.show();
        }
        catch (Exception e) {
            warningMessage.setText("Please select subject first!");

        }
    }
}
