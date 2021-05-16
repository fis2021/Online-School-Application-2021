package org.fis.project.controllers;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.fis.project.Main;
import org.fis.project.exceptions.*;
import org.fis.project.model.Catalog;
import org.fis.project.model.User;
import org.fis.project.services.CatalogService;
import org.fis.project.services.FileSystemService;
import org.fis.project.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxRobotInterface;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class StudentControllerTest {
    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER=".test-database";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();

    }

    @BeforeEach
    void setUp2() throws Exception {
        FileSystemService.APPLICATION_FOLDER=".test-catalog";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
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

        primaryStage.setFullScreen(false);
        primaryStage.centerOnScreen();
        primaryStage.setMaximized(false);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Test
    void studentFunctionalities(FxRobot robot) throws AddSubjectNotTyped, SubjectAlreadyAdded, StudentNotSavedInDB, AddStudentNotTyped, StudentAlreadyAdded, CompleteAllFieldsException, ConfirmPasswordException, PasswordNotLongEnough, UserNameNotLongEnough, UsernameAlreadyExistsException, GradeNotAccepted, AddGradeEmpty, AddPresenceEmpty, PresenceNotAccepted, AddAbsenceEmpty, AbsenceNotAccepted {

        UserService.addUser("teacher", "password", "password", "Teacher", "teacher", "teacher");
        UserService.addUser("student", "password", "password", "Student", "student", "student");

        CatalogService.addTeacher_Subject("teacher", "subject");
        CatalogService.addTeacher_Student_Subject("teacher", "student", "subject");
        CatalogService.addGrade("teacher", "student", "subject", "10");
        CatalogService.addPresence("teacher", "student", "subject", "10");
        CatalogService.addAbsence("teacher", "student", "subject", "10");
        CatalogService.addCourseMaterials("teacher", "subject","Material curs1");
        CatalogService.addHomework("teacher", "subject", "Azi nu avem tema.");

        robot.clickOn("#username");
        robot.write("student");
        robot.clickOn("#password");
        robot.write("password");
        robot.clickOn("#loginButton");


        robot.clickOn("#seegrades");
        Assertions.assertThat(robot.lookup("#msg").queryAs(Label.class)).hasText(String.format("Please select subject first!"));
        robot.clickOn("#seematerials");
        Assertions.assertThat(robot.lookup("#msg").queryAs(Label.class)).hasText(String.format("Please select subject first!"));
        robot.clickOn("#loadhomework");
        Assertions.assertThat(robot.lookup("#msg").queryAs(Label.class)).hasText(String.format("Please select subject first!"));

        robot.clickOn((Node) robot.lookup("#tablec1").nth(1).query());
        robot.clickOn("#seegrades");
        robot.clickOn("#back");

        robot.clickOn((Node) robot.lookup("#tablec1").nth(1).query());
        robot.clickOn("#seematerials");

        robot.clickOn((Node) robot.lookup("#tablec1").nth(1).query());
        robot.clickOn("#loadhomework");
        CatalogService.addHomeworkSolution("teacher", "student", "subject", "Vine vacanta!");
        robot.clickOn("#submithomework");

        robot.clickOn("#logout");



    }
}