package com.mier.Mondeo.util.data;

import android.content.Context;

import com.mier.Mondeo.obj.Route;
import com.mier.Mondeo.obj.Travel;
import com.mier.Mondeo.util.data.dbHelper;

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

    // TODO: loadTravels

    public static boolean saveTravel(Context context, Travel travel) {
        dbHelper db = new dbHelper(context);
        db.saveTravel(travel);
        return true;
    }


}
