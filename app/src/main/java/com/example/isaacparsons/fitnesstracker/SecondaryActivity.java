package com.example.isaacparsons.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.isaacparsons.fitnesstracker.Fragments.NewWorkoutFragment;
import com.example.isaacparsons.fitnesstracker.Home.ExercisesFragment;

public class SecondaryActivity extends AppCompatActivity {

    public static SecondaryActivity secondaryActivity;

    public SecondaryActivity getSecondaryActivity() {
        return secondaryActivity;
    }

    public void setSecondaryActivity(SecondaryActivity secondaryActivity) {
        this.secondaryActivity = secondaryActivity;
    }

    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSecondaryActivity(this);
        setContentView(R.layout.activity_secondary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        startFrag(intent.getStringExtra("choice"));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        returntoMain();
    }
    public void returntoMain(){
        MainActivity mainActivity = new MainActivity();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void startFrag(String frag){

        if(frag.equals("newworkout")) {
            NewWorkoutFragment newWorkoutFragment = new NewWorkoutFragment();
            fm.beginTransaction().replace(R.id.content_secondary, newWorkoutFragment).addToBackStack(null).commit();
        }
        if(frag.equals("viewexercises")) {
            ExercisesFragment exercisesFragment = new ExercisesFragment();
            fm.beginTransaction().replace(R.id.content_secondary, exercisesFragment).addToBackStack(null).commit();
        }
        if(frag.equals("addgoal")){
            GoalFragment goalFragment = new GoalFragment();
            fm.beginTransaction().replace(R.id.content_secondary, goalFragment).addToBackStack(null).commit();
        }

    }

}
