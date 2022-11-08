package com.mier.Mondeo.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.mier.Mondeo.obj.Route;
import com.mier.Mondeo.obj.Travel;

import java.util.ArrayList;
import java.util.List;

public class dbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "mondeodb";
    public static final int DB_VERSION = 1;

    public static final String TABLE_ROUTES = "routes";
    public static final String ROUTE_ID = "_id";
    public static final String ROUTE_ORIGIN = "src";
    public static final String ROUTE_DESTINATION = "dst";

    public static final String TABLE_TRAVELS = "travels";
    public static final String TRAVEL_ID = "_id";
    public static final String TRAVEL_ROUTE_FK = "route";
    public static final String TRAVEL_DATE = "date";
    public static final String TRAVEL_START_TIME = "start";
    public static final String TRAVEL_END_TIME = "finish";
    public static final String TRAVEL_NOTE = "note";

    public static final String TABLE_RELATIVES = "relatives";
    public static final String RELATIVE_ID = "_id";
    public static final String RELATIVE_TRAVEL_FK = "travel";
    public static final String RELATIVE_SPLIT_FK = "split";
    public static final String RELATIVE_START_TIME = "start";
    public static final String RELATIVE_END_TIME = "finish";

    public static final String TABLE_SPLIT = "splits";
    public static final String SPLIT_ID = "_id";
    public static final String SPLIT_NAME = "name";
    public static final String SPLIT_ROUTE_FK = "route";


    public dbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_ROUTES + " (" +
                ROUTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ROUTE_ORIGIN + " TEXT NOT NULL, " +
                ROUTE_DESTINATION + " TEXT NOT NULL);");
        db.execSQL("CREATE TABLE " + TABLE_TRAVELS + " (" +
                TRAVEL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TRAVEL_ROUTE_FK + " INTEGER NOT NULL, " +
                TRAVEL_DATE + " TEXT, " +
                TRAVEL_START_TIME + " TEXT, " +
                TRAVEL_END_TIME + " TEXT, " +
                TRAVEL_NOTE + " TEXT, " +
                "FOREIGN KEY (" + TRAVEL_ROUTE_FK + ") REFERENCES " + TABLE_ROUTES + "(" + ROUTE_ID + "));");
        db.execSQL("CREATE TABLE " + TABLE_RELATIVES + " (" +
                RELATIVE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RELATIVE_TRAVEL_FK + " INTEGER, " +
                RELATIVE_SPLIT_FK + " INTEGER, " +
                RELATIVE_START_TIME + " TEXT, " +
                RELATIVE_END_TIME + " TEXT, " +
                "FOREIGN KEY (" + RELATIVE_SPLIT_FK + ") REFERENCES " + TABLE_SPLIT + "(" + SPLIT_ID + "), " +
                "FOREIGN KEY (" + RELATIVE_TRAVEL_FK + ") REFERENCES " + TABLE_TRAVELS + "(" + TRAVEL_ID + "));");
        db.execSQL("CREATE TABLE " + TABLE_SPLIT + " (" +
                SPLIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SPLIT_NAME + " TEXT, " +
                SPLIT_ROUTE_FK + " INTEGER," +
                "FOREIGN KEY (" + SPLIT_ROUTE_FK + ") REFERENCES " + TABLE_ROUTES + "(" + ROUTE_ID + "));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAVELS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RELATIVES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPLIT);
        onCreate(db);
    }

    public void saveRoute(Route route) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLE_ROUTES + " (" +
                ROUTE_ORIGIN + ", " +
                ROUTE_DESTINATION + ") VALUES ('" +
                route.getOrigin() + "', '" +
                route.getDestination() + "');");

        if(route.hasSplits()) {
            for(String split : route.getSplits()) {
                db.execSQL("INSERT INTO " + TABLE_SPLIT + " (" +
                        SPLIT_NAME + ", " +
                        SPLIT_ROUTE_FK + ") VALUES ('" +
                        split + "', " +
                        "(SELECT " + ROUTE_ID + " FROM " + TABLE_ROUTES + " WHERE " + ROUTE_ORIGIN + " = '" + route.getOrigin() + "' AND " + ROUTE_DESTINATION + " = '" + route.getDestination() + "'));");
            }
        }

        db.close();
    }

    @SuppressLint("Range")
    public List<Route> getRoutes() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ROUTES, null);
        List<Route> routes = new ArrayList<>();
        while (cursor.moveToNext()) {
            String origin = cursor.getString(cursor.getColumnIndex(ROUTE_ORIGIN));
            String destination = cursor.getString(cursor.getColumnIndex(ROUTE_DESTINATION));

            // obtain splits if any are attributed to this route
            List<String> splits = new ArrayList<>();
            Cursor splitCursor = db.rawQuery("SELECT * FROM " + TABLE_SPLIT + " WHERE " + SPLIT_ROUTE_FK + " = " + cursor.getInt(cursor.getColumnIndex(ROUTE_ID)), null);
            while (splitCursor.moveToNext()) {
                splits.add(splitCursor.getString(splitCursor.getColumnIndex(SPLIT_NAME)));
            }
            splitCursor.close();

            routes.add(new Route(origin, destination, splits));

        }
        cursor.close();
        db.close();
        return routes;
    }

    public void saveTravel(Travel travel) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLE_TRAVELS + " (" +
                TRAVEL_ROUTE_FK + ", " +
                TRAVEL_DATE + ", " +
                TRAVEL_START_TIME + ", " +
                TRAVEL_END_TIME + ") VALUES (" +
                "(SELECT " + ROUTE_ID + " FROM " + TABLE_ROUTES + " WHERE " + ROUTE_ORIGIN + " = '" + travel.getRoute().getOrigin() + "' AND " + ROUTE_DESTINATION + " = '" + travel.getRoute().getDestination() + "'), '" +
                travel.getDate() + "', '" +
                travel.getStartTime() + "', '" +
                travel.getFinishTime() + "');");
    }
}
