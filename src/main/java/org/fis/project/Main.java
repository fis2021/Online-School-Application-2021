package org.fis.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.fis.project.model.User;
import org.fis.project.services.FileSystemService;
import org.fis.project.services.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main extends Application {

    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        initDirectory();
        UserService.initDatabase();
        primaryStage.setTitle("Online School Application - Registration");
        scene=new Scene(loadFXML("login"), 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initDirectory() {
        Path applicationHomePath = FileSystemService.APPLICATION_HOME_PATH;
        if (!Files.exists(applicationHomePath))
            applicationHomePath.toFile().mkdirs();
    }

    public static void setRoot(String fxml) throws Exception {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getClassLoader().getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
