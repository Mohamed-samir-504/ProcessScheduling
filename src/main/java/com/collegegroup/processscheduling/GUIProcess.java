package com.collegegroup.processscheduling;


public class GUIProcess {

    private String pid;
    private String burst;
    private String arrivalTime;
    private String priority;
    private int startTime ;
    private int endTime ;
    private int x;

    public GUIProcess(String pid, String burst, String arrivalTime) {
        this.pid = pid;
        this.burst = burst;
        this.arrivalTime = arrivalTime;
        this.startTime = Integer.parseInt(arrivalTime);
        this.priority = "0";
        this.x = 0;
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
        this.startTime = Integer.parseInt(arrivalTime);
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
    public int getArrivalTimeInt(){ return Integer.parseInt(arrivalTime);}

    public String getPriority() {
        return priority;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public boolean isNotEmpty()
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
