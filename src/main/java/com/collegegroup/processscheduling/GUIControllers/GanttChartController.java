package com.collegegroup.processscheduling.GUIControllers;

import com.collegegroup.processscheduling.GUIProcess;
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

public class GanttChartController {
    ObservableList<GUIProcess> processList;

    @FXML HBox hbox;
    private int currentTime = 0, totalTime = 0;
    @FXML private TableColumn<GUIProcess, String> pidColumn;
    @FXML private TableColumn<GUIProcess, String> burstColumn;
    @FXML private TableColumn<GUIProcess, String> arrivalTimeColumn;
    @FXML private TableView<GUIProcess> tableView;


    public  VBox processFactory(GUIProcess s)
    {
        int width = Integer.parseInt(s.getBurst());
        String name = s.getPid();
        Rectangle newProcess = new Rectangle((double) (width * 800) /totalTime + 20,50);
        Text text1 = new Text(name);
        //create a stack pane to stack the rectangle and text on top of each other
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
    public void onClickDraw(ActionEvent ignoredEvent) {
        hbox.getChildren().clear(); currentTime = 0;
        for (GUIProcess s : processList) hbox.getChildren().add(processFactory(s));
        hbox.getChildren().add(rightEdge());
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