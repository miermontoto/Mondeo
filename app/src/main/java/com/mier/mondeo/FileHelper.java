package com.mier.mondeo;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that manages data storage and retrieval.
 * All travels are stored in a single file, called "travels.csv".
 * Each travel is stored in a single line, with the following format:
 * "origin,destination,date,startTime,firstSplit,secondSplit,thirdSplit,finishTime"
 *
 * @author Juan Mier
 */
public class FileHelper {

    private static final String FILENAME = "travels.csv";

    /**
     * Reads the file "travels.csv" and returns a list of all travels.
     * @return A list of all travels.
     */
    public static ArrayList<Travel> readTravels() {
        ArrayList<Travel> travels = new ArrayList<Travel>();
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/travels.csv");
            if (!file.exists()) file.createNewFile();
            FileInputStream fis = new FileInputStream(file);
            Scanner scr = new Scanner(fis);
            scr.useDelimiter("\\Z");
            while (scr.hasNext()) {
                String line = scr.next();
                String[] travelData = line.split(",");
                travels.add(new Travel(travelData[0], travelData[1], travelData[2], travelData[3], travelData[4], travelData[5], travelData[6], travelData[7]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return travels;
        }
    }

    /**
     * Writes a new travel to the file "travels.csv".
     * @param travel The travel to be written.
     */
    public static void writeTravel(Travel travel) {
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/travels.csv");
            if (!file.exists()) file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write(travel.toString().getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
