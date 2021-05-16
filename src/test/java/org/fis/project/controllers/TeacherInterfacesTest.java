package org.fis.project.controllers;

import javafx.fxml.FXML;
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
class TeacherInterfacesTest {

    public static final String STUDENT_123 = "student123";
    public static final String ADMIN_123 = "admin123";
    public static final String MATEMATICA = "matematica";

    @BeforeEach
    void setUp() throws Exception{
        FileSystemService.APPLICATION_FOLDER=".test-database";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
        CatalogService.initDatabase();
        UserService.addUser(ADMIN_123, ADMIN_123, ADMIN_123,"Teacher", ADMIN_123, ADMIN_123);
        UserService.addUser(STUDENT_123, ADMIN_123, ADMIN_123,"Student", ADMIN_123, ADMIN_123);
        UserService.addUser("student12", ADMIN_123, ADMIN_123,"Student", ADMIN_123, ADMIN_123);
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
    void teacherFuncionalities(FxRobot robot) throws ConfirmPasswordException, UsernameAlreadyExistsException, CompleteAllFieldsException, PasswordNotLongEnough, UserNameNotLongEnough, SubjectAlreadyAdded, AddSubjectNotTyped, StudentNotSavedInDB, StudentAlreadyAdded, AddStudentNotTyped {
        CatalogService.addTeacher_Subject(ADMIN_123,"Geografie");
        robot.clickOn("#username");
        robot.write(ADMIN_123);
        robot.clickOn("#password");
        robot.write(ADMIN_123);
        robot.clickOn("#loginButton");
        robot.clickOn("#addButton");
        Assertions.assertThat(robot.lookup("#teacherMessage").queryAs(Label.class)).hasText(String.format("Please type name of subject you want to add!"));
        robot.clickOn("#addSubject");
        robot.write(MATEMATICA);
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


        CatalogService.addTeacher_Student_Subject(ADMIN_123,STUDENT_123,MATEMATICA);
        robot.clickOn("#informationButton");

        //teacher interface for a specific class of students

        robot.clickOn("#addHomeworkButton");
        Assertions.assertThat(robot.lookup("#teacher2Exceptions").queryAs(Label.class)).hasText(String.format("Please select a table field and try"));

        robot.clickOn("#deleteStudentButton");
        Assertions.assertThat(robot.lookup("#teacher2Exceptions").queryAs(Label.class)).hasText(String.format("There is no student selected"));

        robot.clickOn("#addStudentButton");
        Assertions.assertThat(robot.lookup("#teacher2Exceptions").queryAs(Label.class)).hasText(String.format("Please type username of student you want to add!"));

        robot.clickOn("#evaluateStudentButton");
        Assertions.assertThat(robot.lookup("#teacher2Exceptions").queryAs(Label.class)).hasText(String.format("There is no student selected"));

        robot.clickOn("#addStudent");
        robot.write("student12");
        robot.clickOn("#addStudentButton");

        robot.clickOn("#addStudentButton");
        Assertions.assertThat(robot.lookup("#teacher2Exceptions").queryAs(Label.class)).hasText(String.format("Student student12 already added to class!"));

        robot.clickOn("#addStudent");
        robot.write("34");
        robot.clickOn("#addStudentButton");
        Assertions.assertThat(robot.lookup("#teacher2Exceptions").queryAs(Label.class)).hasText(String.format("Student student1234 has no account yet!"));

        robot.clickOn("#backToTeacherButton");
        robot.clickOn((Node) robot.lookup("#subjectsTableCol").nth(1).query());
        robot.clickOn("#informationButton");


        robot.clickOn((Node) robot.lookup("#studentsCol").nth(2).query());
        robot.clickOn("#deleteStudentButton");

        robot.clickOn((Node) robot.lookup("#studentsCol").nth(1).query());
        robot.clickOn("#evaluateStudentButton");
    }

    @Test
    @DisplayName("Adding course materials")
    void courseMaterials(FxRobot robot) throws SubjectAlreadyAdded, AddSubjectNotTyped, StudentNotSavedInDB, StudentAlreadyAdded, AddStudentNotTyped {

        CatalogService.addTeacher_Subject(ADMIN_123,MATEMATICA);
        robot.clickOn("#username");
        robot.write(ADMIN_123);
        robot.clickOn("#password");
        robot.write(ADMIN_123);
        robot.clickOn("#loginButton");

        robot.clickOn((Node) robot.lookup("#subjectsTableCol").nth(1).query());
        CatalogService.addTeacher_Student_Subject(ADMIN_123,STUDENT_123,MATEMATICA);
        robot.clickOn("#informationButton");


        robot.clickOn((Node) robot.lookup("#studentsCol").nth(1).query());
        robot.clickOn("#addCourseButton");
        robot.clickOn("#writeCourseMaterial");
        robot.write("Acesta este cursul de matematica");
        robot.clickOn("#teacherCourseMaterialButton");
    }

    @Test
    @DisplayName("Adding homework requirements")
    void homeworkRequirements(FxRobot robot) throws SubjectAlreadyAdded, AddSubjectNotTyped, StudentNotSavedInDB, StudentAlreadyAdded, AddStudentNotTyped {

        CatalogService.addTeacher_Subject(ADMIN_123,MATEMATICA);
        robot.clickOn("#username");
        robot.write(ADMIN_123);
        robot.clickOn("#password");
        robot.write(ADMIN_123);
        robot.clickOn("#loginButton");

        robot.clickOn((Node) robot.lookup("#subjectsTableCol").nth(1).query());
        CatalogService.addTeacher_Student_Subject(ADMIN_123,STUDENT_123,MATEMATICA);
        robot.clickOn("#informationButton");


        robot.clickOn((Node) robot.lookup("#studentsCol").nth(1).query());
        robot.clickOn("#addHomeworkButton");
        robot.clickOn("#homeworkRequirements");
        robot.write("Tema pentru student123 ");
        robot.clickOn("#addTeacherHomeworkButton");
    }

    @Test
    @DisplayName("Evaluate Student")
    void evaluateStudent(FxRobot robot) throws SubjectAlreadyAdded, AddSubjectNotTyped, StudentNotSavedInDB, StudentAlreadyAdded, AddStudentNotTyped {

        CatalogService.addTeacher_Subject(ADMIN_123,MATEMATICA);
        robot.clickOn("#username");
        robot.write(ADMIN_123);
        robot.clickOn("#password");
        robot.write(ADMIN_123);
        robot.clickOn("#loginButton");

        robot.clickOn((Node) robot.lookup("#subjectsTableCol").nth(1).query());
        CatalogService.addTeacher_Student_Subject(ADMIN_123,STUDENT_123,MATEMATICA);
        robot.clickOn("#informationButton");

        robot.clickOn((Node) robot.lookup("#studentsCol").nth(1).query());
        robot.clickOn("#evaluateStudentButton");


        robot.clickOn("#giveGradeButton");
        Assertions.assertThat(robot.lookup("#evaluateMistakeMessage").queryAs(Label.class)).hasText("Please type grade to add!");

        robot.clickOn("#givePresenceButton");
        Assertions.assertThat(robot.lookup("#evaluateMistakeMessage").queryAs(Label.class)).hasText("Please type presence to add!");

        robot.clickOn("#giveAbsenceButton");
        Assertions.assertThat(robot.lookup("#evaluateMistakeMessage").queryAs(Label.class)).hasText("Please type absence to add!");

        robot.clickOn("#giveGrade");
        robot.write("10");
        robot.clickOn("#giveGradeButton");

        robot.clickOn("#givePresence");
        robot.write("10");
        robot.clickOn("#givePresenceButton");

        robot.clickOn("#giveAbsence");
        robot.write("10");
        robot.clickOn("#giveAbsenceButton");

    }

}