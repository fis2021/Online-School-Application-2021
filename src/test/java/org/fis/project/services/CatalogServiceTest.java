package org.fis.project.services;

import javafx.util.Pair;
import org.apache.commons.io.FileUtils;
import org.fis.project.exceptions.*;
import org.fis.project.model.Catalog;
import org.fis.project.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CatalogServiceTest {

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER=".test-catalog";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        CatalogService.initDatabase();

    }

    @BeforeEach
    void setUp2() throws Exception{
        FileSystemService.APPLICATION_FOLDER=".test-database";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();

    }

    @AfterEach
    void tearDown() {
        CatalogService.closeDatabase();
    }

    @AfterEach
    void tearDown2() {
        UserService.closeDatabase();
    }

    @Test
    void initializeDatabase(){
        assertThat(CatalogService.getAllUsers()).isNotNull();
        assertThat(CatalogService.getAllUsers()).isEmpty();
    }

    @Test
    void initializeDatabase2(){
        assertThat(UserService.getAllUsers()).isNotNull();
        assertThat(UserService.getAllUsers()).isEmpty();
    }

    @Test
    @DisplayName("Teacher can add subjects he teaches")
    void addSubjectToDataBase () throws AddSubjectNotTyped, SubjectAlreadyAdded {
        CatalogService.addTeacher_Subject("teacher", "subject");
        assertThat(CatalogService.getAllUsers()).isNotEmpty();
        assertThat(CatalogService.getAllUsers()).size().isEqualTo(1);
        Catalog catalog=CatalogService.getAllUsers().get(0);
        assertThat(catalog.getTeacherId()).isEqualTo("teacher");
        assertThat(catalog.getSubjectId()).isEqualTo("subject");
    }

    @Test
    @DisplayName("There can be only one class for one subject for teacher")
    void addSubjectToDataBaseTwice () {
        assertThrows(SubjectAlreadyAdded.class, () -> {
            CatalogService.addTeacher_Subject("teacher", "subject");
            CatalogService.addTeacher_Subject("teacher", "subject");
        });
    }

    @Test
    @DisplayName("Teacher has to enter subject name in order to create class")
    void addSubjectToDataBaseNoSubjectTyped () {
        assertThrows(AddSubjectNotTyped.class, () -> {
            CatalogService.addTeacher_Subject("teacher", "");
        });
    }

    @Test
    @DisplayName("Student added to class by username")
    void addStudentToDataBase () throws AddStudentNotTyped, StudentAlreadyAdded, StudentNotSavedInDB, UsernameAlreadyExistsException, CompleteAllFieldsException, PasswordNotLongEnough, UserNameNotLongEnough, ConfirmPasswordException {

        initializeDatabase2();

        UserService.addUser("student","password", "password", "Student", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user=UserService.getAllUsers().get(0);
        assertThat(user.getUsername()).isEqualTo("student");
        assertThat(user.getRole()).isEqualTo("Student");


        CatalogService.addTeacher_Student_Subject("teacher", "student" ,"subject");
        assertThat(CatalogService.getAllUsers()).isNotEmpty();
        assertThat(CatalogService.getAllUsers()).size().isEqualTo(1);
        Catalog catalog=CatalogService.getAllUsers().get(0);
        assertThat(catalog.getTeacherId()).isEqualTo("teacher");
        assertThat(catalog.getStudentId()).isEqualTo("student");
        assertThat(catalog.getSubjectId()).isEqualTo("subject");


    }

    @Test
    @DisplayName("If student username is not entered by the teacher, exception is thrown")
    void addStudentToDataBaseNoStudentTyped () {
        assertThrows(AddStudentNotTyped.class, () -> {
            CatalogService.addTeacher_Student_Subject("teacher", "", "subject");
        });
    }

    @Test
    @DisplayName("Student already added to class can not be added again")
    void addStudentToDataBaseTwice () throws AddSubjectNotTyped, SubjectAlreadyAdded, CompleteAllFieldsException, ConfirmPasswordException, PasswordNotLongEnough, UserNameNotLongEnough, UsernameAlreadyExistsException {
        CatalogService.addTeacher_Subject("teacher", "subject");
        assertThat(CatalogService.getAllUsers()).isNotEmpty();
        assertThat(CatalogService.getAllUsers()).size().isEqualTo(1);
        Catalog catalog=CatalogService.getAllUsers().get(0);
        assertThat(catalog.getTeacherId()).isEqualTo("teacher");
        assertThat(catalog.getSubjectId()).isEqualTo("subject");

        initializeDatabase2();
        UserService.addUser("teacher", "teacher", "teacher", "Teacher", "teacher", "teacher");


        assertThrows(StudentNotSavedInDB.class, () -> {
            CatalogService.addTeacher_Student_Subject("teacher", "student123", "subject");
            CatalogService.addTeacher_Student_Subject("teacher", "student123", "subject");
        });


    }

    @Test
    @DisplayName("Student that has no account can not be added to class")
    void addStudentToDataBaseStudentHasNoAccount () {
        assertThrows(StudentNotSavedInDB.class, () -> {
            CatalogService.addTeacher_Student_Subject("teacher", "nimeni", "subject");
        });
    }

    @Test
    @DisplayName("Teacher can remove subjects and classes")
    void clearSubjectFromCatalogDataBase() throws AddSubjectNotTyped, SubjectAlreadyAdded {
        CatalogService.addTeacher_Subject("teacher", "subject");
        assertThat(CatalogService.getAllUsers()).isNotEmpty();
        assertThat(CatalogService.getAllUsers()).size().isEqualTo(1);
        Catalog catalog=CatalogService.getAllUsers().get(0);
        assertThat(catalog.getTeacherId()).isEqualTo("teacher");
        assertThat(catalog.getSubjectId()).isEqualTo("subject");
        CatalogService.clearSubject("teacher", "subject");
        assertThat(CatalogService.getAllUsers()).size().isEqualTo(0);
    }


    @Test
    @DisplayName("Teacher can remove students from his classes")
    void clearStudentFromCatalogDataBase() throws CompleteAllFieldsException, ConfirmPasswordException, PasswordNotLongEnough, UserNameNotLongEnough, UsernameAlreadyExistsException, StudentNotSavedInDB, AddStudentNotTyped, StudentAlreadyAdded {
        initializeDatabase2();

        UserService.addUser("student","password", "password", "Student", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user=UserService.getAllUsers().get(0);
        assertThat(user.getUsername()).isEqualTo("student");
        assertThat(user.getRole()).isEqualTo("Student");

        CatalogService.addTeacher_Student_Subject("teacher", "student" ,"subject");
        assertThat(CatalogService.getAllUsers()).isNotEmpty();
        assertThat(CatalogService.getAllUsers()).size().isEqualTo(1);
        Catalog catalog=CatalogService.getAllUsers().get(0);
        assertThat(catalog.getTeacherId()).isEqualTo("teacher");
        assertThat(catalog.getStudentId()).isEqualTo("student");
        assertThat(catalog.getSubjectId()).isEqualTo("subject");

        CatalogService.clearStudent("teacher", "student", "subject");
        assertThat(CatalogService.getAllUsers()).size().isEqualTo(0);
    }

    @Test
    @DisplayName("Teacher can add grade for students in his classes")
    void TeacherAddGrade() throws CompleteAllFieldsException, ConfirmPasswordException, PasswordNotLongEnough, UserNameNotLongEnough, UsernameAlreadyExistsException, StudentNotSavedInDB, AddStudentNotTyped, StudentAlreadyAdded, GradeNotAccepted, AddGradeEmpty {
        initializeDatabase2();

        UserService.addUser("student","password", "password", "Student", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user=UserService.getAllUsers().get(0);
        assertThat(user.getUsername()).isEqualTo("student");
        assertThat(user.getRole()).isEqualTo("Student");

        CatalogService.addTeacher_Student_Subject("teacher", "student" ,"subject");
        assertThat(CatalogService.getAllUsers()).isNotEmpty();
        assertThat(CatalogService.getAllUsers()).size().isEqualTo(1);
        Catalog catalog=CatalogService.getAllUsers().get(0);
        assertThat(catalog.getTeacherId()).isEqualTo("teacher");
        assertThat(catalog.getStudentId()).isEqualTo("student");
        assertThat(catalog.getSubjectId()).isEqualTo("subject");
        CatalogService.addGrade("teacher", "student", "subject", "10");

        assertThat(CatalogService.searchGrade("teacher", "student", "subject").equals("10"));

            }

    @Test
    @DisplayName("Teacher can add presences for students in his classes")
    void TeacherAddPresence() throws CompleteAllFieldsException, ConfirmPasswordException, PasswordNotLongEnough, UserNameNotLongEnough, UsernameAlreadyExistsException, StudentNotSavedInDB, AddStudentNotTyped, StudentAlreadyAdded, GradeNotAccepted, AddGradeEmpty, AddPresenceEmpty, PresenceNotAccepted {
        initializeDatabase2();

        UserService.addUser("student","password", "password", "Student", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user=UserService.getAllUsers().get(0);
        assertThat(user.getUsername()).isEqualTo("student");
        assertThat(user.getRole()).isEqualTo("Student");

        CatalogService.addTeacher_Student_Subject("teacher", "student" ,"subject");
        assertThat(CatalogService.getAllUsers()).isNotEmpty();
        assertThat(CatalogService.getAllUsers()).size().isEqualTo(1);
        Catalog catalog=CatalogService.getAllUsers().get(0);
        assertThat(catalog.getTeacherId()).isEqualTo("teacher");
        assertThat(catalog.getStudentId()).isEqualTo("student");
        assertThat(catalog.getSubjectId()).isEqualTo("subject");
        CatalogService.addPresence("teacher", "student", "subject", "1");

        assertThat(CatalogService.searchPresence("teacher", "student", "subject").equals("1"));

    }

    @Test
    @DisplayName("Teacher can add absences for students in his classes")
    void TeacherAddAbsences() throws CompleteAllFieldsException, ConfirmPasswordException, PasswordNotLongEnough, UserNameNotLongEnough, UsernameAlreadyExistsException, StudentNotSavedInDB, AddStudentNotTyped, StudentAlreadyAdded, GradeNotAccepted, AddGradeEmpty, AddPresenceEmpty, PresenceNotAccepted, AddAbsenceEmpty, AbsenceNotAccepted {
        initializeDatabase2();

        UserService.addUser("student","password", "password", "Student", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user=UserService.getAllUsers().get(0);
        assertThat(user.getUsername()).isEqualTo("student");
        assertThat(user.getRole()).isEqualTo("Student");

        CatalogService.addTeacher_Student_Subject("teacher", "student" ,"subject");
        assertThat(CatalogService.getAllUsers()).isNotEmpty();
        assertThat(CatalogService.getAllUsers()).size().isEqualTo(1);
        Catalog catalog=CatalogService.getAllUsers().get(0);
        assertThat(catalog.getTeacherId()).isEqualTo("teacher");
        assertThat(catalog.getStudentId()).isEqualTo("student");
        assertThat(catalog.getSubjectId()).isEqualTo("subject");
        CatalogService.addAbsence("teacher", "student", "subject", "1");

        assertThat(CatalogService.searchAbsence("teacher", "student", "subject").equals("1"));

    }

    @Test
    @DisplayName("Teacher can add course materials for students in his classes")
    void TeacherAddCourseMaterial() throws CompleteAllFieldsException, ConfirmPasswordException, PasswordNotLongEnough, UserNameNotLongEnough, UsernameAlreadyExistsException, StudentNotSavedInDB, AddStudentNotTyped, StudentAlreadyAdded, GradeNotAccepted, AddGradeEmpty, AddPresenceEmpty, PresenceNotAccepted, AddAbsenceEmpty, AbsenceNotAccepted, AddSubjectNotTyped, SubjectAlreadyAdded {
        initializeDatabase2();

        UserService.addUser("student","password", "password", "Student", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user=UserService.getAllUsers().get(0);
        assertThat(user.getUsername()).isEqualTo("student");
        assertThat(user.getRole()).isEqualTo("Student");

        UserService.addUser("teacher","password", "password", "Teacher", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(2);
        User user2=UserService.getAllUsers().get(1);
        assertThat(user2.getUsername()).isEqualTo("teacher");
        assertThat(user2.getRole()).isEqualTo("Teacher");

        CatalogService.addTeacher_Subject("teacher","subject");
        assertThat(CatalogService.getAllUsers()).isNotEmpty();
        assertThat(CatalogService.getAllUsers()).size().isEqualTo(1);
        CatalogService.addCourseMaterials("teacher", "subject", "Material1");
        Catalog catalog=CatalogService.getAllUsers().get(0);
        assertThat(catalog.getTeacherId()).isEqualTo("teacher");
        assertThat(catalog.getSubjectId()).isEqualTo("subject");
        assertThat(CatalogService.searchCourseMaterials("teacher", "subject").equals("Material1"));

    }

    @Test
    @DisplayName("Teacher can add homework section for students in his classes")
    void TeacherAddHomework() throws CompleteAllFieldsException, ConfirmPasswordException, PasswordNotLongEnough, UserNameNotLongEnough, UsernameAlreadyExistsException, StudentNotSavedInDB, AddStudentNotTyped, StudentAlreadyAdded, GradeNotAccepted, AddGradeEmpty, AddPresenceEmpty, PresenceNotAccepted, AddAbsenceEmpty, AbsenceNotAccepted, AddSubjectNotTyped, SubjectAlreadyAdded {
        initializeDatabase2();

        UserService.addUser("student","password", "password", "Student", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user=UserService.getAllUsers().get(0);
        assertThat(user.getUsername()).isEqualTo("student");
        assertThat(user.getRole()).isEqualTo("Student");

        UserService.addUser("teacher","password", "password", "Teacher", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(2);
        User user2=UserService.getAllUsers().get(1);
        assertThat(user2.getUsername()).isEqualTo("teacher");
        assertThat(user2.getRole()).isEqualTo("Teacher");

        CatalogService.addTeacher_Subject("teacher","subject");
        assertThat(CatalogService.getAllUsers()).isNotEmpty();
        assertThat(CatalogService.getAllUsers()).size().isEqualTo(1);
        CatalogService.addHomework("teacher", "subject", "Exercitii:");
        Catalog catalog=CatalogService.getAllUsers().get(0);
        assertThat(catalog.getTeacherId()).isEqualTo("teacher");
        assertThat(catalog.getSubjectId()).isEqualTo("subject");
        assertThat(CatalogService.searchHomeworkRequirements("teacher", "subject").equals("Exercitii:"));

    }

    @Test
    @DisplayName("Student can load homework solution for homewrok requirements teacher created")
    void StudentAddHomeworkSolution() throws CompleteAllFieldsException, ConfirmPasswordException, PasswordNotLongEnough, UserNameNotLongEnough, UsernameAlreadyExistsException, StudentNotSavedInDB, AddStudentNotTyped, StudentAlreadyAdded, GradeNotAccepted, AddGradeEmpty, AddPresenceEmpty, PresenceNotAccepted, AddAbsenceEmpty, AbsenceNotAccepted, AddSubjectNotTyped, SubjectAlreadyAdded {
        initializeDatabase2();

        UserService.addUser("student","password", "password", "Student", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user=UserService.getAllUsers().get(0);
        assertThat(user.getUsername()).isEqualTo("student");
        assertThat(user.getRole()).isEqualTo("Student");

        UserService.addUser("teacher","password", "password", "Teacher", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(2);
        User user2=UserService.getAllUsers().get(1);
        assertThat(user2.getUsername()).isEqualTo("teacher");
        assertThat(user2.getRole()).isEqualTo("Teacher");

        CatalogService.addTeacher_Subject("teacher","subject");
        assertThat(CatalogService.getAllUsers()).isNotEmpty();
        assertThat(CatalogService.getAllUsers()).size().isEqualTo(1);
        CatalogService.addHomework("teacher", "subject", "Exercitii:");
        Catalog catalog=CatalogService.getAllUsers().get(0);
        assertThat(catalog.getTeacherId()).isEqualTo("teacher");
        assertThat(catalog.getSubjectId()).isEqualTo("subject");
        assertThat(CatalogService.searchHomeworkRequirements("teacher", "subject").equals("Exercitii:"));
        CatalogService.addHomeworkSolution("teacher", "student", "subject", "1+1=2");
        assertThat(CatalogService.searchHomeworkSolution("teacher", "student", "subject").equals("1+1=2"));

    }

    @Test
    @DisplayName("Student can see useful course materials teacher submited")
    void StudentSeeCourseMaterials() throws CompleteAllFieldsException, ConfirmPasswordException, PasswordNotLongEnough, UserNameNotLongEnough, UsernameAlreadyExistsException, StudentNotSavedInDB, AddStudentNotTyped, StudentAlreadyAdded, GradeNotAccepted, AddGradeEmpty, AddPresenceEmpty, PresenceNotAccepted, AddAbsenceEmpty, AbsenceNotAccepted, AddSubjectNotTyped, SubjectAlreadyAdded {
        initializeDatabase2();

        UserService.addUser("student","password", "password", "Student", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user=UserService.getAllUsers().get(0);
        assertThat(user.getUsername()).isEqualTo("student");
        assertThat(user.getRole()).isEqualTo("Student");

        UserService.addUser("teacher","password", "password", "Teacher", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(2);
        User user2=UserService.getAllUsers().get(1);
        assertThat(user2.getUsername()).isEqualTo("teacher");
        assertThat(user2.getRole()).isEqualTo("Teacher");

        CatalogService.addTeacher_Subject("teacher","subject");
        assertThat(CatalogService.getAllUsers()).isNotEmpty();
        assertThat(CatalogService.getAllUsers()).size().isEqualTo(1);
        CatalogService.addHomework("teacher", "subject", "Exercitii:");
        Catalog catalog=CatalogService.getAllUsers().get(0);
        assertThat(catalog.getTeacherId()).isEqualTo("teacher");
        assertThat(catalog.getSubjectId()).isEqualTo("subject");
        CatalogService.addCourseMaterials("teacher", "subject", "Material1");
        assertThat(CatalogService.searchCourseMaterials("teacher", "subject").equals("Material1"));

    }

    @Test
    @DisplayName("Empty insert grade field exception")
    void TeacherAEmptyGradeField() throws CompleteAllFieldsException, ConfirmPasswordException, PasswordNotLongEnough, UserNameNotLongEnough, UsernameAlreadyExistsException, StudentNotSavedInDB, AddStudentNotTyped, StudentAlreadyAdded, GradeNotAccepted, AddGradeEmpty {
        initializeDatabase2();

        UserService.addUser("student","password", "password", "Student", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user=UserService.getAllUsers().get(0);
        assertThat(user.getUsername()).isEqualTo("student");
        assertThat(user.getRole()).isEqualTo("Student");

        CatalogService.addTeacher_Student_Subject("teacher", "student" ,"subject");
        assertThat(CatalogService.getAllUsers()).isNotEmpty();
        assertThat(CatalogService.getAllUsers()).size().isEqualTo(1);
        Catalog catalog=CatalogService.getAllUsers().get(0);
        assertThat(catalog.getTeacherId()).isEqualTo("teacher");
        assertThat(catalog.getStudentId()).isEqualTo("student");
        assertThat(catalog.getSubjectId()).isEqualTo("subject");

        assertThrows(AddGradeEmpty.class, () -> {
            CatalogService.addGrade("teacher", "student", "subject", "");
        });

    }

    @Test
    @DisplayName("Empty insert presence field exception")
    void TeacherAEmptyPresenceField() throws CompleteAllFieldsException, ConfirmPasswordException, PasswordNotLongEnough, UserNameNotLongEnough, UsernameAlreadyExistsException, StudentNotSavedInDB, AddStudentNotTyped, StudentAlreadyAdded, GradeNotAccepted, AddGradeEmpty {
        initializeDatabase2();

        UserService.addUser("student","password", "password", "Student", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user=UserService.getAllUsers().get(0);
        assertThat(user.getUsername()).isEqualTo("student");
        assertThat(user.getRole()).isEqualTo("Student");

        CatalogService.addTeacher_Student_Subject("teacher", "student" ,"subject");
        assertThat(CatalogService.getAllUsers()).isNotEmpty();
        assertThat(CatalogService.getAllUsers()).size().isEqualTo(1);
        Catalog catalog=CatalogService.getAllUsers().get(0);
        assertThat(catalog.getTeacherId()).isEqualTo("teacher");
        assertThat(catalog.getStudentId()).isEqualTo("student");
        assertThat(catalog.getSubjectId()).isEqualTo("subject");

        assertThrows(AddPresenceEmpty.class, () -> {
            CatalogService.addPresence("teacher", "student", "subject", "");
        });

    }

    @Test
    @DisplayName("Empty insert absence field exception")
    void TeacherAEmptyAbsenceField() throws CompleteAllFieldsException, ConfirmPasswordException, PasswordNotLongEnough, UserNameNotLongEnough, UsernameAlreadyExistsException, StudentNotSavedInDB, AddStudentNotTyped, StudentAlreadyAdded, GradeNotAccepted, AddGradeEmpty {
        initializeDatabase2();

        UserService.addUser("student","password", "password", "Student", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user=UserService.getAllUsers().get(0);
        assertThat(user.getUsername()).isEqualTo("student");
        assertThat(user.getRole()).isEqualTo("Student");

        CatalogService.addTeacher_Student_Subject("teacher", "student" ,"subject");
        assertThat(CatalogService.getAllUsers()).isNotEmpty();
        assertThat(CatalogService.getAllUsers()).size().isEqualTo(1);
        Catalog catalog=CatalogService.getAllUsers().get(0);
        assertThat(catalog.getTeacherId()).isEqualTo("teacher");
        assertThat(catalog.getStudentId()).isEqualTo("student");
        assertThat(catalog.getSubjectId()).isEqualTo("subject");

        assertThrows(AddAbsenceEmpty.class, () -> {
            CatalogService.addAbsence("teacher", "student", "subject", "");
        });

    }

    @Test
    @DisplayName("Empty insert grade field exception")
    void TeacherWrongGradeInput() throws CompleteAllFieldsException, ConfirmPasswordException, PasswordNotLongEnough, UserNameNotLongEnough, UsernameAlreadyExistsException, StudentNotSavedInDB, AddStudentNotTyped, StudentAlreadyAdded, GradeNotAccepted, AddGradeEmpty {
        initializeDatabase2();

        UserService.addUser("student","password", "password", "Student", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user=UserService.getAllUsers().get(0);
        assertThat(user.getUsername()).isEqualTo("student");
        assertThat(user.getRole()).isEqualTo("Student");

        CatalogService.addTeacher_Student_Subject("teacher", "student" ,"subject");
        assertThat(CatalogService.getAllUsers()).isNotEmpty();
        assertThat(CatalogService.getAllUsers()).size().isEqualTo(1);
        Catalog catalog=CatalogService.getAllUsers().get(0);
        assertThat(catalog.getTeacherId()).isEqualTo("teacher");
        assertThat(catalog.getStudentId()).isEqualTo("student");
        assertThat(catalog.getSubjectId()).isEqualTo("subject");

        assertThrows(NumberFormatException.class, () -> {
            CatalogService.addGrade("teacher", "student", "subject", "nota");
        });

    }

    @Test
    @DisplayName("Empty insert presence field exception")
    void TeacherWrongPresenceInput() throws CompleteAllFieldsException, ConfirmPasswordException, PasswordNotLongEnough, UserNameNotLongEnough, UsernameAlreadyExistsException, StudentNotSavedInDB, AddStudentNotTyped, StudentAlreadyAdded, GradeNotAccepted, AddGradeEmpty {
        initializeDatabase2();

        UserService.addUser("student","password", "password", "Student", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user=UserService.getAllUsers().get(0);
        assertThat(user.getUsername()).isEqualTo("student");
        assertThat(user.getRole()).isEqualTo("Student");

        CatalogService.addTeacher_Student_Subject("teacher", "student" ,"subject");
        assertThat(CatalogService.getAllUsers()).isNotEmpty();
        assertThat(CatalogService.getAllUsers()).size().isEqualTo(1);
        Catalog catalog=CatalogService.getAllUsers().get(0);
        assertThat(catalog.getTeacherId()).isEqualTo("teacher");
        assertThat(catalog.getStudentId()).isEqualTo("student");
        assertThat(catalog.getSubjectId()).isEqualTo("subject");

        assertThrows(NumberFormatException.class, () -> {
            CatalogService.addPresence("teacher", "student", "subject", "prezenta");
        });

    }

    @Test
    @DisplayName("Empty insert absence field exception")
    void TeacherWrongAbsenceInput() throws CompleteAllFieldsException, ConfirmPasswordException, PasswordNotLongEnough, UserNameNotLongEnough, UsernameAlreadyExistsException, StudentNotSavedInDB, AddStudentNotTyped, StudentAlreadyAdded, GradeNotAccepted, AddGradeEmpty {
        initializeDatabase2();

        UserService.addUser("student","password", "password", "Student", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user=UserService.getAllUsers().get(0);
        assertThat(user.getUsername()).isEqualTo("student");
        assertThat(user.getRole()).isEqualTo("Student");

        CatalogService.addTeacher_Student_Subject("teacher", "student" ,"subject");
        assertThat(CatalogService.getAllUsers()).isNotEmpty();
        assertThat(CatalogService.getAllUsers()).size().isEqualTo(1);
        Catalog catalog=CatalogService.getAllUsers().get(0);
        assertThat(catalog.getTeacherId()).isEqualTo("teacher");
        assertThat(catalog.getStudentId()).isEqualTo("student");
        assertThat(catalog.getSubjectId()).isEqualTo("subject");

        assertThrows(NumberFormatException.class, () -> {
            CatalogService.addAbsence("teacher", "student", "subject", "absenta");
        });

    }

    @Test
    @DisplayName("Find the subjects a given teacher teaches")
    void FindTeacherSubjects() throws CompleteAllFieldsException, ConfirmPasswordException, PasswordNotLongEnough, UserNameNotLongEnough, UsernameAlreadyExistsException, AddSubjectNotTyped, SubjectAlreadyAdded {
        UserService.addUser("teacher","password", "password", "Teacher", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user=UserService.getAllUsers().get(0);
        assertThat(user.getUsername()).isEqualTo("teacher");
        assertThat(user.getRole()).isEqualTo("Teacher");

        CatalogService.addTeacher_Subject("teacher", "subject");
        LinkedList <String> subjects = CatalogService.teacherSubjects("teacher");
        assertThat(subjects.size() == 1);

    }

    @Test
    @DisplayName("Find the subjects a given student attends")
    void FindStudentSubjects() throws CompleteAllFieldsException, ConfirmPasswordException, PasswordNotLongEnough, UserNameNotLongEnough, UsernameAlreadyExistsException, AddSubjectNotTyped, SubjectAlreadyAdded, StudentNotSavedInDB, AddStudentNotTyped, StudentAlreadyAdded {

        initializeDatabase2();

        UserService.addUser("student","password", "password", "Student", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user=UserService.getAllUsers().get(0);
        assertThat(user.getUsername()).isEqualTo("student");
        assertThat(user.getRole()).isEqualTo("Student");


        UserService.addUser("teacher","password", "password", "Teacher", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(2);
        User user2=UserService.getAllUsers().get(1);
        assertThat(user2.getUsername()).isEqualTo("teacher");
        assertThat(user2.getRole()).isEqualTo("Teacher");


        CatalogService.addTeacher_Subject("teacher", "subject");
        assertThat(CatalogService.getAllUsers()).isNotEmpty();
        assertThat(CatalogService.getAllUsers()).size().isEqualTo(1);
        Catalog catalog=CatalogService.getAllUsers().get(0);
        assertThat(catalog.getTeacherId()).isEqualTo("teacher");
        assertThat(catalog.getSubjectId()).isEqualTo("subject");


        CatalogService.addTeacher_Student_Subject("teacher", "student" ,"subject");

        LinkedList <String> subjects = CatalogService.studentsSubject("teacher", "subject");
        assertThat(subjects.size() == 1);

    }

    @Test
    @DisplayName("Find the course title and course teacher a given student")
    void FindStudentSubjectsAndTeacher() throws CompleteAllFieldsException, ConfirmPasswordException, PasswordNotLongEnough, UserNameNotLongEnough, UsernameAlreadyExistsException, AddSubjectNotTyped, SubjectAlreadyAdded, StudentNotSavedInDB, AddStudentNotTyped, StudentAlreadyAdded {

        initializeDatabase2();

        UserService.addUser("student","password", "password", "Student", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user=UserService.getAllUsers().get(0);
        assertThat(user.getUsername()).isEqualTo("student");
        assertThat(user.getRole()).isEqualTo("Student");


        UserService.addUser("teacher","password", "password", "Teacher", "firstname", "lastname");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(2);
        User user2=UserService.getAllUsers().get(1);
        assertThat(user2.getUsername()).isEqualTo("teacher");
        assertThat(user2.getRole()).isEqualTo("Teacher");


        CatalogService.addTeacher_Subject("teacher", "subject");
        assertThat(CatalogService.getAllUsers()).isNotEmpty();
        assertThat(CatalogService.getAllUsers()).size().isEqualTo(1);
        Catalog catalog=CatalogService.getAllUsers().get(0);
        assertThat(catalog.getTeacherId()).isEqualTo("teacher");
        assertThat(catalog.getSubjectId()).isEqualTo("subject");


        CatalogService.addTeacher_Student_Subject("teacher", "student" ,"subject");

        LinkedList <Pair<String, String>> subjectsAndteachers = CatalogService.studentSubjectsTeachers("student");
        assertThat(subjectsAndteachers.size() == 1);

    }

}



