package com.collegegroup.processscheduling.Comparators;

import com.collegegroup.processscheduling.GUIProcess;
import com.collegegroup.processscheduling.Process;

import java.util.Comparator;

public class SortByFCFS implements Comparator<GUIProcess> {
    // Used for sorting in ascending order of
    // roll number
    public int compare(GUIProcess a, GUIProcess b)
    {
        return Integer.parseInt(a.getArrivalTime()) - Integer.parseInt(b.getArrivalTime());
    }
}