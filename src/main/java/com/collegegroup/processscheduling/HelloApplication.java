package com.collegegroup.processscheduling;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    //Scene : a drawing surface for graphical content (Text, shapes , images)


    @Override
    //testt
    public void start(Stage stage) throws IOException {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Process scheduling");
            stage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch();
    }

}