package com.collegegroup.processscheduling.GUIControllers;

import com.collegegroup.processscheduling.Comparators.SortByFCFS;
import com.collegegroup.processscheduling.Comparators.SortByPriority_NP;
import com.collegegroup.processscheduling.Comparators.SortBySJF_NP;
import com.collegegroup.processscheduling.GUIProcess;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import static com.collegegroup.processscheduling.util.*;
import static com.collegegroup.processscheduling.util.liveProcessFactory;

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
    private String mode;
    private int currentTimeCounter;


    @FXML
    public void onClickDraw(ActionEvent ignoredEvent) {
        currentTimeCounter = 0;
        if(mode.equals("FCFS")) FCFS();
        if(mode.equals("SJF")) SJF();
        if(mode.equals("SJF_P")) SJF_P();
        if(mode.equals("Priority")) priority();
        if(mode.equals("Priority_P")) priority_P();
        if(mode.equals("RoundRobin")) roundRobin();
    }
    //initialize the scene
    //reason for not using a constructor -> can't create constructor because FXMLLoader requires an empty constructor to instantiate an object
    //of the controller class.
    public void init(ObservableList<GUIProcess> x, int time,String mode)
    {
        //get the process list and the current time
        processList = x;
        totalTime = time;
        this.mode = mode;
        hbox.setSpacing(0);
        //set the columns of the table to read correct attributes
        pidColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));
        burstColumn.setCellValueFactory(new PropertyValueFactory<>("burst"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));

        //fill the table with the list saved from the last scene.
        tableView.setItems(processList);
    }

    private void FCFS()
    {
        hbox.getChildren().clear();
        currentTimeCounter = 0;
        for (GUIProcess s : processList)
        {
            hbox.getChildren().add(processFactory(s,Integer.parseInt(s.getBurst()), currentTimeCounter,totalTime));
            currentTimeCounter += Integer.parseInt(s.getBurst());
        }
        hbox.getChildren().add(rightEdge(currentTimeCounter));
    }

    private void SJF()
    {
        hbox.getChildren().clear();

        currentTimeCounter = 0;
        tableView.getItems().sort(new SortBySJF_NP(currentTimeCounter));
        while (!tableView.getItems().isEmpty())
        {
            GUIProcess current = tableView.getItems().get(0);
            hbox.getChildren().add(processFactory(current,Integer.parseInt(current.getBurst()), currentTimeCounter,totalTime));
            currentTimeCounter += Integer.parseInt(current.getBurst());
            tableView.getItems().remove(current);
            tableView.getItems().sort(new SortBySJF_NP(currentTimeCounter));

        }
        hbox.getChildren().add(rightEdge(currentTimeCounter));
    }
    private void SJF_P()
    {}
    private void roundRobin()
    {}
    private void priority()
    {
        hbox.getChildren().clear();

        currentTimeCounter = 0;
        tableView.getItems().sort(new SortByPriority_NP(currentTimeCounter));
        while (!tableView.getItems().isEmpty())
        {
            GUIProcess current = tableView.getItems().get(0);
            hbox.getChildren().add(processFactory(current,Integer.parseInt(current.getBurst()), currentTimeCounter,totalTime));
            currentTimeCounter += Integer.parseInt(current.getBurst());
            tableView.getItems().remove(current);
            tableView.getItems().sort(new SortByPriority_NP(currentTimeCounter));

        }
        hbox.getChildren().add(rightEdge(currentTimeCounter));
    }
    private void priority_P()
    {}

}