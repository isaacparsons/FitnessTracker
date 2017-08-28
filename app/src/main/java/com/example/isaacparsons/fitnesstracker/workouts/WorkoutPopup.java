package com.example.isaacparsons.fitnesstracker.workouts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.isaacparsons.fitnesstracker.Databases.WorkoutsDB;
import com.example.isaacparsons.fitnesstracker.Home.Home_weight_popup;
import com.example.isaacparsons.fitnesstracker.MainActivity;
import com.example.isaacparsons.fitnesstracker.R;
import com.example.isaacparsons.fitnesstracker.Workout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isaacparsons on 2017-08-12.
 */

public class WorkoutPopup extends DialogFragment {

    public WorkoutPopupAdapter workoutPopupAdapter;
    public RecyclerView recyclerView;

    public TextView titleTextview;
    public TextView dateTextview;



    private ArrayList<Workout> parseData(String title){

        //TODO: add date to raw query so workouts with the same title dont mess things up

        ArrayList<Workout> workout;
        WorkoutsDB workoutsDB = new WorkoutsDB(new MainActivity().getMainActivity());
        Cursor res = workoutsDB.getReadableDatabase().rawQuery("SELECT * FROM workout_table WHERE workout_name = '"+title+"'", null);
        res.moveToFirst();
        workout = new Gson().fromJson(res.getString(1).toString(), new TypeToken<List<Workout>>(){}.getType());
        return workout;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.workoutpopup, null);
        WorkoutsDB workoutsDB = new WorkoutsDB(new MainActivity().getMainActivity());
        Cursor cursor = workoutsDB.getWorkoutData();

        recyclerView = (RecyclerView)v.findViewById(R.id.workout_popup_recycler);
        workoutPopupAdapter = new WorkoutPopupAdapter(parseData(getArguments().getString("key")));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(workoutPopupAdapter);

        //Titles
        titleTextview = (TextView)v.findViewById(R.id.workout_popup_title_textview);
        titleTextview.setText(getArguments().getString("key"));

        builder.setView(v)

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        WorkoutPopup.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public class WorkoutPopupAdapter extends RecyclerView.Adapter<WorkoutPopupAdapter.ViewHolder> {
        ArrayList<Workout> exercises = new ArrayList<>();
        public WorkoutPopupAdapter(ArrayList arrayList) {
            exercises = arrayList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView Title;
            public TextView Weight;
            public TextView Reps;
            public TextView Sets;
            public ViewHolder(View itemView) {
                super(itemView);
                Title = (TextView)itemView.findViewById(R.id.workout_popup_cardview_title);
                Weight = (TextView)itemView.findViewById(R.id.workout_popup_cardview_weight);
                Reps = (TextView)itemView.findViewById(R.id.workout_popup_cardview_reps);
                Sets = (TextView)itemView.findViewById(R.id.workout_popup_cardview_sets);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.workout_popup_recycler_cardview, parent, false);

            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.Title.setText(exercises.get(position).getWorkoutTitle().toString());
            holder.Weight.setText(String.valueOf(exercises.get(position).getWeight()));
            holder.Sets.setText(String.valueOf(exercises.get(position).getSets()));
            holder.Reps.setText(String.valueOf(exercises.get(position).getReps()));
        }

        @Override
        public int getItemCount() {
            return exercises.size();
        }

    }

}
