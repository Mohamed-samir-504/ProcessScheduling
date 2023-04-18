package com.collegegroup.processscheduling.GUIControllers;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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


public class PriorityController implements Initializable {

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
    int time = 0;
    int sum = 0;
    Stage stage;
    Scene scene;
    Parent root;


    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        //set up the columns in the table
        pidColumn.setCellValueFactory(new PropertyValueFactory<GUIProcess, String>("pid"));
        burstColumn.setCellValueFactory(new PropertyValueFactory<GUIProcess, String>("burst"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<GUIProcess, String>("arrivalTime"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<GUIProcess, String>("priority"));
        //load dummy data
        tableView.setItems(lol());
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
        gg.add(new GUIProcess("3","53","4","5"));
        gg.add(new GUIProcess("55","54","4","7"));
        gg.add(new GUIProcess("45","45","4","3"));
        gg.add(new GUIProcess("565","200","4","1"));
        sum = 53+53+45+200;
        return gg;
    }
    @FXML
    public void onDrawClick(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("GanttChart.fxml"));
        root = loader.load();
        GanttChartController gc = loader.getController();
        //Data must be sorted here before being passed
        if(preemptive.isSelected())
        {
            //TODO()
        }
        else tableView.getItems().sort(new SortByPriority_NP());


        gc.init(tableView.getItems(), sum);
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

}





