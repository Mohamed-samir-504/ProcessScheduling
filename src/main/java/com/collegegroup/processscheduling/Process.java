package com.collegegroup.processscheduling;

public class Process {
    public int ID,Arrival,burst,priority,start,end,time_completed;
    public String pid;
    public boolean visited = false;
    public static int curr = 0;
    public Process(String pid, int arrival, int burst, int priority) {
        this.pid=pid;
        this.Arrival=arrival;
        this.burst=burst;
        this.priority=priority;
    }

    public int getID() {
        return ID;
    }

    public int getArrival() {
        return Arrival;
    }

    public int getBurst() {
        return burst;
    }

    public String getPid() {
        return pid;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isCompleted(){
        return this.time_completed==this.burst;
    }
}



