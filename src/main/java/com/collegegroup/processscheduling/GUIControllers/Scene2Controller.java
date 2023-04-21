package com.collegegroup.processscheduling.GUIControllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Scene2Controller implements Initializable {

    @FXML
    private TextField textField;
    @FXML
    private ChoiceBox<String> choiceBox = new ChoiceBox<String>();
    private final String[] schedulerTypes = {"Round Robin", "FC-FS", "SJF" ,"Priority based"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.getItems().addAll(schedulerTypes);

    }

    @FXML
    public void onGo(ActionEvent event) throws IOException {
        String currentChoice = choiceBox.getValue();

        Parent tableViewParent = null;
        switch (currentChoice) {
            case "FC-FS" -> tableViewParent = FXMLLoader.load(getClass().getResource("FCFS.fxml"));
            case "SJF" -> tableViewParent = FXMLLoader.load(getClass().getResource("SJF.fxml"));
            case "Round Robin" -> tableViewParent = FXMLLoader.load(getClass().getResource("RoundRobin.fxml"));
            case "Priority based" -> tableViewParent = FXMLLoader.load(getClass().getResource("Priority.fxml"));
            default -> {
                return;
            }
        }

        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

}