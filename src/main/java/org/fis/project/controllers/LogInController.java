package org.fis.project.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.fis.project.Main;
import org.fis.project.exceptions.CompleteLoginDataException;
import org.fis.project.services.UserService;

import static java.lang.Thread.sleep;


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
    public void handleLogIn() {

        try {
            if (UserService.checkCkredentials(usernameFieldLogin.getText(), passwordFieldLogin.getText()).equals("Teacher")) {
                loginMessage.setTextFill(Color.web("#008000", 0.8));
                loginMessage.setText("You have logged in successfully!");

                Main.setRoot("teacher");

                TeacherController controller=Main.getPath().getController();
                controller.setHelloMessage("Welcome "+usernameFieldLogin.getText());
                controller.populateDataFromLogIn();

            }
            else if (UserService.checkCkredentials(usernameFieldLogin.getText(), passwordFieldLogin.getText()).equals("Student")) {
                loginMessage.setTextFill(Color.web("#008000", 0.8));
                loginMessage.setText("You have logged in successfully!");

                Main.setRoot("student");

                StudentController controller=Main.getPath().getController();
                controller.setHelloMessage("Welcome "+usernameFieldLogin.getText());
            }
            else {
                loginMessage.setTextFill(Color.web("#ef0c0c", 0.8));
                loginMessage.setText("Wrong username or password. Please try again!");
            }
        }
        catch (CompleteLoginDataException e) {
                loginMessage.setTextFill(Color.web("#ef0c0c", 0.8));
                loginMessage.setText("Please complete all log in fields!");
        }
        catch (Exception e) {

        }

    }

}