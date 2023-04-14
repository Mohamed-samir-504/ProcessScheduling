package com.collegegroup.processscheduling.StaticControllers;
import java.net.URL;
import java.util.ResourceBundle;

import com.collegegroup.processscheduling.Processes.PriorityProcess;
import com.collegegroup.processscheduling.Processes.Processs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 * FXML Controller class
 *
 * @author jwright
 */
public class PriorityController implements Initializable {

    //configure the table
    @FXML private TableView<PriorityProcess> tableView;
    @FXML private TableColumn<PriorityProcess, String> pidColumn;
    @FXML private TableColumn<PriorityProcess, String> burstColumn;
    @FXML private TableColumn<PriorityProcess, String> arrivalTimeColumn;
    @FXML private TableColumn<PriorityProcess, String> priorityColumn;

    //These instance variables are used to create new Person objects
    @FXML private TextField pidTextField;
    @FXML private TextField burstTextField;
    @FXML private TextField arrivalTimeTextField;
    @FXML private TextField priorityTextField;


    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        //set up the columns in the table
        pidColumn.setCellValueFactory(new PropertyValueFactory<PriorityProcess, String>("pid"));
        burstColumn.setCellValueFactory(new PropertyValueFactory<PriorityProcess, String>("burst"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<PriorityProcess, String>("arrivalTime"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<PriorityProcess, String>("priority"));
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
        PriorityProcess newProcess = new PriorityProcess(pidTextField.getText(),burstTextField.getText(),arrivalTimeTextField.getText(),priorityTextField.getText());
        if(!newProcess.isEmpty() && newProcess.isValid()) {
            tableView.getItems().add(newProcess);
        }
    }
    @FXML
    public void deleteButtonPushed() {
        ObservableList<PriorityProcess> allProcesses, selected;
        allProcesses = tableView.getItems();
        selected = tableView.getSelectionModel().getSelectedItems();

        for (PriorityProcess process : selected) {
            allProcesses.remove(process);
        }
    }


    //Create dummy data (to be removed)
    @FXML
    private ObservableList<PriorityProcess> lol() {
        // ArrayList but for GUI (sho5a5)
        ObservableList<PriorityProcess> gg = FXCollections.observableArrayList();
        gg.add(new PriorityProcess("3","5","4","5"));
        gg.add(new PriorityProcess("55","5","4","3"));
        return gg;
    }
}
