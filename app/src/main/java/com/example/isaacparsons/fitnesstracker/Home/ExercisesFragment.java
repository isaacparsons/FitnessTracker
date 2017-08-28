package com.example.isaacparsons.fitnesstracker.Home;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.isaacparsons.fitnesstracker.Databases.ExercisesDB;
import com.example.isaacparsons.fitnesstracker.Global;
import com.example.isaacparsons.fitnesstracker.MainActivity;
import com.example.isaacparsons.fitnesstracker.R;
import com.example.isaacparsons.fitnesstracker.Workout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExercisesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExercisesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public exercisesAdapter exercisesAdapter;
    public RecyclerView recyclerView;


    public ExercisesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExercisesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExercisesFragment newInstance(String param1, String param2) {
        ExercisesFragment fragment = new ExercisesFragment();
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
    private ArrayList parseQuery(Cursor cursor){
        ArrayList<Workout> exercises = new ArrayList<>();
        int i = 0;
        if(cursor.moveToFirst()) {
            do {
                Workout workout = new Workout(cursor.getString(0).toString());
                exercises.add(i, workout);
                i++;
            } while (cursor.moveToNext());
        }
        return exercises;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_exercises, container, false);

        Global global = new Global();
        ExercisesDB exercisesDB = new ExercisesDB(getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView)v.findViewById(R.id.exercise_fragment_recycler);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Workout> arrayList = parseQuery(exercisesDB.getExerciseData(global.getMUSCLE().toLowerCase()));
        exercisesAdapter = new exercisesAdapter(arrayList);
        recyclerView.setAdapter(exercisesAdapter);

        return v;
    }

    public class exercisesAdapter extends RecyclerView.Adapter<exercisesAdapter.ViewHolder>{
        ArrayList<Workout> exercises = new ArrayList<>();

        public exercisesAdapter(ArrayList arrayList) {
            exercises = arrayList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView cardviewText;
            public ViewHolder(View View) {
                super(View);
                cardviewText = (TextView)View.findViewById(R.id.exercises_cardview_textview);

            }
        }

        @Override
        public ExercisesFragment.exercisesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.exercisescardview, parent, false);

            return new ExercisesFragment.exercisesAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ExercisesFragment.exercisesAdapter.ViewHolder holder, int position) {
            holder.cardviewText.setText(exercises.get(position).getWorkoutTitle().toString());
        }

        @Override
        public int getItemCount() {
            return exercises.size();
        }

    }

}
