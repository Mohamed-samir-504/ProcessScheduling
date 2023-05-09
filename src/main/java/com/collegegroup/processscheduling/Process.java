package com.collegegroup.processscheduling;

public class Process {
    public int ID,Arrival,burst,priority,start,end,time_completed,originalArrivalTime,originalBurstTime;
    public String pid;
    public boolean visited = false;
    public static int curr = 0;

    public Process(String pid, int arrival, int burst, int priority) {
        this.pid=pid;
        this.Arrival=arrival;
        this.burst=burst;
        this.priority=priority;
        this.originalArrivalTime = arrival;
        this.originalBurstTime = burst;
        if(pid.charAt(0) < '0'|| pid.charAt(0) >'9'){
            this.ID = Integer.parseInt(pid.substring(1));
        }
        else{
            this.ID = Integer.parseInt(pid);
        }

//        this.start = startTime;
//        this.end = endTime;
    }

    public Process(Process process) {
        this.pid=process.pid;
        this.Arrival=process.getArrival();
        this.burst=process.burst;
        this.priority=process.priority;
        this.originalArrivalTime = process.originalArrivalTime;
        this.originalBurstTime=process.originalBurstTime;

        if(pid.charAt(0) < '0'|| pid.charAt(0) >'9'){
            this.ID = Integer.parseInt(pid.substring(1));
        }
        else{
            this.ID = Integer.parseInt(pid);
        }
    }

    public Process(String id, int arrival, int burst, int current_time, int i,int originalBurst) {
        this.pid = id;
        if(pid.charAt(0) < '0'|| pid.charAt(0) >'9'){
            this.ID = Integer.parseInt(pid.substring(1));
        }
        else{
            this.ID = Integer.parseInt(pid);
        }
        this.Arrival = arrival;
        this.burst = burst;
        this.originalArrivalTime = arrival;
        this.originalBurstTime = originalBurst;
        this.start = current_time;
        this.end = i;

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

    public int getOriginalArrivalTime() {
        return originalArrivalTime;
    }

    public int getOriginalBurstTime() {
        return originalBurstTime;
    }

    public int getEnd() {
        return end;
    }



    public boolean isCompleted(){
        return this.time_completed==this.burst;
    }

    public int getArrivalTime() {
        return Arrival;
    }
}



