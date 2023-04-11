package com.collegegroup.processscheduling;

public class Process {
    private int pid;
    private int burst;
    private int arrivalTime;

    public Process(int pid, int burst, int arrivalTime) {
        this.pid = pid;
        this.burst = burst;
        this.arrivalTime = arrivalTime;
    }
    public Process()
    {}

}
