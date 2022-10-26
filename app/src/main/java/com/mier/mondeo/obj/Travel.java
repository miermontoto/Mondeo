package com.mier.mondeo.obj;

import java.util.ArrayList;
import java.util.Locale;

public class Travel {
    private String origin;
    private String destination;
    private Date date;
    private Time startTime;
    private Time finishTime;
    private ArrayList<Split> splits;
    private String note;
    private int numberOfSplits;
    private boolean isFinished;

    public Travel(String origin, String destination, Date date, Time startTime, int numberOfSplits) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.splits = new ArrayList<>();
        this.startTime = startTime;

        numberOfSplits = 0;
        isFinished = true;
    }

    public Travel(String origin, String destination, Date date, Time startTime, Time finishTime, ArrayList<Split> splits, String note, int numberOfSplits, boolean isFinished) {
        this(origin, destination, date, startTime, numberOfSplits);
        isFinished = true;
        this.numberOfSplits = numberOfSplits;
        this.splits.addAll(splits);
    }

    public void setFinishTime(Time finishTime) {
        this.finishTime = finishTime;
    }

    public void addSplit(Split split) {
        this.splits.add(split);
        numberOfSplits++;
    }

    public Split getSplit(int index) {
        return this.splits.get(index);
    }

    public boolean removeSplit(int index) {
        if (index < 0 || index >= numberOfSplits) {
            return false;
        }
        this.splits.remove(index);
        numberOfSplits--;
        return true;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s,%s,%s,%s,%s,%s,%s");
    }
}
