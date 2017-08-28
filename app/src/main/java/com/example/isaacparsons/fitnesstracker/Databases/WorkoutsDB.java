package com.example.isaacparsons.fitnesstracker.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.isaacparsons.fitnesstracker.Workout;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by isaacparsons on 2017-08-12.
 */

public class WorkoutsDB extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "workouts";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "workout_table";
    private final static String WORKOUT_NAME = "workout_name";
    private final static String WORKOUT_JSON_STRING = "workout";
    private final static String DATE = "date";




    public WorkoutsDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_WORKOUTS_TABLE = "CREATE TABLE "+ TABLE_NAME+"("+WORKOUT_NAME+" TEXT,"+WORKOUT_JSON_STRING+" TEXT,"+DATE+ " TEXT)";
        sqLiteDatabase.execSQL(CREATE_WORKOUTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addWorkout(ArrayList<Workout> workouts, String workoutTitle) {

        String jsonOutput = new Gson().toJson(workouts);
        SQLiteDatabase db = this.getWritableDatabase();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        ContentValues values = new ContentValues();
        values.put(WORKOUT_JSON_STRING, jsonOutput);
        values.put(WORKOUT_NAME, workoutTitle);
        values.put(DATE, dateFormat.format(cal.getTime()).toString());

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public Cursor getWorkoutData(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor res = database.rawQuery("SELECT * FROM workout_table", null);
        return res;
    }
}
