package com.example.isaacparsons.fitnesstracker.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import com.example.isaacparsons.fitnesstracker.Workout;

import java.util.ArrayList;

/**
 * Created by isaacparsons on 2017-08-10.
 */

public class ExercisesDB extends SQLiteOpenHelper
{
    private final static String DATABASE_NAME = "exercises";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "exercises_table";
    private final static String EXERCISE_NAME = "exercise_name";
    private final static String DATE = "date";
    private final static String WEIGHT = "weight";
    private final static String WEIGHT_TYPE = "weight_type";
    private final static String SETS = "sets";
    private final static String REPS = "reps";
    private final static String MUSCLE = "muscle";




    public ExercisesDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_WORKOUTS_TABLE = "CREATE TABLE "+ TABLE_NAME+"("+EXERCISE_NAME+" TEXT,"+DATE+ " TEXT,"+
                WEIGHT+" TEXT,"+WEIGHT_TYPE+" TEXT,"+SETS+" TEXT,"+REPS+" TEXT,"+MUSCLE+" TEXT)";
        sqLiteDatabase.execSQL(CREATE_WORKOUTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addExercise(Workout workout) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EXERCISE_NAME, workout.getWorkoutTitle());
        values.put(REPS, workout.getReps());
        values.put(SETS, workout.getSets());
        values.put(WEIGHT, workout.getWeight());
        values.put(WEIGHT_TYPE, workout.getWeightType());
        values.put(MUSCLE, workout.getMuscle());

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        Log.d("DATABASE LOG", "addworkoutcalled");
        db.close();
    }
    public Cursor getExerciseData(String muscle){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor res = database.rawQuery("SELECT * FROM exercises_table WHERE muscle = '"+muscle+"'", null);
        database.close();
        return res;
    }
    public ArrayList<String> getexerciseNames(String search){
        ArrayList results = new ArrayList();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM exercises_table WHERE exercise_name LIKE '"+search+"%'", null);
        int i = 0;
        if(cursor.moveToFirst()) {
            do {
                results.add(i, cursor.getString(0).toString());
                i++;
            } while (cursor.moveToNext());
        }
        database.close();
        return results;
    }
    public Workout getHighestWorkoutWeight(String exerciseName){
        Workout highestWorkout = new Workout(exerciseName);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM exercises_table" +
                " WHERE exercise_name = '"+exerciseName+"' " +
                "ORDER BY weight ASC LIMIT 1", null);
        if(cursor.moveToFirst()){
            highestWorkout.setWeight(cursor.getInt(2));
            highestWorkout.setReps(cursor.getInt(5));
            highestWorkout.setSets(cursor.getInt(4));
        } else {
            highestWorkout.setWeight(0);
            highestWorkout.setReps(0);
            highestWorkout.setSets(0);
        }

        return highestWorkout;
    }
}