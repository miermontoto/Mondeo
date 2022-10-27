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

    /**
     * Reads from a file and returns the data as a String vector.
     */
    public static ArrayList<String> readFile(String filename) {
        ArrayList<String> data = new ArrayList<>();
        try {
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            FileInputStream inputStream = new FileInputStream(file);
            Scanner scr = new Scanner(inputStream);
            while (scr.hasNextLine()) {
                data.add(scr.nextLine());
            }
            scr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void writeToFile(String filename, String line, boolean append) {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            if (!file.exists()) file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file, append);
            fos.write(line.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean fileExists(String filename) {
        File file = new File(Environment.getExternalStorageDirectory(), filename);
        return file.exists();
    }

}
