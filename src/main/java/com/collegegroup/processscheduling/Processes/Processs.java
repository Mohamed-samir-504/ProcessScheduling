package com.collegegroup.processscheduling.Processes;

public class Processs {
    private Integer pid;
    private Integer burst;
    private Integer arrivalTime;

    public Processs(int pid, int burst, int arrivalTime) {
        this.pid = pid;
        this.burst = burst;
        this.arrivalTime = arrivalTime;
    }
    public Processs()
    {}

}
