package com.collegegroup.processscheduling;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public abstract class util {

    public static Process toProcess(GUIProcess guiProcess)
    {
        int arrival,burst,priority;
        String pid;
        pid = guiProcess.getPid();
        arrival = Integer.parseInt(guiProcess.getArrivalTime());
        burst = Integer.parseInt(guiProcess.getBurst());
        priority = Integer.parseInt(guiProcess.getPriority());
        return new Process(pid,arrival,burst,priority);
    }
    public static GUIProcess toGUIProcess(Process process)
    {
        return new GUIProcess(process.getPid(),
                Integer.toString(process.getBurst()),
                Integer.toString(process.getArrival()),
                Integer.toString(process.getPriority()));
    }

    public static VBox processFactory(GUIProcess s, int width, int currentTime , int totalTime)
    {
        String name = s.getPid();
        Rectangle newProcess = new Rectangle((double) (width * 800) /totalTime + 20,50);
        Text text1 = new Text(name);
        //create a stack pane to stack the rectangle and text on top of each other
        StackPane stack = new StackPane();
        stack.getChildren().addAll(newProcess, text1);
        //set the rectangle to be transparent and add a stroke
        newProcess.setFill(Color.TRANSPARENT);
        newProcess.setStroke(Color.BLACK);
        //create a Vbox to add the burst time on the bottom left
        VBox vBox = new VBox();
        vBox.getChildren().add(stack);
        Text text2 = new Text(Integer.toString(currentTime));
        vBox.getChildren().add(text2);
        //shift the burst time a couple of pixels to account for stroke width
        text2.setTranslateX(text2.getX()-4);
        //increment the timer
        return vBox;
    }

    public static VBox liveProcessFactory(GUIProcess s, int currentTime)
    {
        String name = s.getPid();
        Rectangle newProcess = new Rectangle( 50,50);
        Text text1 = new Text(name);
        //create a stack pane to stack the rectangle and text on top of each other
        StackPane stack = new StackPane();
        stack.getChildren().addAll(newProcess, text1);
        //set the rectangle to be transparent and add a stroke
        newProcess.setFill(Color.TRANSPARENT);
        newProcess.setStroke(Color.BLACK);
        //create a Vbox to add the burst time on the bottom left
        VBox vBox = new VBox();
        vBox.getChildren().add(stack);
        Text text2 = new Text(Integer.toString(currentTime));
        vBox.getChildren().add(text2);
        //shift the burst time a couple of pixels to account for stroke width
        text2.setTranslateX(text2.getX()-4);
        //increment the timer
        return vBox;
    }













    //add the last burst time in the table (cosmetic)
    public static VBox rightEdge(int currentTime)
    {
        VBox vBox = new VBox();
        Rectangle empty = new Rectangle(0,50);
        Text last = new Text(Integer.toString(currentTime));
        last.setTranslateX(last.getX()-6);
        vBox.getChildren().add(empty);
        vBox.getChildren().add(last);
        return vBox;
    }




}
