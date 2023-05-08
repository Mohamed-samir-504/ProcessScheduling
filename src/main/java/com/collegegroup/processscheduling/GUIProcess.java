package com.collegegroup.processscheduling;


public class GUIProcess {

    private String pid;
    private String burst;
    private String arrivalTime;
    private String priority;
    private double startTime ;
    private double endTime ;
    private double x;

    public GUIProcess(String pid, String burst, String arrivalTime) {
        this.pid = pid;
        this.burst = burst;
        this.arrivalTime = arrivalTime;
        this.startTime = Integer.parseInt(arrivalTime);
        this.priority = "0";
        this.x = 0;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setBurst(double x)
    {
        burst = String.valueOf(x);
    }


    public GUIProcess(String pid, String burst, String arrivalTime, String priority) {
        this.pid = pid;
        this.burst = burst;
        this.arrivalTime = arrivalTime;
        this.startTime = Double.parseDouble(arrivalTime);
        this.priority = priority;
    }

    public GUIProcess(String pid, String burst, String arrivalTime, String priority, String start, String end)
    {
        this.pid = pid;
        this.burst = burst;
        this.arrivalTime = arrivalTime;
        //this.startTime = Integer.parseInt(arrivalTime);
        this.priority = priority;
        this.startTime = Double.parseDouble(start);
        this.endTime = Double.parseDouble(end);

    }

    public String  getPid() {
        return pid;
    }

    public String getBurst() {
        return burst;
    }
    public double getBurstDouble() {
        return Double.parseDouble(burst);
    }


    public double getArrivalTimeDouble(){ return Integer.parseInt(arrivalTime);}

    public String getPriority() {
        return priority;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
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

            Double.parseDouble(burst);
            Double.parseDouble(arrivalTime);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }


}
