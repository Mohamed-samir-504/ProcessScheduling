package com.collegegroup.processscheduling;


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
    private String[] schedulerTypes = {"Round robin", "FCFS", "SJF (preemptive)",
            "SJF (non-preemptive)","priority (preemptive)","priority (non-preemptive)"};


    private Stage stage;

    private Scene scene;

    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.getItems().addAll(schedulerTypes);

    }

    @FXML
    public void onGo(ActionEvent event) throws IOException {
        String currentChoice = choiceBox.getValue();

        Parent tableViewParent = null;
        if (currentChoice.equals("FCFS") ||
            currentChoice.equals("SJF (preemptive)") ||
            currentChoice.equals("SJF (non-preemptive)")) {
            tableViewParent = FXMLLoader.load(getClass().getResource("FCFS_SJF.fxml"));
        }
        else if (currentChoice.equals("Round robin"))
        {
            tableViewParent = FXMLLoader.load(getClass().getResource("RoundRobin.fxml"));
        } else if (currentChoice.equals("priority (preemptive)")||currentChoice.equals("priority (non-preemptive)")) {
            tableViewParent = FXMLLoader.load(getClass().getResource("Priority.fxml"));
        }
        else
            return;


        Scene tableViewScene = new Scene(tableViewParent);
        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

}