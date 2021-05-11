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
import javafx.scene.control.cell.PropertyValueFactory;
import org.fis.project.Main;
import org.fis.project.model.TeacherSubjects;
import org.fis.project.services.CatalogService;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherController extends Exception {

    @FXML
    private Label helloMessage;
    @FXML
    private TableView<TeacherSubjects> tableView;
    @FXML
    private TableColumn<TeacherSubjects,String> colSubject;


    @FXML
    public void setHelloMessage(String username) {
        helloMessage.setText(username);
    }

    @FXML
    public void switchToLogIn() throws Exception {
        Main.setRoot("login");
    }


    public void populateDataFromLogIn(){
        ObservableList<TeacherSubjects> observableList= FXCollections.observableArrayList(
                new TeacherSubjects("Fis"),
                new TeacherSubjects("Oc"),
                new TeacherSubjects("Bd")
        );
        colSubject.setCellValueFactory(new PropertyValueFactory<>("SubjectName"));
        tableView.setItems(observableList);
    }
    public void populateData(){}
}
