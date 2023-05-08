package com.collegegroup.processscheduling.Comparators;

import com.collegegroup.processscheduling.GUIProcess;
import com.collegegroup.processscheduling.Process;

import java.util.Comparator;

public class SortByPriority_NP implements Comparator<GUIProcess> {
    private double curr;

    public SortByPriority_NP(double curr) {
        this.curr = curr;
    }
    public int compare(GUIProcess a, GUIProcess b)
    {
        if(Integer.parseInt(a.getArrivalTime()) - Integer.parseInt(b.getArrivalTime()) ==0)
        {
            return Integer.parseInt(a.getPriority()) - Integer.parseInt(b.getPriority());
        }
        else if (Integer.parseInt(a.getArrivalTime()) - curr <=0 && Integer.parseInt(b.getArrivalTime()) - curr <=0)
        {
            return Integer.parseInt(a.getPriority()) - Integer.parseInt(b.getPriority());
        }
        else return Integer.parseInt(a.getArrivalTime()) - Integer.parseInt(b.getArrivalTime());
    }
}
