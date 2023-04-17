package com.collegegroup.processscheduling;

public abstract class util {

    public Process toProcess(GUIProcess guiProcess)
    {
        int arrival,burst,priority;
        String pid;
        pid = guiProcess.getPid();
        arrival = Integer.parseInt(guiProcess.getArrivalTime());
        burst = Integer.parseInt(guiProcess.getBurst());
        priority = Integer.parseInt(guiProcess.getPriority());
        return new Process(pid,arrival,burst,priority);
    }
    public GUIProcess toGUIProcess(Process process)
    {
        return new GUIProcess(process.getPid(),
                Integer.toString(process.getBurst()),
                Integer.toString(process.getArrival()),
                Integer.toString(process.getPriority()));
    }




}
