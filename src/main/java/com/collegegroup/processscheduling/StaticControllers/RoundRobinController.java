package com.collegegroup.processscheduling.StaticControllers;
import java.net.URL;
import java.util.ResourceBundle;

import com.collegegroup.processscheduling.Processes.Processs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;


public class RoundRobinController implements Initializable {
    //configure the table
    @FXML private TableView<Processs> tableView;
    @FXML private TableColumn<Processs, String> pidColumn;
    @FXML private TableColumn<Processs, String> burstColumn;
    @FXML private TableColumn<Processs, String> arrivalTimeColumn;
    String quantum;


    //These instance variables are used to create new Person objects
    @FXML private TextField pidTextField;
    @FXML private TextField burstTextField;
    @FXML private TextField arrivalTimeTextField;
    @FXML private TextField quantumTextField;


    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        //set up the columns in the table
        pidColumn.setCellValueFactory(new PropertyValueFactory<Processs, String>("pid"));
        burstColumn.setCellValueFactory(new PropertyValueFactory<Processs, String>("burst"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<Processs, String>("arrivalTime"));
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
        }
    }
    @FXML
    public void deleteButtonPushed() {
        ObservableList<Processs> allProcesses, selected;
        allProcesses = tableView.getItems();
        selected = tableView.getSelectionModel().getSelectedItems();

        for (Processs processs : selected) {
            allProcesses.remove(processs);
        }
    }

    //TODO()
    @FXML
    public void drawButtonPushed()
    {
        quantum = quantumTextField.getText();
    }


    //Create dummy data (to be removed)
    @FXML
    private ObservableList<Processs> lol() {
        // ArrayList but for GUI (sho5a5)
        ObservableList<Processs> gg = FXCollections.observableArrayList();
        gg.add(new Processs("3","5","4"));
        gg.add(new Processs("55","5","4"));
        return gg;
    }
}
