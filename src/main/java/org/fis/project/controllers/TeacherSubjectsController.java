package org.fis.project.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.fis.project.Main;
import org.fis.project.exceptions.AddStudentNotTyped;
import org.fis.project.exceptions.StudentAlreadyAdded;
import org.fis.project.exceptions.StudentNotSavedInDB;
import org.fis.project.model.SubjectInformation;
import org.fis.project.model.TeacherSubjects;
import org.fis.project.services.CatalogService;

import java.util.LinkedList;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeacherSubjectsController {

    @FXML
    private Label exceptionMessage;
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

    @FXML
    public void switchToStudentView() throws Exception {
        try {
                ObservableList<SubjectInformation> student;
                student=tableView.getSelectionModel().getSelectedItems();
                if(student.get(0).getStudentName()!=null) {
                    Main.setRoot("teacher5");

                    TeacherStudentViewController controller = Main.getPath().getController();
                    controller.setHelloMessage(student.get(0).getStudentName());
                    controller.populateDataFromTeacherSubjects(teacherUsername, student.get(0).getStudentName(), subjectName);

                    String grade = CatalogService.searchGrade(teacherUsername, student.get(0).getStudentName(), subjectName);
                    controller.setGrade(grade);

                    String absence = CatalogService.searchAbsence(teacherUsername, student.get(0).getStudentName(), subjectName);
                    controller.setAbsence(absence);

                    String presence = CatalogService.searchPresence(teacherUsername, student.get(0).getStudentName(), subjectName);
                    controller.setPresence(presence);
                }
        }catch (Exception e){
            exceptionMessage.setText("There is no student selected");
        }

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

        try {
            CatalogService.addTeacher_Student_Subject(teacherUsername, addStudent.getText(), subjectName);
            tableView.getItems().add(new SubjectInformation(addStudent.getText()));

            exceptionMessage.setText("Student " + addStudent.getText() + " added to class!");
        } catch (AddStudentNotTyped e) {

            exceptionMessage.setText("Please type username of student you want to add!");

        } catch (StudentAlreadyAdded e) {

            exceptionMessage.setText("Student " + addStudent.getText() + " already added to class!");
        } catch (StudentNotSavedInDB e) {
            exceptionMessage.setText("Student " + addStudent.getText() + " has no account yet!");
        }


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

    @FXML
    private Button addcm;

    private static FXMLLoader path;

    @FXML
    public void handleButtonClick (){

        try {
            Stage stage2 = new Stage();
            stage2.setTitle("Subject materials add");

            Scene scene2 = new Scene(loadFXML("teacher3"), 600, 400);

            stage2.getIcons().add(new Image(this.getClass().getResourceAsStream("/9.png")));

            stage2.setScene(scene2);
            stage2.setResizable(false);
            stage2.setMaximized(false);
            stage2.centerOnScreen();
            stage2.initModality(Modality.WINDOW_MODAL);

            TeacherAddMaterialsController controller=path.getController();
            String materials=CatalogService.searchCourseMaterials(teacherUsername,subjectName);
            controller.populateDataFromTeacher(teacherUsername,subjectName,materials);
            exceptionMessage.setText("Teacher Subject Dashboard");
            stage2.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void handleButtonClick2 (){

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
            ObservableList<SubjectInformation> student;
            student=tableView.getSelectionModel().getSelectedItems();
            String requirements=CatalogService.searchHomeworkRequirements(teacherUsername,subjectName);
            String solution=CatalogService.searchHomeworkSolution(teacherUsername,student.get(0).getStudentName(),subjectName);
            controller.populateDataFromTeacher(teacherUsername,subjectName,requirements,solution);
            stage3.show();

        }catch (Exception e){
            exceptionMessage.setText("Please select a table field and try");
        }


    }

    private static Parent loadFXML(String fxml) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getClassLoader().getResource(fxml + ".fxml"));
        path=fxmlLoader;
        return fxmlLoader.load();
    }

    public static FXMLLoader getPath() {
        return path;
    }


    public void handleRemoveStudent() {
        try {
            ObservableList<SubjectInformation> allStudents, singleStudent;
            singleStudent = tableView.getSelectionModel().getSelectedItems();

            CatalogService.clearStudent(teacherUsername, singleStudent.get(0).getStudentName(), subjectName);

            allStudents = tableView.getItems();
            singleStudent.forEach(allStudents::remove);
            exceptionMessage.setText("Teacher Subject Dashboard");
        }catch (Exception e){
            exceptionMessage.setText("There is no student selected");
        }
    }
}
