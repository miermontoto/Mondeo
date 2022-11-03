package com.mier.Mondeo.util;

import com.mier.Mondeo.obj.Route;
import com.mier.Mondeo.obj.Travel;

import java.util.ArrayList;
import java.util.List;

public class LoadSave {

    public static final String travelLog = "travel_log.csv";
    public static final String routeList = "routes.csv";

    public static List<Route> getRoutes() {
        ArrayList<String> data = FileHelper.readFile(routeList);
        List<Route> routes = new ArrayList<>();
        for (String line : data) {
            String[] fields = line.split("¿?");
            routes.add(new Route(fields[0], fields[1]));
        }
        return routes;
    }

    public static void addRoute(Route route) {
        FileHelper.writeToFile(routeList, route.toString(), true);
    }

    public static boolean addTravel(Travel travel) {
        // TODO: travel verification
        FileHelper.writeToFile(travelLog, travel.toString(), true);
        return true;
    }

}
