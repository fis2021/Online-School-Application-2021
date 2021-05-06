package org.fis.project.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.fis.project.Main;
import org.fis.project.exceptions.*;
import org.fis.project.services.UserService;

import java.io.IOException;

public class RegistrationController extends Exception {

    @FXML
    private Label registrationMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmpasswordField;
    @FXML
    private TextField usernameField;
    @FXML
    private ChoiceBox role;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;

    @FXML
    public void initialize()
    {
        role.getItems().addAll("Teacher", "Student");
    }

    @FXML
    public void handleRegisterAction() {
        try {
            UserService.addUser(usernameField.getText(), passwordField.getText(), confirmpasswordField.getText(), (String) role.getValue(), firstnameField.getText(), lastnameField.getText());
            //System.out.println("Account created successfully");
            registrationMessage.setTextFill(Color.web("#008000", 0.8));

            registrationMessage.setText("Account created successfully!");
        }
        catch (CompleteAllFieldsException e) {
            //System.out.println("Complete all fields");
            registrationMessage.setTextFill(Color.web("#ef0808", 0.8));
            registrationMessage.setText("Please complete all fields");
        }
        catch (UsernameAlreadyExistsException e) {
            //System.out.println("Username already exists!");
            registrationMessage.setTextFill(Color.web("#ef0808", 0.8));
            registrationMessage.setText("Username already exists!");
            registrationMessage.setTextAlignment(TextAlignment.LEFT);
            registrationMessage.setWrapText(true);
        }
        catch (ConfirmPasswordException e) {
            //System.out.println("Password error!" + passwordField.getText() + " and " + confirmpasswordField.getText());
            registrationMessage.setTextFill(Color.web("#ef0808", 0.8));
            registrationMessage.setText("Please enter same password twice!");
        }
        catch (UserNameNotLongEnough e) {
            //System.out.println("Password error!" + passwordField.getText() + " and " + confirmpasswordField.getText());
            registrationMessage.setTextFill(Color.web("#ef0808", 0.8));
            registrationMessage.setText("Username must contain at least 6 characters!");
        }
        catch (PasswordNotLongEnough e) {
            //System.out.println("Password error!" + passwordField.getText() + " and " + confirmpasswordField.getText());
            registrationMessage.setTextFill(Color.web("#ef0808", 0.8));
            registrationMessage.setText("Password must contain at least 6 characters!");
        }
    }

    @FXML
    public void switchToLogin() throws Exception {
        Main.setRoot("login");
    }

}

