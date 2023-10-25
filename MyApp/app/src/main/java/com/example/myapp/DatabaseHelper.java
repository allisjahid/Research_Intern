package com.example.myapp;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sensor_data.db";
    private static final int DATABASE_VERSION = 1;

    // Sensor table and columns
    public static final String TABLE_SENSOR_DATA = "sensor_data";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SENSOR_TYPE = "sensor_type";
    public static final String COLUMN_SENSOR_VALUE = "sensor_value";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    // Create table SQL statement
    private static final String CREATE_TABLE_SENSOR_DATA = "CREATE TABLE " +
            TABLE_SENSOR_DATA + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_SENSOR_TYPE + " TEXT," +
            COLUMN_SENSOR_VALUE + " REAL," +
            COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SENSOR_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SENSOR_DATA);
        onCreate(db);
    }
}

