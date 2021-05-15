package org.fis.project.controllers;

import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.fis.project.Main;

import org.fis.project.exceptions.AddSubjectNotTyped;
import org.fis.project.exceptions.SubjectAlreadyAdded;
import org.fis.project.model.Catalog;
import org.fis.project.model.TeacherSubjects;
import org.fis.project.model.User;
import org.fis.project.services.CatalogService;

import java.util.LinkedList;


public class TeacherController extends Exception {

    @FXML
    private Label exceptionsMessage;
    @FXML
    private Label helloMessage;
    @FXML
    private TableView<TeacherSubjects> tableView;
    @FXML
    private TableColumn<TeacherSubjects,String> colSubject;
    @FXML
    private TextField addSubject;


    private String teacherUsername;

    @FXML
    public void setHelloMessage(String username) {
        helloMessage.setText(username);
    }

    @FXML
    public void switchToLogIn() throws Exception {
        Main.setRoot("login");
    }

    @FXML
    public void switchToSubjects() throws Exception {
        try {
            ObservableList<TeacherSubjects> subject;
            subject = tableView.getSelectionModel().getSelectedItems();

            if(subject.get(0).getSubjectName()!=null){
                Main.setRoot("teacher2");
                TeacherSubjectsController controller = Main.getPath().getController();
                controller.setHelloMessage(subject.get(0).getSubjectName());
                controller.populateDataFromDashboard(teacherUsername, subject.get(0).getSubjectName());
                exceptionsMessage.setText("Teacher Main Dashboard");
            }
        }catch (Exception e){
            exceptionsMessage.setText("There is no subject selected");
        }
    }

    public void populateDataFromLogIn(String username){
        teacherUsername=username;
        colSubject.setCellValueFactory(new PropertyValueFactory<>("SubjectName"));

        LinkedList<String> teacherSubject=CatalogService.teacherSubjects(teacherUsername);

        for(String subject:teacherSubject){
            tableView.getItems().add(new TeacherSubjects(subject));
        }
    }

    public void handleAddingSubject(){

        try {

            CatalogService.addTeacher_Subject(teacherUsername, addSubject.getText());
            tableView.getItems().add(new TeacherSubjects(addSubject.getText()));

            exceptionsMessage.setText("Subject " + addSubject.getText() + " added to dashboard!");
        } catch (AddSubjectNotTyped e) {

            exceptionsMessage.setText("Please type name of subject you want to add!");
        } catch (SubjectAlreadyAdded e) {

            exceptionsMessage.setText("Subject already added!");
        }

    }

    public void handleRemovingSubject(){
        try {
            ObservableList<TeacherSubjects> allSubjects, singleSubjects;
            singleSubjects = tableView.getSelectionModel().getSelectedItems();
            CatalogService.clearSubject(teacherUsername, singleSubjects.get(0).getSubjectName());

            allSubjects = tableView.getItems();
            singleSubjects.forEach(allSubjects::remove);
            exceptionsMessage.setText("Teacher Main Dashboard");
        }catch (Exception e){
            exceptionsMessage.setText("There is no subject selected");
        }
    }


}
