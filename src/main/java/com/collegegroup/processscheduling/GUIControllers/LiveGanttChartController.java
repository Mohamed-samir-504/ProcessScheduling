package com.collegegroup.processscheduling.GUIControllers;

import com.collegegroup.processscheduling.Comparators.SortByFCFS;
import com.collegegroup.processscheduling.Comparators.SortByPriority_NP;
import com.collegegroup.processscheduling.Comparators.SortBySJF_NP;
import com.collegegroup.processscheduling.GUIProcess;
import javafx.animation.Animation;
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
    @FXML private Text currentTimeText,avgTurnAroundText,avgWaitingTimeText;
    ArrayList<GUIProcess> processedItems;
    int currentTimeCounter, itr,timeDrawStart;
    Timeline timeline;
    private String mode,quantum = "1";



    @FXML
    public void onClickDraw(ActionEvent ignoredEvent) {
         currentTimeCounter = 0;
         itr = 0;
         timeDrawStart = 0;
         if(mode.equals("FCFS")) FCFS();
         if(mode.equals("SJF")) SJF();
         if(mode.equals("SJF_P")) SJF_P();
         if(mode.equals("Priority")) priority();
         if(mode.equals("Priority_P")) priority_P();
         if(mode.equals("RoundRobin")) roundRobin();
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
        //log of the items used for calculations
        processedItems = new ArrayList<>();
    }

    public void init(ObservableList<GUIProcess> x, int time, String mode, String quantum)
    {
        //get the process list and the current time
        processList = x;
        totalTime = time;
        scale = time;
        this.mode = mode;
        this.quantum = quantum;
        hbox.setSpacing(0);
        //set the columns of the table to read correct attributes
        pidColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));
        burstColumn.setCellValueFactory(new PropertyValueFactory<>("burst"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        //fill the table with the list saved from the last scene.
        tableView.setItems(processList);
        //log of the items used for calculations
        processedItems = new ArrayList<>();
    }


    @FXML
    public void insertButtonPushed()
    {
        GUIProcess newProcess = new GUIProcess(pidTextField.getText(),burstTextField.getText(),arrivalTimeTextField.getText());
        if(newProcess.isNotEmpty() && newProcess.isValid()) {
            tableView.getItems().add(newProcess);
            totalTime+=Integer.parseInt(newProcess.getBurst());

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

    }

    private void FCFS()
    {
        tableView.getItems().sort(new SortByFCFS());
        timeline = new Timeline(new KeyFrame(Duration.seconds(1),event -> {
            if(tableView.getItems().size()==0)
            {finish(); return;}

            currentTimeText.setText(Integer.toString(currentTimeCounter));
            GUIProcess currentProcess = tableView.getItems().get(0);

            if(currentTimeCounter<currentProcess.getArrivalTimeInt())
            {
                currentTimeCounter++;
                timeDrawStart++;
                return;
            }
            hbox.getChildren().add(liveProcessFactory(currentProcess, currentTimeCounter,1));
            if(currentProcess.getBurstInt()>0)
            {
                currentProcess.setBurst(currentProcess.getBurstInt()-1);
                tableView.refresh();
            }

            if(currentProcess.getBurstInt()==0)
            {   processedItems.add(currentProcess);
                currentProcess.setEndTime(currentTimeCounter+1);
                tableView.getItems().remove(currentProcess);
                tableView.getItems().sort(new SortByFCFS());
                if(tableView.getItems().size()>0)
                    tableView.getItems().get(0).setStartTime(currentTimeCounter+1);
            }
            avgTurnAroundText.setText(avgTurnaround());
            avgWaitingTimeText.setText(avgWaiting());
            currentTimeCounter++;
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    hbox.getChildren().add(rightEdge(currentTimeCounter));
                    tableView.getItems().clear();
            }
        });
    }
    private void SJF()
    {
        tableView.getItems().sort(new SortBySJF_NP(currentTimeCounter));
        timeline = new Timeline(new KeyFrame(Duration.seconds(1),event -> {
            if(tableView.getItems().size()==0)
            {finish(); return;}
            currentTimeText.setText(Integer.toString(currentTimeCounter));
            GUIProcess currentProcess = tableView.getItems().get(0);

            if(currentTimeCounter<currentProcess.getArrivalTimeInt())
            {
                currentTimeCounter++;
                timeDrawStart++;
                return;
            }
            hbox.getChildren().add(liveProcessFactory(currentProcess, currentTimeCounter,1));
            if(currentProcess.getBurstInt()>0)
            {
                currentProcess.setBurst(currentProcess.getBurstInt()-1);
                tableView.refresh();
            }

            if(currentProcess.getBurstInt()==0)
            {   processedItems.add(currentProcess);
                currentProcess.setEndTime(currentTimeCounter+1);
                tableView.getItems().remove(currentProcess);
                tableView.getItems().sort(new SortBySJF_NP(currentTimeCounter+1));
                if(tableView.getItems().size()>0)
                    tableView.getItems().get(0).setStartTime(currentTimeCounter+1);
            }
            avgTurnAroundText.setText(avgTurnaround());
            avgWaitingTimeText.setText(avgWaiting());
            currentTimeCounter++;
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hbox.getChildren().add(rightEdge(currentTimeCounter));
                tableView.getItems().clear();
            }
        });
    }
    private void SJF_P()
    {
        tableView.getItems().sort(new SortBySJF_NP(currentTimeCounter));
         timeline = new Timeline(new KeyFrame(Duration.seconds(1),event -> {
             if(tableView.getItems().size()==0)
             {finish(); return;}
            currentTimeText.setText(Integer.toString(currentTimeCounter));
            GUIProcess currentProcess = tableView.getItems().get(0);

            if(currentTimeCounter<currentProcess.getArrivalTimeInt())
            {
                currentTimeCounter++;
                timeDrawStart++;
                return;
            }
            hbox.getChildren().add(liveProcessFactory(currentProcess, currentTimeCounter,1));
            if(currentProcess.getBurstInt()>0)
            {
                currentProcess.setBurst(currentProcess.getBurstInt()-1);
                tableView.refresh();
            }

            if(currentProcess.getBurstInt()==0)
            {   processedItems.add(currentProcess);
                currentProcess.setEndTime(currentTimeCounter+1);
                tableView.getItems().remove(currentProcess);
                tableView.getItems().sort(new SortBySJF_NP(currentTimeCounter+1));
                if(tableView.getItems().size()>0)
                    tableView.getItems().get(0).setStartTime(currentTimeCounter+1);
            }
            avgTurnAroundText.setText(avgTurnaround());
            avgWaitingTimeText.setText(avgWaiting());
            tableView.getItems().sort(new SortBySJF_NP(currentTimeCounter));
            currentTimeCounter++;
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hbox.getChildren().add(rightEdge(currentTimeCounter));
                tableView.getItems().clear();
            }
        });
    }
    private void roundRobin()
    {
        tableView.getItems().sort(new SortByFCFS());
        itr = 0;
         timeline = new Timeline(new KeyFrame(Duration.seconds(1),event -> {
             if(tableView.getItems().size()==0)
             {finish(); return;}

            currentTimeText.setText(Integer.toString(currentTimeCounter));

                if(tableView.getItems().size()<=itr)
                {itr = (itr+1)%(tableView.getItems().size()+processedItems.size());
                    return;}
                GUIProcess currentProcess = tableView.getItems().get(itr);
            if(currentTimeCounter<currentProcess.getArrivalTimeInt())
            {
                currentTimeCounter++;
                return;
            }

                hbox.getChildren().add(liveProcessFactory(currentProcess, currentTimeCounter,Integer.parseInt(quantum)));
                if(currentProcess.getBurstInt()>0)
                {
                    currentProcess.setBurst(currentProcess.getBurstInt()-1);
                    tableView.refresh();
                }
                if(currentProcess.getBurstInt()==0)
                {   processedItems.add(currentProcess);
                    currentProcess.setEndTime(currentTimeCounter+1);
                    tableView.getItems().remove(currentProcess);
                    tableView.getItems().sort(new SortBySJF_NP(currentTimeCounter+1));
                    if(tableView.getItems().size()>0)
                        tableView.getItems().get(0).setStartTime(currentTimeCounter+1);
                }
                currentTimeCounter++;

            avgTurnAroundText.setText(avgTurnaround());
           //TODO() AVGWaitingTimePreemptive
                itr = (itr+1)%(tableView.getItems().size()+processedItems.size());

        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hbox.getChildren().add(rightEdge(currentTimeCounter));
                tableView.getItems().clear();
            }
        });


    }
    private void priority()
    {
        tableView.getItems().sort(new SortByPriority_NP(currentTimeCounter));
         timeline = new Timeline(new KeyFrame(Duration.seconds(1),event -> {
             if(tableView.getItems().size()==0)
             {finish(); return;}
            currentTimeText.setText(Integer.toString(currentTimeCounter));
            GUIProcess currentProcess = tableView.getItems().get(0);

            if(currentTimeCounter<currentProcess.getArrivalTimeInt())
            {
                currentTimeCounter++;
                timeDrawStart++;
                return;
            }
            hbox.getChildren().add(liveProcessFactory(currentProcess, currentTimeCounter,1));
            if(currentProcess.getBurstInt()>0)
            {
                currentProcess.setBurst(currentProcess.getBurstInt()-1);
                tableView.refresh();
            }

            if(currentProcess.getBurstInt()==0)
            {   processedItems.add(currentProcess);
                currentProcess.setEndTime(currentTimeCounter+1);
                tableView.getItems().remove(currentProcess);
                tableView.getItems().sort(new SortByPriority_NP(currentTimeCounter+1));
                if(tableView.getItems().size()>0)
                    tableView.getItems().get(0).setStartTime(currentTimeCounter+1);
            }
            avgTurnAroundText.setText(avgTurnaround());
            avgWaitingTimeText.setText(avgWaiting());
            currentTimeCounter++;
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hbox.getChildren().add(rightEdge(currentTimeCounter));
                tableView.getItems().clear();
            }
        });
    }
    private void priority_P()
    {
        tableView.getItems().sort(new SortByPriority_NP(currentTimeCounter));
         timeline = new Timeline(new KeyFrame(Duration.seconds(1),event -> {
             if(tableView.getItems().size()==0)
             {finish(); return;}
            currentTimeText.setText(Integer.toString(currentTimeCounter));
            GUIProcess currentProcess = tableView.getItems().get(0);

            if(currentTimeCounter<currentProcess.getArrivalTimeInt())
            {
                currentTimeCounter++;
                timeDrawStart++;
                return;
            }
            hbox.getChildren().add(liveProcessFactory(currentProcess, currentTimeCounter,50));
            if(currentProcess.getBurstInt()>0)
            {
                currentProcess.setBurst(currentProcess.getBurstInt()-1);
                tableView.refresh();
            }

            if(currentProcess.getBurstInt()==0)
            {   processedItems.add(currentProcess);
                currentProcess.setEndTime(currentTimeCounter+1);
                tableView.getItems().remove(currentProcess);
                tableView.getItems().sort(new SortByPriority_NP(currentTimeCounter+1));
                if(tableView.getItems().size()>0)
                    tableView.getItems().get(0).setStartTime(currentTimeCounter+1);
            }
            avgTurnAroundText.setText(avgTurnaround());
            //TODO() AVGWaitingTimePreemptive
            tableView.getItems().sort(new SortByPriority_NP(currentTimeCounter));
            currentTimeCounter++;
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hbox.getChildren().add(rightEdge(currentTimeCounter));
                tableView.getItems().clear();
            }
        });
    }
    private String avgWaiting()
    {
        double sum = 0;
        for(GUIProcess s : processedItems)
        {
            sum += (s.getStartTime()-s.getArrivalTimeInt());
        }
       return Double.toString(sum /= processedItems.size()).formatted("%2lf");
    }
    private String avgTurnaround()
    {
        double sum = 0;
        for(GUIProcess s : processedItems)
        {
            sum += (s.getEndTime()-s.getArrivalTimeInt());
        }
        return Double.toString(sum /= processedItems.size()).formatted("%2lf");

    }

    private void finish()
    {
        timeline.stop();
        timeline.getOnFinished().handle(new ActionEvent());
    }



}