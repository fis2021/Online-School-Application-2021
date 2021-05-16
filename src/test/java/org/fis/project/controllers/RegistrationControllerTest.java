package org.fis.project.controllers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.fis.project.Main;
import org.fis.project.exceptions.CompleteAllFieldsException;
import org.fis.project.exceptions.ConfirmPasswordException;
import org.fis.project.exceptions.PasswordNotLongEnough;
import org.fis.project.exceptions.UserNameNotLongEnough;
import org.fis.project.services.CatalogService;
import org.fis.project.services.FileSystemService;
import org.fis.project.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.scene.control.Label;


import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class RegistrationControllerTest {

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER=".test-database";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        CatalogService.initDatabase();
        UserService.initDatabase();

    }

    @Start
    void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Online School Application");
        Main.scene=new Scene(Main.loadFXML("register"), 300, 275);
        primaryStage.setScene(Main.scene);

        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/9.png")));

        primaryStage.setWidth(1550);
        primaryStage.setHeight(835);

        primaryStage.setFullScreen(false);
        primaryStage.centerOnScreen();
        primaryStage.setMaximized(false);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Test
    void registrationStudent(FxRobot robot) {
        Assertions.assertThat(robot.lookup("#regmsg").queryAs(Label.class)).hasText(String.format("Please enter registration credentials"));
        robot.clickOn("#reg");
        robot.clickOn("#rolec");
        robot.type(KeyCode.UP);
        robot.type(KeyCode.ENTER);
        //robot.write("Teacher");
        robot.clickOn("#firstname");
        robot.write("Cristiano");
        robot.clickOn("#lastname");
        robot.write("Ronaldo");
        robot.clickOn("#username");
        robot.write("cr7");
        robot.clickOn("#password");
        robot.write("1234");
        robot.clickOn("#confirmpassword");
        robot.write("1234");;
        robot.clickOn("#reg");
        Assertions.assertThat(robot.lookup("#regmsg").queryAs(Label.class)).hasText(String.format("Username must contain at least 6 characters!"));
        robot.clickOn("#username");
        robot.eraseText(3);
        robot.write("cristiano");

        robot.clickOn("#reg");
        Assertions.assertThat(robot.lookup("#regmsg").queryAs(Label.class)).hasText(String.format("Password must contain at least 6 characters!"));
        robot.clickOn("#password");
        robot.eraseText(4);
        robot.write("123456");
        robot.clickOn("#confirmpassword");
        robot.eraseText(4);
        robot.write("123457");
        robot.clickOn("#reg");
        Assertions.assertThat(robot.lookup("#regmsg").queryAs(Label.class)).hasText(String.format("Please enter same password twice!"));
        robot.clickOn("#password");
        robot.eraseText(6);
        robot.write("123456");
        robot.clickOn("#confirmpassword");
        robot.eraseText(6);
        robot.write("123456");
        robot.clickOn("#reg");
        Assertions.assertThat(robot.lookup("#regmsg").queryAs(Label.class)).hasText(String.format("Account created successfully!"));

    }


}