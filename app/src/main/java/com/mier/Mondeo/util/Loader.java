package com.mier.Mondeo.util;

import android.content.Context;

import com.mier.Mondeo.obj.Route;
import com.mier.Mondeo.obj.Travel;

import java.util.List;

public class Loader {

    private Loader() {}

    public static List<Route> loadRoutes(Context context) {
        dbHelper db = new dbHelper(context);
        return db.getRoutes();
    }

    public static boolean saveRoute(Context context, Route route) {
        dbHelper db = new dbHelper(context);
        db.saveRoute(route);
        return true;
    }

    public static boolean saveTravel(Context context, Travel travel) {
        dbHelper db = new dbHelper(context);
        db.saveTravel(travel);
        return true;
    }


}
