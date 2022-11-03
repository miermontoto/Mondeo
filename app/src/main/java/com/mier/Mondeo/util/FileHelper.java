package com.mier.Mondeo.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;

import androidx.core.app.ActivityCompat;

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

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

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


    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}
