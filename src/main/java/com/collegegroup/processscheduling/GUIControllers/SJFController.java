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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SJFController implements Initializable {

    //configure the table
    @FXML private TableView<GUIProcess> tableView;
    @FXML private TableColumn<GUIProcess, String> pidColumn;
    @FXML private TableColumn<GUIProcess, String> burstColumn;
    @FXML private TableColumn<GUIProcess, String> arrivalTimeColumn;
    @FXML
    FlowPane hbox;
    //These instance variables are used to create new Person objects
    @FXML private TextField pidTextField;
    @FXML private TextField burstTextField;
    @FXML private TextField arrivalTimeTextField;
    int time = 0;
    int sum = 0;
    Stage stage;
    Scene scene;
    Parent root;



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
        gg.add(new GUIProcess("3","5","4"));
        gg.add(new GUIProcess("55","5","4"));
        sum = 255;
        return gg;
    }

    VBox processFactory(GUIProcess s)
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
