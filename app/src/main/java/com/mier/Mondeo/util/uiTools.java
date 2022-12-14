package com.mier.Mondeo.util;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class uiTools {

    private uiTools() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Set a snackbar to the bottom of the screen
     * @param root the root view of the activity
     * @param snackTitle the title of the snackbar
     */
    public static void setSnackBar(View root, String snackTitle) {
        Snackbar snackbar = Snackbar.make(root, snackTitle, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
