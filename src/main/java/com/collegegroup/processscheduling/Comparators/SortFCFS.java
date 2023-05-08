package com.collegegroup.processscheduling.Comparators;

import com.collegegroup.processscheduling.Process;

import java.util.Comparator;

public class SortFCFS implements Comparator<Process> {
    // Used for sorting in ascending order of
    // roll number
    public int compare(Process a, Process b)
    {
        return (int) (a.Arrival - b.Arrival);
    }
}
