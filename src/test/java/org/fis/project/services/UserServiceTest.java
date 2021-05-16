package org.fis.project.services;

import org.apache.commons.io.FileUtils;
import org.fis.project.exceptions.*;
import org.fis.project.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    public static final String ADMIN_123 = "admin123";

    @BeforeEach
    void setUp() throws Exception{
        FileSystemService.APPLICATION_FOLDER=".test-database";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
    }

    @Test
    void initializeDatabase(){
        assertThat(UserService.getAllUsers()).isNotNull();
        assertThat(UserService.getAllUsers()).isEmpty();
    }

    @Test
    @DisplayName("User is added in database")
    void addUserDatabase() throws ConfirmPasswordException, UsernameAlreadyExistsException, CompleteAllFieldsException, PasswordNotLongEnough, UserNameNotLongEnough {
        UserService.addUser(ADMIN_123, ADMIN_123, ADMIN_123,"Teacher", ADMIN_123, ADMIN_123);
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user=UserService.getAllUsers().get(0);
        assertThat(user.getUsername()).isEqualTo(ADMIN_123);
        assertThat(user.getPassword()).isEqualTo(UserService.encodePassword(ADMIN_123,ADMIN_123));
        assertThat(user.getRole()).isEqualTo("Teacher");
    }

    @Test
    @DisplayName("User can not be added twice")
    void addSecondUser(){
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            UserService.addUser(ADMIN_123, ADMIN_123, ADMIN_123,"Teacher", ADMIN_123, ADMIN_123);
            UserService.addUser(ADMIN_123, ADMIN_123, ADMIN_123,"Teacher", ADMIN_123, ADMIN_123);
        });
    }

    @Test
    @DisplayName("Please complete all fields(username not selected)")
    void selectUsername(){
        assertThrows(CompleteAllFieldsException.class , () -> {
            UserService.addUser("", ADMIN_123, ADMIN_123,"Teacher", ADMIN_123, ADMIN_123);
        });
    }

    @Test
    @DisplayName("Please complete all fields(password not selected)")
    void selectPassword(){
        assertThrows(CompleteAllFieldsException.class , () -> {
            UserService.addUser(ADMIN_123, "", ADMIN_123,"Teacher", ADMIN_123, ADMIN_123);
        });
    }

    @Test
    @DisplayName("Please complete all fields(retypePassword not selected)")
    void selectRetypePassword(){
        assertThrows(CompleteAllFieldsException.class , () -> {
            UserService.addUser(ADMIN_123, ADMIN_123, "","Teacher", ADMIN_123, ADMIN_123);
        });
    }

    @Test
    @DisplayName("Please complete all fields(role not selected)")
    void selectRole(){
        assertThrows(CompleteAllFieldsException.class , () -> {
            UserService.addUser(ADMIN_123, ADMIN_123, ADMIN_123,null, ADMIN_123, ADMIN_123);
        });
    }

    @Test
    @DisplayName("Please complete all fields(firstName not selected)")
    void selectFirstName(){
        assertThrows(CompleteAllFieldsException.class , () -> {
            UserService.addUser(ADMIN_123, ADMIN_123, ADMIN_123,"Teacher", "", ADMIN_123);
        });
    }

    @Test
    @DisplayName("Please complete all fields(lastName not selected)")
    void selectLastName(){
        assertThrows(CompleteAllFieldsException.class , () -> {
            UserService.addUser(ADMIN_123, ADMIN_123, ADMIN_123,"Teacher", ADMIN_123, "");
        });
    }

    @Test
    @DisplayName("Username is not long enough")
    void usernameNotLongEnough(){
        assertThrows(UserNameNotLongEnough.class , () ->{
            UserService.addUser("1234", ADMIN_123, ADMIN_123,"Teacher", ADMIN_123, ADMIN_123);
        });
    }

    @Test
    @DisplayName("Password is not long enough")
    void passwordNotLongEnough(){
        assertThrows(PasswordNotLongEnough.class , () ->{
            UserService.addUser(ADMIN_123, "123", ADMIN_123,"Teacher", ADMIN_123, ADMIN_123);
        });
    }

    @Test
    @DisplayName("Password and ConfirmPassword are equlas")
    void equalsPassword(){
        assertThrows(ConfirmPasswordException.class , () -> {
            UserService.addUser(ADMIN_123, ADMIN_123,"admin12","Teacher", ADMIN_123, ADMIN_123);
        });
    }

    @Test
    @DisplayName("Check valid credentials for LogIn")
    void checkCredentials() throws ConfirmPasswordException, UsernameAlreadyExistsException, CompleteAllFieldsException, PasswordNotLongEnough, UserNameNotLongEnough {
        UserService.addUser(ADMIN_123, ADMIN_123,ADMIN_123,"Student", ADMIN_123, ADMIN_123);
        UserService.addUser("andrei123", ADMIN_123,ADMIN_123,"Student", ADMIN_123, ADMIN_123);
        UserService.addUser("andrei1234", ADMIN_123,ADMIN_123,"Student", ADMIN_123, ADMIN_123);
        User user=UserService.getAllUsers().get(0);
        assertThat(user.getUsername()).isNotEqualTo("student123");
        assertThat(user.getPassword()).isEqualTo(UserService.encodePassword(ADMIN_123,ADMIN_123));
        user=UserService.getAllUsers().get(1);
        assertThat(user.getUsername()).isNotEqualTo("student123");
        assertThat(user.getPassword()).isNotEqualTo(UserService.encodePassword(ADMIN_123,ADMIN_123));
        user=UserService.getAllUsers().get(2);
        assertThat(user.getUsername()).isEqualTo("andrei1234");
        assertThat(user.getPassword()).isEqualTo(UserService.encodePassword("andrei1234",ADMIN_123));
    }

    @Test
    @DisplayName("Check invalid credentials for LogIn")
    void checkInvalidCredentials() throws ConfirmPasswordException, UsernameAlreadyExistsException, CompleteAllFieldsException, PasswordNotLongEnough, UserNameNotLongEnough {
        UserService.addUser(ADMIN_123, ADMIN_123,ADMIN_123,"Student", ADMIN_123, ADMIN_123);
        UserService.addUser("andrei123", ADMIN_123,ADMIN_123,"Student", ADMIN_123, ADMIN_123);
        UserService.addUser("andrei1234", ADMIN_123,ADMIN_123,"Student", ADMIN_123, ADMIN_123);
        User user=UserService.getAllUsers().get(0);
        assertThat(user.getUsername()).isNotEqualTo("student123");
        assertThat(user.getPassword()).isEqualTo(UserService.encodePassword(ADMIN_123,ADMIN_123));
        user=UserService.getAllUsers().get(1);
        assertThat(user.getUsername()).isNotEqualTo("student123");
        assertThat(user.getPassword()).isNotEqualTo(UserService.encodePassword(ADMIN_123,ADMIN_123));
        user=UserService.getAllUsers().get(2);
        assertThat(user.getUsername()).isEqualTo("andrei1234");
        assertThat(user.getPassword()).isNotEqualTo(UserService.encodePassword(ADMIN_123,ADMIN_123));
    }

    @Test
    @DisplayName("Complete all log in fields for username")
    void completeUsernameLogInFields(){
        assertThrows(CompleteLoginDataException.class,() ->{
            UserService.checkCkredentials(ADMIN_123, ADMIN_123);
            UserService.checkCkredentials("andrei123", ADMIN_123);
            UserService.checkCkredentials("", ADMIN_123);
        });
    }

    @Test
    @DisplayName("Complete all log in fields for password")
    void completePasswordLogInFields(){
        assertThrows(CompleteLoginDataException.class,() ->{
            UserService.checkCkredentials(ADMIN_123, ADMIN_123);
            UserService.checkCkredentials("andrei123", ADMIN_123);
            UserService.checkCkredentials( ADMIN_123,"");
        });
    }

    @Test
    @DisplayName("Verify encode password method")
    void encodePassword(){
        assertThat(UserService.encodePassword("andrei","marian")).isEqualTo(UserService.encodePassword("andrei","marian"));
        assertThat(UserService.encodePassword("andrei123","marian123")).isEqualTo(UserService.encodePassword("andrei123","marian123"));
        assertThat(UserService.encodePassword("andrei123","marian")).isNotEqualTo(UserService.encodePassword("andrei","marian"));
        assertThat(UserService.encodePassword("andrei","marian123")).isNotEqualTo(UserService.encodePassword("andrei","marian"));
        assertThat(UserService.encodePassword("andrei","marian123")).isNotEqualTo(UserService.encodePassword("andrei123","marian123"));
    }

    @Test
    @DisplayName("Student doesn't exists in database")
    void searchStudentDatabase() throws ConfirmPasswordException, UsernameAlreadyExistsException, CompleteAllFieldsException, PasswordNotLongEnough, UserNameNotLongEnough {
        UserService.addUser(ADMIN_123, ADMIN_123,ADMIN_123,"Student", ADMIN_123, ADMIN_123);
        UserService.addUser("andrei123", ADMIN_123,ADMIN_123,"Student", ADMIN_123, ADMIN_123);
        UserService.addUser("andrei1234", ADMIN_123,ADMIN_123,"Student", ADMIN_123, ADMIN_123);
        User user=UserService.getAllUsers().get(0);
        assertThat(user.getUsername()).isNotEqualTo("student123");
        user=UserService.getAllUsers().get(1);
        assertThat(user.getUsername()).isNotEqualTo("student123");
        user=UserService.getAllUsers().get(2);
        assertThat(user.getUsername()).isNotEqualTo("student123");
    }

    @Test
    @DisplayName("Student not saved in database Exception")
    void searchStudentDatabaseException() throws StudentNotSavedInDB, ConfirmPasswordException, UsernameAlreadyExistsException, CompleteAllFieldsException, PasswordNotLongEnough, UserNameNotLongEnough {
        assertThrows(StudentNotSavedInDB.class,()-> {
            UserService.addUser(ADMIN_123, ADMIN_123, ADMIN_123, "Student", ADMIN_123, ADMIN_123);
            UserService.addUser("andrei123", ADMIN_123, ADMIN_123, "Student", ADMIN_123, ADMIN_123);
            UserService.addUser("andrei1234", ADMIN_123, ADMIN_123, "Student", ADMIN_123, ADMIN_123);
            UserService.StudentuserName("student123");
        });
    }
}