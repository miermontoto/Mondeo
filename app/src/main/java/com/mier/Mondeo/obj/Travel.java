package com.mier.Mondeo.obj;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Travel {
    private Route route;
    private Date date;
    private Time startTime;
    private Time finishTime;
    private Time totalTime;
    private String note;
    private List<Time> relatives;

    public Travel(Route route) {
        this.route = route;
        relatives = new LinkedList<>();
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate() {
        this.date = new Date();
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setStartTime() {
        this.startTime = new Time();
    }

    public void setFinishTime(Time finishTime) {
        this.finishTime = finishTime;
    }

    public void setFinishTime() {
        this.finishTime = new Time();
    }

    public void setNote (String note) {
        this.note = note;
    }

    public void setTotalTime(Time time) {
        this.totalTime = time;
    }

    public Time getTotalTime() {
        return totalTime;
    }

    public Time getFinishTime() {
        return finishTime;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Date getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }

    public void addRelative(Time rel) {
        relatives.add(rel);
    }

    public String[] getRelatives() {
        return relatives.stream().map(Time::toString).toArray(String[]::new);
    }

    public Route getRoute() {
        return route;
    }

    @NotNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s^%s^%s^%s^%s^%s^%s", route, date, startTime, finishTime, totalTime, note, relatives);
    }
}
