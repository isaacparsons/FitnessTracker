package com.example.isaacparsons.fitnesstracker.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.isaacparsons.fitnesstracker.Goal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by isaacparsons on 2017-08-19.
 */

public class GoalsDB extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "goals";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "goals_table";
    private final static String GOAL_NAME = "goal_name";
    private final static String START_DATE = "start_date";
    private final static String END_DATE = "end_date";
    private final static String START_WEIGHT = "start_weight";
    private final static String END_WEIGHT = "end_weight";
    private final static String PROGRESS = "progress";
    //from 1-100 (percent)





    public GoalsDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_GOALS_TABLE = "CREATE TABLE "+ TABLE_NAME+"("+GOAL_NAME+" TEXT,"+START_DATE+ " TEXT,"+
                END_DATE+" TEXT,"+START_WEIGHT+" INTEGER,"+END_WEIGHT+" INTEGER,"+PROGRESS+" INTEGER)";
        sqLiteDatabase.execSQL(CREATE_GOALS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        goal.setStartDate(dateFormat.format(cal.getTime()).toString());

        ContentValues values = new ContentValues();
        values.put(GOAL_NAME, goal.getGoalTitle());
        values.put(START_DATE, goal.getStartDate());
        values.put(END_DATE, goal.getEndDate());
        values.put(START_WEIGHT, goal.getStartWeight());
        values.put(END_WEIGHT, goal.getEndWeight());
        values.put(PROGRESS, goal.getProgressPercent());


        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        Log.d("DATABASE LOG", "add goal called");
        db.close();
    }
    public ArrayList getGoals(){
        ArrayList goals = new ArrayList();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM goals_table", null);
        int i = 0;
        if(cursor.moveToFirst()) {
            do {
                Goal goal = new Goal();
                goal.setGoalTitle(cursor.getString(0));
                goal.setStartDate(cursor.getString(1));
                goal.setEndDate(cursor.getString(2));
                goal.setStartWeight(cursor.getInt(3));
                goal.setEndWeight(cursor.getInt(4));
                goal.setProgressPercent(cursor.getInt(5));

                goals.add(i, goal);
                i++;
            } while (cursor.moveToNext());
        }
        database.close();
        return goals;
    }
    public void updateProgress(int progress){
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PROGRESS, progress);
        database.update(TABLE_NAME, cv, "", null);
    }
}
