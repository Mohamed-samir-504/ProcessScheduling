package com.collegegroup.processscheduling.GUIControllers;

import com.collegegroup.processscheduling.Comparators.SortByFCFS;
import com.collegegroup.processscheduling.GUIProcess;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;

import static com.collegegroup.processscheduling.util.*;


public class LiveGanttChartController {
    ObservableList<GUIProcess> processList;

    @FXML HBox hbox;
    private int totalTime = 0, scale;
    @FXML private TableColumn<GUIProcess, String> pidColumn;
    @FXML private TableColumn<GUIProcess, String> burstColumn;
    @FXML private TableColumn<GUIProcess, String> arrivalTimeColumn;
    @FXML private TableColumn<GUIProcess, String> priorityColumn;
    @FXML private TextField pidTextField,burstTextField,arrivalTimeTextField;
    @FXML private TableView<GUIProcess> tableView;
    @FXML private Text currentTime;
    ArrayList<GUIProcess> processedItems;
    int current;
    private String mode;



    @FXML
    public void onClickDraw(ActionEvent ignoredEvent) {
         current = 0;

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),event -> {
            currentTime.setText(Integer.toString(current));
            GUIProcess currentProcess = tableView.getItems().get(0);
            hbox.getChildren().add(liveProcessFactory(currentProcess,current));

            if(currentProcess.getBurstInt()>0)
            {
                currentProcess.setBurst(currentProcess.getBurstInt()-1);
                tableView.refresh();
            }
            if(currentProcess.getBurstInt()<=0)
            {   processedItems.add(currentProcess);
                currentProcess.setEndTime(current);
                tableView.getItems().remove(currentProcess);
                tableView.getItems().get(0).setStartTime(current);
            }
            tableView.getItems().sort(new SortByFCFS());
            current++;

        }));
        timeline.setCycleCount(totalTime);
        timeline.play();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(current==totalTime)
                {
                    hbox.getChildren().add(rightEdge(current));
                    tableView.getItems().clear();
                }
            }
        });


    }

    public void init(ObservableList<GUIProcess> x, int time, String mode)
    {
        //get the process list and the current time
        processList = x;
        totalTime = time;
        scale = time;
        this.mode = mode;
        hbox.setSpacing(0);
        //set the columns of the table to read correct attributes
        pidColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));
        burstColumn.setCellValueFactory(new PropertyValueFactory<>("burst"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        //fill the table with the list saved from the last scene.
        tableView.setItems(processList);
        processedItems = new ArrayList<>();
    }

    @FXML
    public void insertButtonPushed()
    {
        GUIProcess newProcess = new GUIProcess(pidTextField.getText(),burstTextField.getText(),arrivalTimeTextField.getText());
        if(newProcess.isEmpty() && newProcess.isValid()) {
            tableView.getItems().add(newProcess);
            totalTime+=Integer.parseInt(newProcess.getBurst());
            if(mode.equals("FCFS")) {
                tableView.getItems().sort(new SortByFCFS());
            }
        }

    }

    @FXML
    public void deleteButtonPushed() {
        ObservableList<GUIProcess> allProcesses, selected;
        allProcesses = tableView.getItems();
        selected = tableView.getSelectionModel().getSelectedItems();

        for (GUIProcess GUIProcesss : selected) {
            allProcesses.remove(GUIProcesss);
            totalTime-=Integer.parseInt(GUIProcesss.getBurst());
        }
        if(mode.equals("FCFS")) {
            tableView.getItems().sort(new SortByFCFS());
        }
    }




}