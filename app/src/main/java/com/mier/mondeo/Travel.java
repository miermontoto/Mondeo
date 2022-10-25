package com.mier.mondeo;

public class Travel {
    private String origin;
    private String destination;
    private String date;
    private String startTime;
    private String firstSplit;
    private String secondSplit;
    private String thirdSplit;
    private String finishTime;

    public Travel(String origin, String destination, String date, String startTime, String firstSplit, String secondSplit, String thirdSplit, String finishTime) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.startTime = startTime;
        this.firstSplit = firstSplit;
        this.secondSplit = secondSplit;
        this.thirdSplit = thirdSplit;
        this.finishTime = finishTime;
    }
}
