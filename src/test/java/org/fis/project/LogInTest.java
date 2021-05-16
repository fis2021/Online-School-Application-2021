package org.fis.project;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.fis.project.exceptions.*;
import org.fis.project.services.CatalogService;
import org.fis.project.services.FileSystemService;
import org.fis.project.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.scene.control.Label;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testfx.assertions.api.Assertions.assertThat;
import static org.testfx.api.FxAssert.verifyThat;

@ExtendWith(ApplicationExtension.class)
class LogInTest {
    public static final String ADMIN_123 = "admin123";
    public static final String STUDENT_123 = "student123";

    @BeforeEach
    void setUp() throws Exception{
        FileSystemService.APPLICATION_FOLDER=".test-database";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
        CatalogService.initDatabase();
    }

    @Start
    void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Online School Application");
        Main.scene=new Scene(Main.loadFXML("login"), 300, 275);
        primaryStage.setScene(Main.scene);

        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/9.png")));

        primaryStage.setWidth(1550);
        primaryStage.setHeight(835);

        primaryStage.centerOnScreen();
        primaryStage.setMaximized(false);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

   @Test
   @DisplayName("Switch to register")
   void switchToRegister(FxRobot robot){
       robot.clickOn("#registerButton");
   }

    @Test
    @DisplayName("As a teacher I want to log in and see my subject")
    void testLogInTeacher(FxRobot robot) throws Exception{
        UserService.addUser(ADMIN_123, ADMIN_123, ADMIN_123,"Teacher", ADMIN_123, ADMIN_123);
        CatalogService.addTeacher_Subject(ADMIN_123,"FIS");
        CatalogService.addTeacher_Subject(ADMIN_123,"OC");
        CatalogService.addTeacher_Subject(ADMIN_123,"BD");
        robot.clickOn("#username");
        robot.write(ADMIN_123);
        robot.clickOn("#password");
        robot.write(ADMIN_123);
        robot.clickOn("#loginButton");
    }

    @Test
    @DisplayName("Please complete all LogIn fields")
    void loginErrors(FxRobot robot) throws ConfirmPasswordException, UsernameAlreadyExistsException, CompleteAllFieldsException, PasswordNotLongEnough, UserNameNotLongEnough {
        UserService.addUser(ADMIN_123, ADMIN_123, ADMIN_123,"Teacher", ADMIN_123, ADMIN_123);
        Assertions.assertThat(robot.lookup("#message").queryAs(Label.class)).hasText("Please enter username and password");
        robot.clickOn("#username");
        robot.write(ADMIN_123);
        robot.clickOn("#loginButton");
        Assertions.assertThat(robot.lookup("#message").queryAs(Label.class)).hasText(String.format("Please complete all log in fields!"));
        robot.clickOn("#password");
        robot.write("profesor");
        robot.clickOn("#loginButton");
        Assertions.assertThat(robot.lookup("#message").queryAs(Label.class)).hasText("Wrong username or password. Please try again!");
    }

    @Test
    @DisplayName("As a student I want to log in and see my subjects and my teachers")
    void testLogInStudent(FxRobot robot) throws Exception{
        UserService.addUser(STUDENT_123, ADMIN_123, ADMIN_123,"Student", ADMIN_123, ADMIN_123);
        UserService.addUser(ADMIN_123, ADMIN_123, ADMIN_123,"Teacher", ADMIN_123, ADMIN_123);
        CatalogService.addTeacher_Student_Subject(ADMIN_123,STUDENT_123,"FIS");
        CatalogService.addTeacher_Student_Subject(ADMIN_123,STUDENT_123,"OC");
        CatalogService.addTeacher_Student_Subject(ADMIN_123,STUDENT_123,"BD");
        robot.clickOn("#username");
        robot.write(STUDENT_123);
        robot.clickOn("#password");
        robot.write(ADMIN_123);
        robot.clickOn("#loginButton");
    }
}