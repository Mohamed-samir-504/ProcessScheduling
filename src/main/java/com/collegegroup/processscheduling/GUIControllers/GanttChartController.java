package com.collegegroup.processscheduling.GUIControllers;

import com.collegegroup.processscheduling.GUIProcess;
import com.collegegroup.processscheduling.util;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import static com.collegegroup.processscheduling.util.processFactory;
import static com.collegegroup.processscheduling.util.rightEdge;

//TODO() Add Priority Column in TableView


public class GanttChartController {
    ObservableList<GUIProcess> processList;

    @FXML HBox hbox;
    private int totalTime = 0;
    @FXML private TableColumn<GUIProcess, String> pidColumn;
    @FXML private TableColumn<GUIProcess, String> burstColumn;
    @FXML private TableColumn<GUIProcess, String> arrivalTimeColumn;
    @FXML private TableColumn<GUIProcess, String> priorityColumn;
    @FXML private TableView<GUIProcess> tableView;


    @FXML
    public void onClickDraw(ActionEvent ignoredEvent) {
        hbox.getChildren().clear();
        int currentTime = 0;
        for (GUIProcess s : processList)
        {
            hbox.getChildren().add(processFactory(s,Integer.parseInt(s.getBurst()), currentTime,totalTime));
            currentTime += Integer.parseInt(s.getBurst());
        }
        hbox.getChildren().add(rightEdge(currentTime));
    }
    //initialize the scene
    //reason for not using a constructor -> can't create constructor because FXMLLoader requires an empty constructor to instantiate an object
    //of the controller class.
    public void init(ObservableList<GUIProcess> x, int time)
    {
        //get the process list and the current time
        processList = x;
        totalTime = time;
        hbox.setSpacing(0);
        //set the columns of the table to read correct attributes
        pidColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));
        burstColumn.setCellValueFactory(new PropertyValueFactory<>("burst"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));

        //fill the table with the list saved from the last scene.
        tableView.setItems(processList);
    }

}