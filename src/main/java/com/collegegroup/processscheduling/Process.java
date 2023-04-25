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
        this.ID = Integer.parseInt(pid);
//        this.start = startTime;
//        this.end = endTime;
    }

    public Process(Process process) {
        this.pid=process.pid;
        this.Arrival=process.getArrival();
        this.burst=process.burst;
        this.priority=process.priority;
        this.ID = Integer.parseInt(process.pid);
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



