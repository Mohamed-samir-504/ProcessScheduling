package com.collegegroup.processscheduling;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Scene1Controller {

    @FXML
    TextField nameTextField;

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    void onStaticClick(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Static mode");
        stage.show();
    }
}
