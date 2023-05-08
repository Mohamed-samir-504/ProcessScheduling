package com.collegegroup.processscheduling.GUIControllers;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.collegegroup.processscheduling.GUIProcess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;


public class RoundRobinController implements Initializable {
    //configure the table
    @FXML private TableView<GUIProcess> tableView;
    @FXML private TableColumn<GUIProcess, String> pidColumn;
    @FXML private TableColumn<GUIProcess, String> burstColumn;
    @FXML private TableColumn<GUIProcess, String> arrivalTimeColumn;
    @FXML private CheckBox preemptive, live;
    String quantum = "1";

    //These instance variables are used to create new Person objects
    @FXML private TextField pidTextField;
    @FXML private TextField burstTextField;
    @FXML private TextField arrivalTimeTextField;
    @FXML private TextField quantumTextField;
    Stage stage;
    Scene scene;
    Parent root;
    int sum = 0;


    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        //set up the columns in the table
        pidColumn.setCellValueFactory(new PropertyValueFactory<GUIProcess, String>("pid"));
        burstColumn.setCellValueFactory(new PropertyValueFactory<GUIProcess, String>("burst"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<GUIProcess, String>("arrivalTime"));
        //load dummy data
        //tableView.setItems(lol());
        //Set rows to be Editable
        tableView.setEditable(true);
        pidColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        burstColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        arrivalTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        //select multiple rows
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        quantumTextField.setText("1");

    }
    @FXML
    public void insertButtonPushed()
    {
        GUIProcess newProcess = new GUIProcess(pidTextField.getText(),burstTextField.getText(),arrivalTimeTextField.getText());
        if(newProcess.isNotEmpty() && newProcess.isValid()) {
            tableView.getItems().add(newProcess);
            sum+=Integer.parseInt(burstTextField.getText());
        }

    }
    @FXML
    public void deleteButtonPushed() {
        ObservableList<GUIProcess> allProcesses, selected;
        allProcesses = tableView.getItems();
        selected = tableView.getSelectionModel().getSelectedItems();

        for (GUIProcess GUIprocess : selected) {
            allProcesses.remove(GUIprocess);
            sum-=Integer.parseInt(GUIprocess.getBurst());
        }
    }

    @FXML
    public void drawButtonPushed(ActionEvent event) throws IOException {
        Stage stage;
        Scene scene;
        Parent root;
        if(live.isSelected())
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LiveGanttChart.fxml"));
            root = loader.load();
            LiveGanttChartController gc = loader.getController();
            gc.init(tableView.getItems(), sum,"RoundRobin",quantumTextField.getText());

        }
        else
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GanttChart.fxml"));
            root = loader.load();
            GanttChartController gc = loader.getController();
            gc.init(tableView.getItems(), sum, "RoundRobin", quantumTextField.getText());

        }
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    //Create dummy data (to be removed)
    @FXML
    private ObservableList<GUIProcess> lol() {
        // ArrayList but for GUI
        ObservableList<GUIProcess> gg = FXCollections.observableArrayList();
        gg.add(new GUIProcess("P3","10","0"));
        gg.add(new GUIProcess("P1","3","0"));
        gg.add(new GUIProcess("P2","1","2"));
        gg.add(new GUIProcess("P0","5","3"));
        gg.add(new GUIProcess("P4","2","3"));

        //1 3 1
        sum = 24;
        return gg;
    }
    public void init(ObservableList<GUIProcess> arr)
    {

        tableView.setItems(arr);
    }
}
