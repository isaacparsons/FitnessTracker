package com.example.isaacparsons.fitnesstracker;

import java.util.Calendar;

/**
 * Created by isaacparsons on 2017-08-19.
 */

public class Goal {
    private String goalTitle;
    private String startDate;
    private String endDate;
    private int startWeight;
    private int EndWeight;
    private Integer progressPercent;

    public String getGoalTitle() {
        return goalTitle;
    }

    public void setGoalTitle(String goalTitle) {
        this.goalTitle = goalTitle;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(int startWeight) {
        this.startWeight = startWeight;
    }

    public int getEndWeight() {
        return EndWeight;
    }

    public void setEndWeight(int endWeight) {
        EndWeight = endWeight;
    }

    public Integer getProgressPercent() {
        return progressPercent;
    }

    public void setProgressPercent(Integer progressPercent) {
        this.progressPercent = progressPercent;
    }
}
