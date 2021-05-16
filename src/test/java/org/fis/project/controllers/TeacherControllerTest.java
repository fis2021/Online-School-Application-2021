package org.fis.project.controllers;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.fis.project.Main;
import org.fis.project.exceptions.*;
import org.fis.project.services.CatalogService;
import org.fis.project.services.FileSystemService;
import org.fis.project.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.function.Predicate;

import static java.util.Locale.lookup;


@ExtendWith(ApplicationExtension.class)
class TeacherControllerTest {

    public static final String ADMIN_123 = "admin123";

    @BeforeEach
    void setUp() throws Exception{
        FileSystemService.APPLICATION_FOLDER=".test-database";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
        CatalogService.initDatabase();
        UserService.addUser(ADMIN_123, ADMIN_123, ADMIN_123,"Teacher", ADMIN_123, ADMIN_123);
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
    @DisplayName("Testing teacher main dashboard interface for all cases")
    void teacherFuncionalities(FxRobot robot) throws ConfirmPasswordException, UsernameAlreadyExistsException, CompleteAllFieldsException, PasswordNotLongEnough, UserNameNotLongEnough, SubjectAlreadyAdded, AddSubjectNotTyped {
        CatalogService.addTeacher_Subject(ADMIN_123,"Geografie");
        robot.clickOn("#username");
        robot.write(ADMIN_123);
        robot.clickOn("#password");
        robot.write(ADMIN_123);
        robot.clickOn("#loginButton");
        robot.clickOn("#addButton");
        Assertions.assertThat(robot.lookup("#teacherMessage").queryAs(Label.class)).hasText(String.format("Please type name of subject you want to add!"));
        robot.clickOn("#addSubject");
        robot.write("matematica");
        robot.clickOn("#addButton");
        Assertions.assertThat(robot.lookup("#teacherMessage").queryAs(Label.class)).hasText(String.format("Subject matematica added to dashboard!"));
        robot.clickOn("#addButton");
        Assertions.assertThat(robot.lookup("#teacherMessage").queryAs(Label.class)).hasText(String.format("Subject already added!"));
        robot.clickOn("#logOut");
        robot.clickOn("#username");
        robot.write(ADMIN_123);
        robot.clickOn("#password");
        robot.write(ADMIN_123);
        robot.clickOn("#loginButton");
        robot.clickOn("#deleteButton");
        Assertions.assertThat(robot.lookup("#teacherMessage").queryAs(Label.class)).hasText(String.format("There is no subject selected"));
        robot.clickOn("#informationButton");
        Assertions.assertThat(robot.lookup("#teacherMessage").queryAs(Label.class)).hasText(String.format("There is no subject selected"));
        robot.clickOn((Node) robot.lookup("#subjectsTableCol").nth(1).query());
        robot.clickOn("#deleteButton");
        Assertions.assertThat(robot.lookup("#teacherMessage").queryAs(Label.class)).hasText(String.format("Teacher Main Dashboard"));
        robot.clickOn((Node) robot.lookup("#subjectsTableCol").nth(1).query());
        robot.clickOn("#informationButton");
    }

}