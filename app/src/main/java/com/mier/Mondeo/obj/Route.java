package com.mier.Mondeo.obj;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class Route {

    private String origin;
    private String destination;
    private List<String> splits;

    public Route(String src, String dst, List<String> splits) {
        this(src, dst);
        this.splits = new LinkedList<>(splits);
    }

    public Route(String src, String dst) {
        this.origin = src;
        this.destination = dst;
        splits = new LinkedList<>();
    }

    public String[] getSplits() {
        return splits.toArray(new String[0]);
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public void addSplit(String split) {
        splits.add(split);
    }

    public void removeSplit(String split) {
        splits.remove(split);
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean hasSplits() {
        return !splits.isEmpty();
    }

    @NotNull
    public String getTitleSimple() {
        return String.format(Locale.getDefault(), "%s → %s", origin, destination);
    }

    @NotNull
    public String getTitleComplete() {
        String title = this.getTitleSimple();
        if(!splits.isEmpty()) {
            title += " via ";
            for(String s: splits) {
                title += s + " → ";
            }
            title = title.substring(0, title.length() - 3);
        }
        return title;
    }
}
