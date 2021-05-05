package org.fis.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.fis.project.Main;
import org.fis.project.exceptions.CompleteAllFieldsException;
import org.fis.project.exceptions.UsernameAlreadyExistsException;
import org.fis.project.services.UserService;

import java.io.IOException;

public class RegistrationController extends Exception{

    @FXML
    private Text registrationMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private ChoiceBox role;

    @FXML
    public void initialize() {
        role.getItems().addAll("Teacher", "Student");
    }

    @FXML
    public void handleRegisterAction() {
        try {
            UserService.addUser(usernameField.getText(), passwordField.getText(), (String) role.getValue());
            registrationMessage.setText("Account created successfully!");
        } catch (UsernameAlreadyExistsException e) {
            registrationMessage.setText(e.getMessage());
        } catch (CompleteAllFieldsException e){
            registrationMessage.setText(e.getMessage());
        }
    }

    @FXML
    public void switchToLogin() throws Exception {
        Main.setRoot("login");
    }

}
