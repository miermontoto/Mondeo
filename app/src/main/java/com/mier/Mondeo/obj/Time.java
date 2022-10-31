package com.mier.Mondeo.obj;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Date;

public class Time {

    private final byte hour;
    private final byte minute;
    private final byte second;

    public Time(String time) {
        String[] timeData = new String[3];
        if (time.matches("\\d{2}:\\d{2}:\\d{2}")) {
            timeData = time.split(":");
        } else if (time.matches("\\d{2}:\\d{2}")) {
            timeData = time.split(":");
            timeData[2] = "00";
        } else {
            throw new IllegalArgumentException("Invalid time format.");
        }

        this.hour = Byte.parseByte(timeData[0]);
        this.minute = Byte.parseByte(timeData[1]);
        this.second = Byte.parseByte(timeData[2]);
    }

    public Time(byte hour, byte minute, byte second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public Time() {
        java.util.Date date = new Date();
        this.hour = (byte) date.getHours();
        this.minute = (byte) date.getMinutes();
        this.second = (byte) date.getSeconds();
    }

    public byte getHour() {
        return hour;
    }

    public byte getMinute() {
        return minute;
    }

    public byte getSecond() {
        return second;
    }

    @NotNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, minute, second);
    }
}
