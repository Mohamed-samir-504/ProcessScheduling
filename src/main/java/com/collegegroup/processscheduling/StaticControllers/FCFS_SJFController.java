package com.collegegroup.processscheduling.StaticControllers;

import com.collegegroup.processscheduling.Processes.PriorityProcess;
import com.collegegroup.processscheduling.Processes.Processs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;

import java.net.URL;
import java.util.ResourceBundle;

public class FCFS_SJFController implements Initializable {

    //configure the table
    @FXML private TableView<Processs> tableView;
    @FXML private TableColumn<Processs, String> pidColumn;
    @FXML private TableColumn<Processs, String> burstColumn;
    @FXML private TableColumn<Processs, String> arrivalTimeColumn;
    @FXML
    FlowPane hbox;
    //These instance variables are used to create new Person objects
    @FXML private TextField pidTextField;
    @FXML private TextField burstTextField;
    @FXML private TextField arrivalTimeTextField;
    int time = 0;
    int sum = 0;



    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        //set up the columns in the table
        pidColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));
        burstColumn.setCellValueFactory(new PropertyValueFactory<>("burst"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        //load dummy data
        tableView.setItems(lol());
        //Set rows to be Editable
        tableView.setEditable(true);
        pidColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        burstColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        arrivalTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        //select multiple rows
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }
    @FXML
    public void insertButtonPushed()
    {
        Processs newProcess = new Processs(pidTextField.getText(),burstTextField.getText(),arrivalTimeTextField.getText());

        if(!newProcess.isEmpty() && newProcess.isValid()) {
            tableView.getItems().add(newProcess);
            sum+=Integer.parseInt(burstTextField.getText());
        }
    }
    @FXML
    public void deleteButtonPushed() {
        ObservableList<Processs> allProcesses, selected;
        allProcesses = tableView.getItems();
        selected = tableView.getSelectionModel().getSelectedItems();

            for (Processs processs : selected) {
                allProcesses.remove(processs);
                sum-=Integer.parseInt(processs.getBurst());
            }
    }

    //Create dummy data (to be removed)
    @FXML
    private ObservableList<Processs> lol() {
        // ArrayList but for GUI (sho5a5)
        ObservableList<Processs> gg = FXCollections.observableArrayList();
        gg.add(new Processs("3","5","4"));
        gg.add(new Processs("55","5","4"));
        gg.add(new PriorityProcess("45","45","4"));
        gg.add(new PriorityProcess("565","200","4"));
        sum = 255;
        return gg;
    }

    VBox processFactory(Processs s)
    {
        int Width = Integer.parseInt(s.getBurst());

        String name = s.getPid();

        Rectangle newProcess = new Rectangle(Width*tableView.getWidth()/sum,50);

        Text text1 = new Text(name);

        StackPane stack = new StackPane();

        stack.getChildren().addAll(newProcess, text1);



        newProcess.setFill(Color.TRANSPARENT);
        newProcess.setStroke(Color.BLACK);

        VBox vBox = new VBox();
        vBox.getChildren().add(stack);
        Text text2 = new Text(Integer.toString(time));
        vBox.getChildren().add(text2);
        time+= Integer.parseInt(s.getBurst());
        return vBox;
    }

    @FXML
    public void onDrawClick(ActionEvent e)
    {
        hbox.getChildren().clear();

        for (Processs s : tableView.getItems()) {
            hbox.getChildren().add(processFactory(s));
        }




    }








}
