package com.collegegroup.processscheduling.Comparators;

import com.collegegroup.processscheduling.GUIProcess;
import com.collegegroup.processscheduling.Process;

import java.util.Comparator;

public class SortBySJF_NP implements Comparator<GUIProcess> {
    private double curr;

    public SortBySJF_NP(double curr) {
        this.curr = curr;
    }

    public int compare(GUIProcess a, GUIProcess b)
    {
        if(Integer.parseInt(a.getArrivalTime()) - Integer.parseInt(b.getArrivalTime()) ==0)
        {
            return Integer.parseInt(a.getBurst()) - Integer.parseInt(b.getBurst());
        }
        else if (Integer.parseInt(a.getArrivalTime()) - curr <=0 && Integer.parseInt(b.getArrivalTime()) - curr <=0)
        {
            return Integer.parseInt(a.getBurst()) - Integer.parseInt(b.getBurst());
        }
        else return Integer.parseInt(a.getArrivalTime()) - Integer.parseInt(b.getArrivalTime());
    }
}