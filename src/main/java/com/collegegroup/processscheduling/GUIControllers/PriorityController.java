package com.collegegroup.processscheduling.GUIControllers;

import com.collegegroup.processscheduling.Comparators.SortByPriority_NP;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


@SuppressWarnings("ALL")
public class PriorityController implements Initializable{

    //configure the table
    @FXML private TableView<GUIProcess> tableView;
    @FXML private TableColumn<GUIProcess, String> pidColumn;
    @FXML private TableColumn<GUIProcess, String> burstColumn;
    @FXML private TableColumn<GUIProcess, String> arrivalTimeColumn;
    @FXML private TableColumn<GUIProcess, String> priorityColumn;
    @FXML private CheckBox preemptive;

    //These instance variables are used to create new Person objects
    @FXML private TextField pidTextField;
    @FXML private TextField burstTextField;
    @FXML private TextField arrivalTimeTextField;
    @FXML private TextField priorityTextField;
    @FXML private CheckBox live;
    int sum = 0;
    Stage stage;
    Scene scene;
    Parent root;



    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        //set up the columns in the table
        pidColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));
        burstColumn.setCellValueFactory(new PropertyValueFactory<>("burst"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
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
        GUIProcess newProcess = new GUIProcess(pidTextField.getText(),burstTextField.getText(),arrivalTimeTextField.getText(),priorityTextField.getText());

        if(newProcess.isEmpty() && newProcess.isValid()) {
            tableView.getItems().add(newProcess);
            sum+=Integer.parseInt(burstTextField.getText());
        }
    }
    @FXML
    public void deleteButtonPushed() {
        ObservableList<GUIProcess> allProcesses;
        ObservableList<GUIProcess> selected;
        allProcesses = tableView.getItems();
        selected = tableView.getSelectionModel().getSelectedItems();

        for (GUIProcess GUIProcess : selected) {
            allProcesses.remove(GUIProcess);
            sum-=Integer.parseInt(GUIProcess.getBurst());
        }
    }

    //Create dummy data (to be removed)
    @FXML
    private ObservableList<GUIProcess> lol() {
        // ArrayList but for GUI (sho5a5)
        ObservableList<GUIProcess> gg = FXCollections.observableArrayList();
        gg.add(new GUIProcess("1","1","0","2"));
        gg.add(new GUIProcess("2","4","0","1"));
        gg.add(new GUIProcess("3","2","1","1"));
        gg.add(new GUIProcess("4","3","1","3"));
        sum = 1+4+2+3;
        return gg;
    }
    @FXML
    public void onDrawClick(ActionEvent event) throws IOException {

        Stage stage;
        Scene scene;
        Parent root;
        if(live.isSelected())
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LiveGanttChart.fxml"));
            root = loader.load();
            LiveGanttChartController gc = loader.getController();
            gc.init(tableView.getItems(), sum,"Priority");
        }
        else
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GanttChart.fxml"));
            root = loader.load();
            GanttChartController gc = loader.getController();
            gc.init(tableView.getItems(), sum,"Priority");
        }


        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

}





