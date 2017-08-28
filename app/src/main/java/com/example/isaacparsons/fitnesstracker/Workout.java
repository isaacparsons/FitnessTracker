package com.example.isaacparsons.fitnesstracker;

/**
 * Created by isaacparsons on 2017-08-03.
 */

public class Workout {
    private String WorkoutTitle;
    private String muscle;
    private int reps;
    private int sets;
    private int weight;
    private String weightType;
    private String Date;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public String getWeightType() {
        return weightType;
    }

    public void setWeightType(String weightType) {
        this.weightType = weightType;
    }

    public Workout(String workoutTitle) {
        WorkoutTitle = workoutTitle;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getWorkoutTitle() {
        return WorkoutTitle;
    }

    public void setWorkoutTitle(String workoutTitle) {
        WorkoutTitle = workoutTitle;
    }
}
