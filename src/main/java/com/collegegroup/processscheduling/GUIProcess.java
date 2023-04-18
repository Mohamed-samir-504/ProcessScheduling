package com.collegegroup.processscheduling;


public class GUIProcess {

    private String pid;
    private String burst;
    private String arrivalTime;
    private String priority;
    private int startTime = 0;
    private int endTime = 0;

    public GUIProcess(String pid, String burst, String arrivalTime) {
        this.pid = pid;
        this.burst = burst;
        this.arrivalTime = arrivalTime;
        this.priority = "0";
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setBurst(int x)
    {
        burst = String.valueOf(x);
    }


    public GUIProcess(String pid, String burst, String arrivalTime, String priority) {
        this.pid = pid;
        this.burst = burst;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
    }

    public GUIProcess()
    {}

    public String  getPid() {
        return pid;
    }

    public String getBurst() {
        return burst;
    }
    public int getBurstInt() {
        return Integer.parseInt(burst);
    }

    public String getPriority() {
        return priority;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public boolean isEmpty()
    {
        return !burst.isBlank() && !arrivalTime.isBlank();
    }
    public boolean isValid()
    {
        try
        {
            Integer.parseInt(burst);
            Integer.parseInt(arrivalTime);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }


}
