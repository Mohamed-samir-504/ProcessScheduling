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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FCFSController implements Initializable {

    //configure the table
    @FXML private TableView<GUIProcess> tableView;
    @FXML private TableColumn<GUIProcess, String> pidColumn;
    @FXML private TableColumn<GUIProcess, String> burstColumn;
    @FXML private TableColumn<GUIProcess, String> arrivalTimeColumn;
    //These instance variables are used to create new Person objects
    @FXML private TextField pidTextField;
    @FXML private TextField burstTextField;
    @FXML private TextField arrivalTimeTextField;
    Stage stage;
    Scene scene;
    Parent root;
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
        GUIProcess newProcess = new GUIProcess(pidTextField.getText(),burstTextField.getText(),arrivalTimeTextField.getText());

        if(newProcess.isEmpty() && newProcess.isValid()) {
            tableView.getItems().add(newProcess);
            sum+=Integer.parseInt(burstTextField.getText());
        }
    }
    @FXML
    public void deleteButtonPushed() {
        ObservableList<GUIProcess> allProcesses, selected;
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
        gg.add(new GUIProcess("P1","5","4"));
        gg.add(new GUIProcess("P2","5","4"));
        sum = 255;
        return gg;
    }



    @FXML
    public void onDrawClick(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("GanttChart.fxml"));
        root = loader.load();
        GanttChartController gc = loader.getController();
        //Data must be sorted here before being passed
        //TODO()
        gc.init(tableView.getItems(), sum);
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }








}
