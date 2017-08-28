package com.example.isaacparsons.fitnesstracker.Fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.isaacparsons.fitnesstracker.Databases.WorkoutsDB;
import com.example.isaacparsons.fitnesstracker.MainActivity;
import com.example.isaacparsons.fitnesstracker.R;
import com.example.isaacparsons.fitnesstracker.Workout;
import com.example.isaacparsons.fitnesstracker.workouts.WorkoutPopup;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WorkoutsAdapter workoutsAdapter;
    public RecyclerView workoutsRecycler;

    public WorkoutsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutsFragment newInstance(String param1, String param2) {
        WorkoutsFragment fragment = new WorkoutsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_workouts, container, false);
        WorkoutsDB workoutsDB = new WorkoutsDB(getContext());
        Cursor cursor = workoutsDB.getWorkoutData();

        ArrayList<Workout> workouts = new ArrayList<>();
        int i = 0;
        Log.d("cursor", " "+ cursor.moveToFirst());
        if(cursor.moveToFirst()){
            do{
                Workout workout = new Workout(cursor.getString(0).toString());
                if(cursor.getString(2)!= null) {
                    workout.setDate(cursor.getString(2).toString());
                }
                workouts.add(i, workout);
                i++;
            }while (cursor.moveToNext());
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        workoutsAdapter = new WorkoutsAdapter(workouts);
        workoutsRecycler = (RecyclerView)v.findViewById(R.id.workouts_recycler);
        workoutsRecycler.setHasFixedSize(true);
        workoutsRecycler.setLayoutManager(linearLayoutManager);

        workoutsRecycler.setAdapter(workoutsAdapter);



        return v;
    }

    public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.ViewHolder> {
        ArrayList<Workout> workouts = new ArrayList<>();

        public WorkoutsAdapter(ArrayList arrayList) {
            workouts = arrayList;
            Collections.reverse(workouts);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView deleteImageview;
            public TextView workoutTitle;
            public TextView workoutDate;
            public ViewHolder(View View) {
                super(View);
                workoutTitle = (TextView)View.findViewById(R.id.workout_fragment_card_textview);
                deleteImageview = (ImageView)View.findViewById(R.id.fragment_workout_delete);
                workoutDate = (TextView)View.findViewById(R.id.workout_fragment_card_date);


            }
        }
        @Override
        public WorkoutsFragment.WorkoutsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.workoutfragmentcardview, parent, false);

            return new WorkoutsFragment.WorkoutsAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final WorkoutsFragment.WorkoutsAdapter.ViewHolder holder, final int position) {
            holder.workoutTitle.setText(workouts.get(position).getWorkoutTitle().toString());
            if(workouts.get(position).getDate()!= null) {
                holder.workoutDate.setText(workouts.get(position).getDate().toString());
            }


            holder.workoutTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("key", workouts.get(position).getWorkoutTitle().toString());
                    WorkoutPopup workoutPopup = new WorkoutPopup();
                    workoutPopup.setArguments(bundle);
                    workoutPopup.show(getActivity().getFragmentManager(), "");
                }
            });
            holder.workoutTitle.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    holder.deleteImageview.setVisibility(View.VISIBLE);
                    return true;
                }
            });

            holder.deleteImageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return workouts.size();
        }

    }

}
