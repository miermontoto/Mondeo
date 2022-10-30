package com.mier.Mondeo.obj;

import java.util.Locale;

public class Date {

    private final byte day;
    private final byte month;
    private final short year;

    public Date(String date) {
        String[] dateData = new String[3];
        if (date.matches("\\d{2}/\\d{2}/\\d{4}")) {
            dateData = date.split("/");
        } else if (date.matches("\\d{2}-\\d{2}-\\d{4}")) {
            dateData = date.split("-");
        } else {
            throw new IllegalArgumentException("Invalid date format.");
        }

        this.day = Byte.parseByte(dateData[0]);
        this.month = Byte.parseByte(dateData[1]);
        this.year = Short.parseShort(dateData[2]);
    }

    public Date(byte day, byte month, short year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public byte getDay() {
        return day;
    }

    public byte getMonth() {
        return month;
    }

    public short getYear() {
        return year;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month, year);
    }
}
