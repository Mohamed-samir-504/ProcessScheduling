package com.collegegroup.processscheduling.GUIControllers;

import com.collegegroup.processscheduling.Comparators.SortByFCFS;
import com.collegegroup.processscheduling.Comparators.SortByPriority_NP;
import com.collegegroup.processscheduling.Comparators.SortBySJF_NP;
import com.collegegroup.processscheduling.GUIProcess;
import com.collegegroup.processscheduling.Process;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

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



    @FXML private TableView<GUIProcess> tableView;


    @FXML private Text avgTurnAroundText,avgWaitingTimeText;
    private String mode,quantum;
    private int currentTimeCounter;
    private ArrayList<Process>arr;
    ArrayList<Process> resultt;
    ArrayList<GUIProcess> processedItems;
    String flag;


    @FXML
    public void onClickDraw(ActionEvent ignoredEvent) {
        currentTimeCounter = 0;
        hbox.getChildren().clear();
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
    public void init(ObservableList<GUIProcess> x, int time, String mode)
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
        arr = new ArrayList<Process>();
        resultt = new ArrayList<Process>();
        processedItems = new ArrayList<>();
    }

    public void init(ObservableList<GUIProcess> items, int sum, String mode , String quantum)
    {
        this.quantum = quantum;
        init(items,sum,mode);
    }

    private void FCFS()
    {
        hbox.getChildren().clear();
        currentTimeCounter = 0;
        int st = 0;
        tableView.getItems().sort(new SortByFCFS());
        int idle = 0;
        for (int i = 0; i<processList.size(); i++)
        {
            var s = processList.get(i);
            st = currentTimeCounter;
            while(currentTimeCounter<s.getArrivalTimeInt()) {
                currentTimeCounter++; idle++;
            }

            if(idle>0)
            {
                hbox.getChildren().add(idleProcessFactory(idle,st,totalTime));
                idle = 0;
            }

            s.setStartTime(currentTimeCounter);
            hbox.getChildren().add(processFactory(s,Integer.parseInt(s.getBurst()), currentTimeCounter,totalTime));

            currentTimeCounter += Integer.parseInt(s.getBurst());
            s.setEndTime(currentTimeCounter);
            processedItems.add(s);
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

            int st = currentTimeCounter, idle = 0;

            while(currentTimeCounter<current.getArrivalTimeInt()) {
                currentTimeCounter++; idle++;
            }

            if(idle>0)
            {
                hbox.getChildren().add(idleProcessFactory(idle,st,totalTime));
            }

            current.setStartTime(currentTimeCounter);
            hbox.getChildren().add(processFactory(current,Integer.parseInt(current.getBurst()), currentTimeCounter,totalTime));
            currentTimeCounter += Integer.parseInt(current.getBurst());
            current.setEndTime(currentTimeCounter);
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
        flag = "Pre";
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
        avgWaitingTimeText.setText(avgWaitingPre());
        avgTurnAroundText.setText(avgTurnAroundPre());


    }
    private void roundRobin()
    {
        flag = "RR";
        arr = new ArrayList<>(tableView.getItems().size());
        resultt = new ArrayList<>(tableView.getItems().size());

        ObservableList<GUIProcess> list = tableView.getItems();
        for(var item : list){
            arr.add(new Process(item.getPid(), item.getArrivalTimeInt(),item.getBurstInt(),Integer.parseInt(item.getPriority())));
        }


        resultt = round_robin(arr,Integer.parseInt(quantum));


        for(var current : resultt){

            hbox.getChildren().add(processFactory(toGUIProcess(current),current.end-current.start, current.start,totalTime));

        }
        hbox.getChildren().add(rightEdge(resultt.get(resultt.size()-1).end));
        avgWaitingTimeText.setText(avgWaitingPre());
        avgTurnAroundText.setText(avgTurnAroundPre());
    }
    private void priority()
    {
        hbox.getChildren().clear();

        currentTimeCounter = 0;
        tableView.getItems().sort(new SortByPriority_NP(currentTimeCounter));
        while (!tableView.getItems().isEmpty())
        {
            GUIProcess current = tableView.getItems().get(0);

            int st = currentTimeCounter, idle = 0;

            while(currentTimeCounter<current.getArrivalTimeInt()) {
                currentTimeCounter++; idle++;
            }

            if(idle>0)
            {
                hbox.getChildren().add(idleProcessFactory(idle,st,totalTime));
            }



            current.setStartTime(currentTimeCounter);
            hbox.getChildren().add(processFactory(current,Integer.parseInt(current.getBurst()), currentTimeCounter,totalTime));
            currentTimeCounter += Integer.parseInt(current.getBurst());
            current.setEndTime(currentTimeCounter);
            tableView.getItems().remove(current);
            tableView.getItems().sort(new SortByPriority_NP(currentTimeCounter));
            processedItems.add(current);

        }
        hbox.getChildren().add(rightEdge(currentTimeCounter));
        avgWaitingTimeText.setText(avgWaitingNonPre());
        avgTurnAroundText.setText(avgTurnaroundNonPre());
    }
    private void priority_P()
    {
        flag = "Pre";
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
        avgWaitingTimeText.setText(avgWaitingPre());
        avgTurnAroundText.setText(avgTurnAroundPre());
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

    private String avgWaitingPre(){
        double sum = 0;
        int n = 0;
        for(Process s : resultt)
        {
            if(Objects.equals(flag, "Pre")){
                if(s.burst == 1){
                    sum+=(s.end-s.originalArrivalTime-s.originalBurstTime);
                    n++;
                }
            }
            if(Objects.equals(flag, "RR")){
                if(s.burst == 0){
                    sum+=(s.end-s.originalArrivalTime-s.originalBurstTime);
                    n++;
                }
            }

        }

        sum /= n;
        sum = Math.round(sum*100);
        sum /=100;
        return Double.toString(sum);
    }

    private String avgTurnAroundPre(){
        double sum = 0;
        int n = 0;
        for(Process s : resultt)
        {
            if(Objects.equals(flag, "Pre")){
                if(s.burst == 1){
                    sum+=(s.end-s.originalArrivalTime);
                    n++;
                }
            }
            if(Objects.equals(flag, "RR")){
                if(s.burst == 0){
                    sum+=(s.end-s.originalArrivalTime);
                    n++;
                }
            }

        }

        sum /= n;
        sum = Math.round(sum*100);
        sum /=100;
        return Double.toString(sum);
    }


    @FXML
    public void back(ActionEvent ignoredEvent) throws IOException {

        if(mode.equals("FCFS"))
        {
            Stage stage;
            Scene scene;
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FCFS.fxml"));
            root = loader.load();
            FCFSController controller = loader.getController();
            controller.init(tableView.getItems());
            stage = (Stage)((Node) ignoredEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        if(mode.equals("SJF") || mode.equals("SJF_P"))
        {
            Stage stage;
            Scene scene;
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SJF.fxml"));
            root = loader.load();
            SJFController controller = loader.getController();
            controller.init(tableView.getItems());
            stage = (Stage)((Node) ignoredEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        }

        if(mode.equals("Priority") || mode.equals("Priority_P"))
        {
            Stage stage;
            Scene scene;
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Priority.fxml"));
            root = loader.load();
            PriorityController controller = loader.getController();
            controller.init(tableView.getItems());
            stage = (Stage)((Node) ignoredEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }

        if(mode.equals("RoundRobin"))
        {
            Stage stage;
            Scene scene;
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RoundRobin.fxml"));
            root = loader.load();
            RoundRobinController controller = loader.getController();
            controller.init(tableView.getItems());
            stage = (Stage)((Node) ignoredEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
            
        
        
    }
    }



