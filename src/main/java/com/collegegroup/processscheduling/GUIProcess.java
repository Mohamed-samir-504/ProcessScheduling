package com.collegegroup.processscheduling;

public class GUIProcess {
    private String pid;
    private String burst;
    private String arrivalTime;
    private String priority;
    private String startTime;
    private String endTime;

    public GUIProcess(String pid, String burst, String arrivalTime) {
        this.pid = pid;
        this.burst = burst;
        this.arrivalTime = arrivalTime;
        this.priority = "0";
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
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
