package com.collegegroup.processscheduling.Processes;

public class PriorityProcess extends Processs {
    private String priority;

    public PriorityProcess(String pid, String burst, String arrivalTime, String priority) {
        super(pid, burst, arrivalTime);
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }

    public PriorityProcess(String pid, String burst, String arrivalTime) {
        super(pid, burst, arrivalTime);
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() || priority.isBlank();
    }

    @Override
    public boolean isValid() {
       try
       {
           Integer.parseInt(priority);
           return super.isValid();
       }
       catch (NumberFormatException e)
       {
           return false;
       }

    }
}
