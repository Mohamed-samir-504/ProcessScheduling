package com.collegegroup.processscheduling;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
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
    public void onGo(ActionEvent event)  {
        String type = choiceBox.getValue();

        if(Objects.equals(type, "FCFS")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FCFS_scene.fxml"));

            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

//            FCFS_sceneController FCFS = loader.getController();
//            FCFS.display(textField.getText());

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("FCFS");
            stage.show();
        }
    }

}