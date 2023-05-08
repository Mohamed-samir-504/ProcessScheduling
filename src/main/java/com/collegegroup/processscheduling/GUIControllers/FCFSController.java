package com.collegegroup.processscheduling.GUIControllers;

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

public class FCFSController implements Initializable {

    @FXML private TableView<GUIProcess> tableView;
    @FXML private TableColumn<GUIProcess, String> pidColumn,burstColumn,arrivalTimeColumn;
    @FXML private TextField pidTextField,burstTextField,arrivalTimeTextField;
    @FXML private CheckBox live;
    double sum = 0;




    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        //set up the columns in the table
        pidColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));
        burstColumn.setCellValueFactory(new PropertyValueFactory<>("burst"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        //load dummy data
        //tableView.setItems(lol());
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
        GUIProcess newProcess = new GUIProcess(pidTextField.getText(),burstTextField.getText(),arrivalTimeTextField.getText());

        if(newProcess.isNotEmpty() && newProcess.isValid()) {
            tableView.getItems().add(newProcess);
            sum+=Double.parseDouble(burstTextField.getText());
        }
    }
    @FXML
    public void deleteButtonPushed() {
        ObservableList<GUIProcess> allProcesses, selected;
        allProcesses = tableView.getItems();
        selected = tableView.getSelectionModel().getSelectedItems();

            for (GUIProcess GUIProcess : selected) {
                allProcesses.remove(GUIProcess);
                sum-=Double.parseDouble(GUIProcess.getBurst());
            }
    }

    //Create dummy data (to be removed)
    @FXML
    private ObservableList<GUIProcess> lol() {
        // ArrayList but for GUI
        ObservableList<GUIProcess> gg = FXCollections.observableArrayList();
        gg.add(new GUIProcess("P0","3","0"));
        gg.add(new GUIProcess("P1","4","3"));
        gg.add(new GUIProcess("P2","2","4"));
        gg.add(new GUIProcess("P3","8","2"));
        gg.add(new GUIProcess("P4","3","8"));
        gg.add(new GUIProcess("P5","5","6"));
        sum = 3+4+2+8+3+5;
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
            gc.init(tableView.getItems(), sum,"FCFS");
        }
        else
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GanttChart.fxml"));
            root = loader.load();
            GanttChartController gc = loader.getController();
            gc.init(tableView.getItems(), sum,"FCFS");
        }


        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
