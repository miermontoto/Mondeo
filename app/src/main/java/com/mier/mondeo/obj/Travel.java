package com.mier.mondeo.obj;

import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

public class Travel {
    private String origin;
    private String destination;
    private Date date;
    private Time startTime;
    private Time finishTime;
    private ArrayList<Split> splits;
    private String note;

    /**
     * Basic constructor for a new travel.
     * Only the basic information is required.
     *
     * @param origin
     * @param destination
     * @param date
     * @param startTime
     */
    public Travel(String origin, String destination, Date date, Time startTime) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.splits = new ArrayList<>();
        this.startTime = startTime;
    }

    /**
     * Complete constructor for a new travel.
     *
     * @param origin
     * @param destination
     * @param date
     * @param startTime
     * @param finishTime
     * @param splits
     * @param note
     */
    public Travel(String origin, String destination, Date date, Time startTime, Time finishTime, ArrayList<Split> splits, String note) {
        this(origin, destination, date, startTime);
        this.splits.addAll(splits);
    }

    public Travel(String data) {
        String[] parts = data.split(";");
        this.origin = parts[0];
        this.destination = parts[1];
        this.date = new Date(parts[2]);
        this.startTime = new Time(parts[3]);
        this.finishTime = new Time(parts[4]);
        this.splits = new ArrayList<>();
        String[] splitParts = parts[5].split("¡");
        for(String splitPart : splitParts) {
            this.splits.add(new Split(splitPart));
        }
        this.note = parts[6];
    }

    public void setFinishTime(Time finishTime) {
        this.finishTime = finishTime;
    }

    public void addSplit(Split split) {
        splits.add(split);
    }

    public Split getSplit(int index) {
        return this.splits.get(index);
    }

    public Split removeSplit(int index) {
        return splits.remove(index);
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s;%s;%s;%s;%s;%s;%s",
                origin, destination, date.toString(), startTime.toString(), finishTime.toString(),
                    splits.isEmpty() ? "" : splits.stream().map(Split::toString).collect(Collectors.joining("¡")), note);
    }
}
