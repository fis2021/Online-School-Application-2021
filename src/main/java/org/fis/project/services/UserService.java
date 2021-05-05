package org.fis.project.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis.project.exceptions.CompleteAllFieldsException;
import org.fis.project.model.User;
import org.fis.project.exceptions.UsernameAlreadyExistsException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static org.fis.project.services.FileSystemService.getPathToFile;

public class UserService {

    private static ObjectRepository<User> userRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("registration-example.db").toFile())
                .openOrCreate("test", "test");

        userRepository = database.getRepository(User.class);
    }

    public static void addUser(String username, String password, String role) throws UsernameAlreadyExistsException, CompleteAllFieldsException {
        checkUserDoesNotAlreadyExist(username);
        checkCredentialsAreNotEmpty(username , password , role);
        userRepository.insert(new User(username, encodePassword(username, password), role));
    }

    public static boolean checkCkredentials(String username, String password){
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()) &&
                    Objects.equals(encodePassword(username , password),user.getPassword()))
                return true;
        }
        return false;
    }

    private static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }

    private static void checkCredentialsAreNotEmpty(String username , String password , String role) throws CompleteAllFieldsException {
        if(username.equals(new String("")) || password.equals(new String("")) || role==null)
            throw new CompleteAllFieldsException();
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
