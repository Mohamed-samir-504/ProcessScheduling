package com.collegegroup.processscheduling.GUIControllers;

import com.collegegroup.processscheduling.Comparators.SortByFCFS;
import com.collegegroup.processscheduling.Comparators.SortByPriority_NP;
import com.collegegroup.processscheduling.Comparators.SortBySJF_NP;
import com.collegegroup.processscheduling.GUIProcess;
import com.collegegroup.processscheduling.Process;
import com.collegegroup.processscheduling.util;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

import static com.collegegroup.processscheduling.util.*;

//TODO() Add Priority Column in TableView


public class GanttChartController {
    ObservableList<GUIProcess> processList;

    @FXML HBox hbox;
    private int totalTime = 0;
    @FXML private TableColumn<GUIProcess, String> pidColumn;
    @FXML private TableColumn<GUIProcess, String> burstColumn;
    @FXML private TableColumn<GUIProcess, String> arrivalTimeColumn;
    @FXML private TableColumn<GUIProcess, String> priorityColumn;

    @FXML private TableColumn<GUIProcess, String> pidColumn2;
    @FXML private TableColumn<GUIProcess, String> startTimeColumn1;
    @FXML private TableColumn<GUIProcess, String> arrivalTimeColumn2;
    @FXML private TableColumn<GUIProcess, String> endTimeColumn1;

    @FXML private TableView<GUIProcess> tableView;
    @FXML private TableView<GUIProcess> tableView2;

    @FXML private Text avgTurnAroundText,avgWaitingTimeText;
    private String mode,quantum;
    private int currentTimeCounter;
    private ArrayList<Process>arr;
    ArrayList<Process>resultt;
    ArrayList<GUIProcess> processedItems;


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

        pidColumn2.setCellValueFactory(new PropertyValueFactory<>("pid"));
        arrivalTimeColumn2.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        startTimeColumn1.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn1.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        //fill the table with the list saved from the last scene.
        tableView.setItems(processList);
        arr = new ArrayList<Process>();
        resultt = new ArrayList<Process>();
        processedItems = new ArrayList<>();
    }

    public void init(ObservableList<GUIProcess> items, int sum, String roundRobin, String quantum) {
        hbox.getChildren().clear();
        processedItems = new ArrayList<>();
        this.quantum = quantum;
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

    private void FCFS()
    {
        hbox.getChildren().clear();
        currentTimeCounter = 0;
        tableView.getItems().sort(new SortByFCFS());
        for (GUIProcess s : processList)
        {   s.setStartTime(currentTimeCounter);
            hbox.getChildren().add(processFactory(s,Integer.parseInt(s.getBurst()), currentTimeCounter,totalTime));
            currentTimeCounter += Integer.parseInt(s.getBurst());
            s.setEndTime(currentTimeCounter);
            processedItems.add(s);

            tableView2.getItems().add(s);
        }
        hbox.getChildren().add(rightEdge(currentTimeCounter));
        avgWaitingTimeText.setText(avgWaitingNonPre());
        avgTurnAroundText.setText(avgTurnaroundNonPre());
    }

    private void SJF()
    {
        hbox.getChildren().clear();

        currentTimeCounter = 0;
        tableView.getItems().sort(new SortBySJF_NP(currentTimeCounter));
        while (!tableView.getItems().isEmpty())
        {
            GUIProcess current = tableView.getItems().get(0);
            current.setStartTime(currentTimeCounter);
            hbox.getChildren().add(processFactory(current,Integer.parseInt(current.getBurst()), currentTimeCounter,totalTime));
            currentTimeCounter += Integer.parseInt(current.getBurst());
            current.setEndTime(currentTimeCounter);
            tableView2.getItems().add(current);
            tableView.getItems().remove(current);
            tableView.getItems().sort(new SortBySJF_NP(currentTimeCounter));
            processedItems.add(current);
        }
        hbox.getChildren().add(rightEdge(currentTimeCounter));
        avgWaitingTimeText.setText(avgWaitingNonPre());
        avgTurnAroundText.setText(avgTurnaroundNonPre());
    }

    private void SJF_P()
    {
        arr = new ArrayList<>(tableView.getItems().size());
        resultt = new ArrayList<>(tableView.getItems().size());

        ObservableList<GUIProcess> list = tableView.getItems();
        for(var item : list){
            arr.add(new Process(item.getPid(), item.getArrivalTimeInt(),item.getBurstInt(),Integer.parseInt(item.getPriority())));
        }


        resultt = modify(SJP_PREE(arr,false));


        for(var current : resultt){
            hbox.getChildren().add(processFactory(toGUIProcess(current),current.end-current.start, current.start,totalTime));

        }
        hbox.getChildren().add(rightEdge(resultt.get(resultt.size()-1).end));


    }
    private void roundRobin()
    {

    }
    private void priority()
    {
        hbox.getChildren().clear();

        currentTimeCounter = 0;
        tableView.getItems().sort(new SortByPriority_NP(currentTimeCounter));
        while (!tableView.getItems().isEmpty())
        {
            GUIProcess current = tableView.getItems().get(0);
            current.setStartTime(currentTimeCounter);
            hbox.getChildren().add(processFactory(current,Integer.parseInt(current.getBurst()), currentTimeCounter,totalTime));
            currentTimeCounter += Integer.parseInt(current.getBurst());
            current.setEndTime(currentTimeCounter);
            tableView.getItems().remove(current);
            tableView2.getItems().add(current);
            tableView.getItems().sort(new SortByPriority_NP(currentTimeCounter));
            processedItems.add(current);

        }
        hbox.getChildren().add(rightEdge(currentTimeCounter));
        avgWaitingTimeText.setText(avgWaitingNonPre());
        avgTurnAroundText.setText(avgTurnaroundNonPre());
    }
    private void priority_P()
    {
        arr = new ArrayList<>(tableView.getItems().size());
        resultt = new ArrayList<>(tableView.getItems().size());

        ObservableList<GUIProcess> list = tableView.getItems();
        for(var item : list){
            arr.add(new Process(item.getPid(), item.getArrivalTimeInt(),item.getBurstInt(),Integer.parseInt(item.getPriority())));
        }


        resultt = modify(SJP_PREE(arr,true));


        for(var current : resultt){
            hbox.getChildren().add(processFactory(toGUIProcess(current),current.end-current.start, current.start,totalTime));

        }
        hbox.getChildren().add(rightEdge(resultt.get(resultt.size()-1).end));
    }

    private String avgWaitingNonPre()
    {
        double sum = 0;
        for(GUIProcess s : processedItems)
        {
            sum += (s.getStartTime()-s.getArrivalTimeInt());
        }
        sum /= processedItems.size();
        sum = Math.round(sum*100);
        sum /=100;
        return Double.toString(sum);
    }

    private String avgTurnaroundNonPre()
    {
        double sum = 0;
        for(GUIProcess s : processedItems)
        {
            sum += (s.getEndTime()-s.getArrivalTimeInt() );
        }
        sum /= processedItems.size();
        sum = Math.round(sum*100);
        sum /=100;
        return Double.toString(sum);

    }




}