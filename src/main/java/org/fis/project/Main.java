package org.fis.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.fis.project.model.Catalog;
import javafx.scene.image.Image;
import org.fis.project.model.User;
import org.fis.project.services.CatalogService;
import org.fis.project.services.FileSystemService;
import org.fis.project.services.UserService;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main extends Application {

    private static Scene scene;
    private static FXMLLoader path;

    @Override
    public void start(Stage primaryStage) throws Exception {
        initDirectory();
        UserService.initDatabase();
        CatalogService.initDatabase();

        primaryStage.setTitle("Online School Application");
        scene=new Scene(loadFXML("login"), 300, 275);
        primaryStage.setScene(scene);

        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/9.png")));

        primaryStage.setWidth(1550);
        primaryStage.setHeight(835);

        primaryStage.centerOnScreen();
        primaryStage.setMaximized(false);
        primaryStage.setResizable(false);
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
        path=fxmlLoader;
        return fxmlLoader.load();
    }

    public static FXMLLoader getPath() {
        return path;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
