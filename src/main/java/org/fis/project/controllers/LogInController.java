package org.fis.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.fis.project.Main;
import org.fis.project.exceptions.CompleteAllFieldsException;
import org.fis.project.exceptions.UsernameAlreadyExistsException;
import org.fis.project.services.UserService;


public class LogInController {
    @FXML
    private Label loginMessage;
    @FXML
    private TextField usernameFieldLogin;
    @FXML
    private PasswordField passwordFieldLogin;

    @FXML
    public void switchToRegister() throws Exception {
        Main.setRoot("register");
    }

    @FXML
    public void switchToLogIn() throws Exception {
        Main.setRoot("login");
    }


    @FXML
    public void handleLogIn() throws Exception{
        if(UserService.checkCkredentials(usernameFieldLogin.getText() , passwordFieldLogin.getText()).equals("Teacher"))
            Main.setRoot("teacher");
        else if(UserService.checkCkredentials(usernameFieldLogin.getText() , passwordFieldLogin.getText()).equals("Student"))
            Main.setRoot("student");
    }

}