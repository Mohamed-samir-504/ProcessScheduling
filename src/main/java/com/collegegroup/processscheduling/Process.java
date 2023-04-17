package com.collegegroup.processscheduling;

class Process {
    private int ID,Arrival,burst,priority,start,end,time_completed;
    private String pid;
    private boolean visited = false;
    private static int curr = 0;
    public Process(String pid, int arrival, int brust, int priority) {
        this.pid=pid;
        this.Arrival=arrival;
        this.burst=brust;
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



