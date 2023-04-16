package com.collegegroup.processscheduling.StaticControllers;

import com.collegegroup.processscheduling.Processes.Processs;
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

public class GanttChart {
    ObservableList<Processs> processList;
    @FXML
    HBox hbox;
    private int currentTime = 0, totalTime = 0;
    @FXML private TableColumn<Processs, String> pidColumn;
    @FXML private TableColumn<Processs, String> burstColumn;
    @FXML private TableColumn<Processs, String> arrivalTimeColumn;
    @FXML private TableView<Processs> tableView;


    @FXML
    public  VBox processFactory(Processs s)
    {
        int width = Integer.parseInt(s.getBurst());
        String name = s.getPid();
        Rectangle newProcess = new Rectangle(width*500/totalTime + 10,50);
        Text text1 = new Text(name);
        //create a stackpane to stack the rectangle and text on top of each other
        StackPane stack = new StackPane();
        stack.getChildren().addAll(newProcess, text1);
        //set the rectangle to be transparent and add a stroke
        newProcess.setFill(Color.TRANSPARENT);
        newProcess.setStroke(Color.BLACK);
        //create a Vbox to add the burst time on the bottom left
        VBox vBox = new VBox();
        vBox.getChildren().add(stack);
        Text text2 = new Text(Integer.toString(currentTime));
        vBox.getChildren().add(text2);
        //shift the burst time a couple of pixels to account for stroke width
        text2.setTranslateX(text2.getX()-4);
        //increment the timer
        currentTime += Integer.parseInt(s.getBurst());
        return vBox;
    }

    @FXML
    public void onClickDraw(ActionEvent event) {
        hbox.getChildren().clear();
        currentTime = 0;
        for (Processs s : processList) {
            hbox.getChildren().add(processFactory(s));
        }
        hbox.getChildren().add(rightEdge());
    }
    //initialize the scene
    //reason : can't create constructor cuz FXMLLoader requires an empty constructor to instantiate an object
    //of the controller class.
    public void init(ObservableList<Processs> x, int time)
    {
        //get the process list and the current time
        processList = x;
        totalTime = time;
        //set the columns of the table to read correct attributes
        pidColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));
        burstColumn.setCellValueFactory(new PropertyValueFactory<>("burst"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        //fill the table with the list saved from the last scene.
        tableView.setItems(processList);
    }
    //add the last burst time in the table (cosmetic)
    private VBox rightEdge()
    {
        VBox vBox = new VBox();
        Rectangle empty = new Rectangle(0,50);
        Text last = new Text(Integer.toString(currentTime));
        last.setTranslateX(last.getX()-6);
        vBox.getChildren().add(empty);
        vBox.getChildren().add(last);
        return vBox;

    }









}