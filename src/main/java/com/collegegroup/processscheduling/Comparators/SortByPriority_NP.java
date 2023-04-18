package com.collegegroup.processscheduling.Comparators;

import com.collegegroup.processscheduling.GUIProcess;
import com.collegegroup.processscheduling.Process;

import java.util.Comparator;

public class SortByPriority_NP implements Comparator<GUIProcess> {

    public int compare(GUIProcess a, GUIProcess b)
    {
        if(Integer.parseInt(a.getArrivalTime()) - Integer.parseInt(b.getArrivalTime()) ==0)
        {
            return Integer.parseInt(a.getPriority()) - Integer.parseInt(b.getPriority());
        }
        else if (Integer.parseInt(a.getArrivalTime()) - Process.curr <=0 && Integer.parseInt(b.getArrivalTime()) - Process.curr <=0)
        {
            return Integer.parseInt(a.getPriority()) - Integer.parseInt(b.getPriority());
        }
        else return Integer.parseInt(a.getArrivalTime()) - Integer.parseInt(b.getArrivalTime());
    }
}
