package com.mier.Mondeo.util;

import java.util.ArrayList;

public class LoadSave {

    public static final String travelLog = "travel_log.csv";
    public static final String travelList = "travel_list.csv";

    public static String[] getTravelList() {
        ArrayList<String> data = FileHelper.readFile(travelList);
        String[] travels = new String[data.size()];
        for(String s: data) {
            travels[data.indexOf(s)] = s;
        }
        return travels;
    }

    public static void addTravel(String travel) {
        FileHelper.writeToFile(travelList, travel, true);
    }
}
