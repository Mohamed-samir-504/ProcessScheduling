package com.collegegroup.processscheduling;

import com.collegegroup.processscheduling.Processes.Processs;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class GanttChartController implements Initializable {
    ObservableList<Processs> processList;
@FXML
  public  void getProcessList(ObservableList<Processs> x)
    {
        processList = x;
    }
@FXML
  public  StackPane processFactory(Processs s)
    {
        int Width = Integer.parseInt(s.getBurst());

        String name = s.getPid();

        Rectangle newProcess = new Rectangle(40,50);

        Text text1 = new Text(name);

        StackPane stack = new StackPane();

        stack.getChildren().addAll(newProcess, text1);



//        newProcess.setFill(Color.TRANSPARENT);
//        newProcess.setStroke(Color.BLACK);
//
//        VBox vBox = new VBox();
//        vBox.getChildren().add(stack);
//        Text text2 = new Text(Integer.toString(time));
//        vBox.getChildren().add(text2);
//        time+= Integer.parseInt(s.getBurst());
        return stack;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (Processs s : processList) {
           // hbox.getChildren().add(processFactory(s));
        }

    }
}
