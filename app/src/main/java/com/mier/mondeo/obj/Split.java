package com.mier.mondeo.obj;

import androidx.annotation.NonNull;

public class Split {

    private final String origin;
    private final String destination;
    private Time time;

    public Split(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public Split(String data) {
        String[] parts = data.split("¿");
        this.origin = parts[0];
        this.destination = parts[1];
        this.time = new Time(parts[2]);
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Time getTime() {
        return time;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    @NonNull
    @Override
    public String toString() {
        return origin + "¿" + destination + "¿" + time.toString();
    }
}
