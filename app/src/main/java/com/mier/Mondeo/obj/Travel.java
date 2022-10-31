package com.mier.Mondeo.obj;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Travel {
    private String origin;
    private String destination;
    private Date date;
    private Time startTime;
    private Time finishTime;
    private List<Split> splits;
    private String note;
    private boolean completed;

    public Travel(String origin, String destination, List<Split> splits) {
        this.origin = origin;
        this.destination = destination;
        this.splits = new LinkedList<>(splits);
        completed = false;
    }

    public Travel(String data) {
        String[] split = data.split(",");
        this.origin = split[0];
        this.destination = split[1];
        this.splits = new LinkedList<>();
        for(int i = 5; i < split.length; i++) {
            this.splits.add(new Split(split[i]));
        }
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

    @NotNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s;%s;%s",
                origin, destination, splits.isEmpty() ? "" : splits.stream().map(Split::toString).collect(Collectors.joining("¡")));
    }

    public String getTitle() {
        StringBuilder title = new StringBuilder(String.format("%s → ", origin));
        for(Split split : splits) {
            title.append(String.format("%s → ", split.getDestination()));
        }
        title.append(destination);
        return title.toString();
    }
}
