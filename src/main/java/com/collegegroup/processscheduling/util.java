package com.collegegroup.processscheduling;

import com.collegegroup.processscheduling.Comparators.SortByFCFS;
import com.collegegroup.processscheduling.Comparators.*;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.*;

public abstract class util {

    public static Process toProcess(GUIProcess guiProcess)
    {
        int arrival,burst,priority,startTime,endTime;
        String pid;
        pid = guiProcess.getPid();
        arrival = Integer.parseInt(guiProcess.getArrivalTime());
        burst = Integer.parseInt(guiProcess.getBurst());
        priority = Integer.parseInt(guiProcess.getPriority());
//        startTime = guiProcess.getStartTime();
//        endTime = guiProcess.getEndTime();
        return new Process(pid,arrival,burst,priority);
    }
    public static GUIProcess toGUIProcess(Process process)
    {
        return new GUIProcess(process.getPid(),
                Integer.toString(process.getBurst()),
                Integer.toString(process.getArrival()),
                Integer.toString(process.getPriority()),
                Integer.toString(process.start),
                Integer.toString(process.end));
    }

    public static VBox processFactory(GUIProcess s, int width, int currentTime , int totalTime)
    {
        String name = s.getPid();
        Rectangle newProcess = new Rectangle(50*width,50);
//        (double) (width * 600) /totalTime + 20
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

    public static VBox liveProcessFactory(GUIProcess s, int currentTime,int quantum)
    {
        String name = s.getPid();
        Rectangle newProcess = new Rectangle( 50*quantum,50);
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
        vBox.setId(name);
        //shift the burst time a couple of pixels to account for stroke width
        text2.setTranslateX(text2.getX()-4);
        //increment the timer
        return vBox;
    }


    public static VBox IDLEliveProcessFactory(String s, int currentTime,int quantum)
    {
        String name = s;
        Rectangle newProcess = new Rectangle( 50*quantum,50);
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
        vBox.setId(name);
        //shift the burst time a couple of pixels to account for stroke width
        text2.setTranslateX(text2.getX()-4);
        //increment the timer
        return vBox;
    }


    public static ArrayList<Process> SJP_PREE(ArrayList<Process> processes, boolean priority)
    {
        Comparator<Process> comparator ;
        if(priority) {
            comparator = Comparator.comparing(Process::getArrival)
                    .thenComparing(Process::getPriority).thenComparing(Process::getID);
        }
        else comparator= Comparator.comparing(Process::getArrival).thenComparing(Process::getBurst).thenComparing(Process::getID);

        PriorityQueue<Process> pq = new PriorityQueue<>(comparator);

        for (int i = 0; i < processes.size(); i++) {
            pq.add(processes.get(i));
//            /if (!p.isCompleted && p.arrivalTime <= currentTime) {
//                pq.add(p);
//            }/
        }

        int currentTime = 0;
        ArrayList<Process> ganttChart = new ArrayList<>();

        while(!pq.isEmpty()){
            Process process = pq.poll();
            if(currentTime>=process.getArrival()){

                ganttChart.add(new Process(process));
                process.burst--;

            }
            else ganttChart.add(new Process(Integer.toString(0),0,0,0));
            currentTime++;
            pq.clear();
            for (int i = 0; i < processes.size(); i++) {
                if(currentTime> processes.get(i).Arrival) processes.get(i).Arrival=currentTime;
                if(processes.get(i).burst!=0)pq.add(processes.get(i));
            }

        }

        return ganttChart;
    }


    public static ArrayList<Process> round_robin(ArrayList<Process> processes,int roundtime)
    {
        processes.sort(new SortFCFS());
        ArrayList<Process> gc=new ArrayList<Process>();
        int current_time=0;
        int itr = 0;
        boolean f = true;

        Hashtable<Process,Integer>hashh = new Hashtable<Process, Integer>();

        for(Process p : processes){
            hashh.put(p,p.getBurst());
        }

        while(!processes.isEmpty()){
            f = true;
            Process pro = processes.get(itr);
            if(pro.Arrival <= current_time){

                if(pro.burst>=roundtime)
                {
                    pro.burst-=roundtime;
                    gc.add(new Process(pro.pid, pro.Arrival, pro.burst,current_time,current_time+roundtime, hashh.get(pro)));
                    //current_time+=roundtime;
                }
                else{
                    gc.add(new Process(pro.pid, pro.Arrival, 0,current_time,current_time+ pro.burst, hashh.get(pro)));
                    current_time+= pro.burst;
                    f = false;
                    pro.burst=0;
                }
            }
            if(pro.burst <= 0){
                processes.remove(itr);
            }
            if(processes.size()!=0){
                itr= (itr+1)%processes.size();
            }

            if(f){
                current_time+=roundtime;
            }

        }
        return gc;

    }

    public static ArrayList<Process> modify(ArrayList<Process> gc){
        int last_id=0,start=0;
        ArrayList<Process> gcFINAL = new ArrayList<Process>();
        for (int i=0;i< gc.size()-1;i++)
        {
            if(gc.get(i).ID==0) {start=i+1; continue;}

            if(gc.get(i).ID!=gc.get(i+1).ID)
            {
                int x=i+1;
                gc.get(i).end=x;
                gc.get(i).start=start;
                gcFINAL.add(gc.get(i));
                //System.out.println(gc.get(i).ID + " "+start+" "+x);
                start=x;
                gc.get(i).visited=true;
            }
        }
        // System.out.println(gc.get(gc.size()-1).ID + " "+start+" "+ gc.size());
        gc.get(gc.size()-1).start=start;
        gc.get(gc.size()-1).end=gc.size();
        gcFINAL.add(gc.get(gc.size()-1));

        return gcFINAL;
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
