package org.fis.project.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis.project.exceptions.*;
import org.fis.project.exceptions.UserNameNotLongEnough;
import org.fis.project.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static org.fis.project.services.FileSystemService.getPathToFile;

public class UserService {

    private static ObjectRepository<User> userRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("OSA-Registration.db").toFile())
                .openOrCreate("test", "test");

        userRepository = database.getRepository(User.class);
    }

    public static void addUser(String username, String password, String confirmpassword, String role, String firstname, String lastname) throws UsernameAlreadyExistsException, CompleteAllFieldsException, ConfirmPasswordException, UserNameNotLongEnough, PasswordNotLongEnough {
        checkCredentialsAreNotEmpty(username , password , confirmpassword , role, firstname, lastname);
        checkUserNameLength(username);
        checkUserDoesNotAlreadyExist(username);
        checkPasswordLength(password, confirmpassword);
        checkConfirmPassword(password, confirmpassword);
        userRepository.insert(new User(username, encodePassword(username, password), encodePassword(username, confirmpassword) , role, firstname, lastname));
    }

    private static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }

    private static void checkCredentialsAreNotEmpty(String username , String password , String confirmpassword , String role, String firstname, String lastname) throws CompleteAllFieldsException {
        if(username.equals(new String("")) || password.equals(new String("")) || confirmpassword.equals(new String("")) || firstname.equals(new String("")) || lastname.equals(new String("")) || role==null)
            throw new CompleteAllFieldsException();
    }

    private static void checkConfirmPassword(String password , String confirmpassword) throws ConfirmPasswordException {
        if(password.equals(confirmpassword) == false)
            throw new ConfirmPasswordException(password, confirmpassword);
    }

    private static void checkUserNameLength(String username) throws UserNameNotLongEnough {
        if(username.length() < 6)
            throw new UserNameNotLongEnough();
    }

    private static void checkPasswordLength(String password, String confirmpassword) throws PasswordNotLongEnough {
        if(password.length() < 6 || confirmpassword.length() < 6)
            throw new PasswordNotLongEnough();
    }

    private static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }


}
