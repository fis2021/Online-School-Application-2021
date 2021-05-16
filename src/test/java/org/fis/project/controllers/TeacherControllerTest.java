package org.fis.project.controllers;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.fis.project.Main;
import org.fis.project.services.CatalogService;
import org.fis.project.services.FileSystemService;
import org.fis.project.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class TeacherControllerTest {

    public static final String ADMIN_123 = "admin123";
    public static final String STUDENT_123 = "student123";

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
        Main.scene=new Scene(Main.loadFXML("teacher"), 300, 275);
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
    void addSubject(FxRobot robot) throws Exception{
        robot.clickOn("#addSubject");
        robot.write("matematica");
        robot.clickOn("#addButton");
    }

    @Test
    void logOut(FxRobot robot){
        robot.clickOn("#logOut");
    }

    @Test
    void deleteSubject(FxRobot robot){
        robot.clickOn("#deleteButton");
    }

    @Test
    void SubjectInformation(FxRobot robot) throws Exception{
        CatalogService.addTeacher_Subject(ADMIN_123,"matematica");
        robot.clickOn("#informationButton");
    }

}