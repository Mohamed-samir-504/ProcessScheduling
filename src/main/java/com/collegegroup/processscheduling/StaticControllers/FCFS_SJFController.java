package com.collegegroup.processscheduling.StaticControllers;
import java.net.URL;
import java.util.ResourceBundle;

import com.collegegroup.processscheduling.Processes.Processs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author jwright
 */
public class FCFS_SJFController implements Initializable {

    //configure the table
    @FXML private TableView<Processs> tableView;
    @FXML private TableColumn<Processs, String> pidColumn;
    @FXML private TableColumn<Processs, String> burstColumn;
    @FXML private TableColumn<Processs, String> arrivalTimeColumn;
    @FXML HBox hbox = new HBox();
    //These instance variables are used to create new Person objects
    @FXML private TextField pidTextField;
    @FXML private TextField burstTextField;
    @FXML private TextField arrivalTimeTextField;



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
        hbox.setAlignment(Pos.CENTER);
        hbox.setMaxWidth(100);
        hbox.setFillHeight(true);

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

    //Create dummy data (to be removed)
    @FXML
    private ObservableList<Processs> lol() {
        // ArrayList but for GUI (sho5a5)
        ObservableList<Processs> gg = FXCollections.observableArrayList();
        gg.add(new Processs("3","5","4"));
        gg.add(new Processs("55","5","4"));
        return gg;
    }

    StackPane processFactory(Processs s)
    {
        int Width = Integer.parseInt(s.getBurst());
        String name = s.getPid();
        Rectangle newProcess = new Rectangle(Width*5,50);
        Text text = new Text(name);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(newProcess, text);
        newProcess.setFill(Color.TRANSPARENT);
        newProcess.setStroke(Color.BLACK);
        return stack;
    }
    @FXML
    public void onDrawClick(ActionEvent e)
    {
        hbox.getChildren().clear();
        for (Processs s : tableView.getItems()) {
            StackPane x = processFactory(s);
            hbox.getChildren().add(x);
            x.setMaxHeight(200);
            x.setMaxWidth(300);
            HBox.setHgrow(x, Priority.ALWAYS);
        }
    }








}
