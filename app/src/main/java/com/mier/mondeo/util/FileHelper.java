package com.mier.mondeo.util;

import android.os.Environment;

import com.mier.mondeo.obj.Travel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that manages data storage and retrieval.
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
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + FILENAME);
            if (!file.exists()) return null;
            FileInputStream fis = new FileInputStream(file);
            Scanner scr = new Scanner(fis);
            scr.useDelimiter("\\Z");
            while (scr.hasNext()) {
                String line = scr.next();
                String[] travelData = line.split(",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return travels;
    }

    /**
     * Writes a new travel to the file "travels.csv".
     * @param travel The travel to be written.
     */
    public static void writeTravel(Travel travel) {
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + FILENAME);
            if (!file.exists()) file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write(travel.toString().getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
