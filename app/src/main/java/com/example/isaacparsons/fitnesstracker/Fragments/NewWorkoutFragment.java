package com.example.isaacparsons.fitnesstracker.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.isaacparsons.fitnesstracker.Databases.ExercisesDB;
import com.example.isaacparsons.fitnesstracker.Databases.WorkoutsDB;
import com.example.isaacparsons.fitnesstracker.EditWorkoutTitlesPopup;
import com.example.isaacparsons.fitnesstracker.MainActivity;
import com.example.isaacparsons.fitnesstracker.R;
import com.example.isaacparsons.fitnesstracker.Workout;
import com.example.isaacparsons.fitnesstracker.addworkoutAlert;
import com.example.isaacparsons.fitnesstracker.rx.RxSearch;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link NewWorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewWorkoutFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static NewWorkoutFragment newWorkoutFragment;

    //getters and setters

    public static NewWorkoutFragment getNewWorkoutFragment() {
        return newWorkoutFragment;
    }

    public static void setNewWorkoutFragment(NewWorkoutFragment newWorkoutFragment) {
        NewWorkoutFragment.newWorkoutFragment = newWorkoutFragment;
    }

    public NewWorkoutSearchAdapter getNewWorkoutSearchAdapter() {
        return newWorkoutSearchAdapter;
    }

    public void setNewWorkoutSearchAdapter(NewWorkoutSearchAdapter newWorkoutSearchAdapter) {
        this.newWorkoutSearchAdapter = newWorkoutSearchAdapter;
    }

    public RecyclerView getSearchRecycler() {
        return searchRecycler;
    }

    public void setSearchRecycler(RecyclerView searchRecycler) {
        this.searchRecycler = searchRecycler;
    }

    //recyclerview
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    public NewWorkoutAdapter adapter;
    ArrayList<Workout> workouts = new ArrayList<>();

    //search bar
    public RecyclerView searchRecycler;
    public RecyclerView.LayoutManager searchlayoutmanager;
    public NewWorkoutSearchAdapter newWorkoutSearchAdapter;
    ArrayList<String> resultsArray = new ArrayList<>();

    //UI elements
    public EditText WorkoutTitleEdittext;
    public EditText WorkoutWeightEdittext;
    public EditText WorkoutSetsEdittext;
    public EditText WorkoutRepsEdittext;
    public CheckBox WorkoutWeightTypeLbs;
    public CheckBox WorkoutWeightTypeKgs;
    public EditText WorkoutTitle;

    //Buttons
    private Button AddWorkoutButton;
    private Button FinishAddNewWorkoutButton;
    private Button CancelAddNewWorkoutButton;
    private Button ChangeSelectionsButton;

    //CheckBoxes for muscle group
    private CheckBox BicepsCheckbox;
    private CheckBox TricepsCheckbox;
    private CheckBox ShouldersCheckbox;
    private CheckBox CalvesCheckbox;
    private CheckBox QuadsCheckbox;
    private CheckBox ForearmsCheckbox;
    private CheckBox BackCheckbox;
    private CheckBox ChestCheckbox;
    private CheckBox AbsCheckbox;

    //highest weight autocomplete
    public LinearLayout highestWeight;
    public LinearLayout highestSets;
    public LinearLayout highestReps;

    public TextView highestWeightTextview;
    public TextView highestSetsTextview;
    public TextView highestRepsTextview;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public NewWorkoutFragment() {
        // Required empty public constructor
    }
    public void thing(){
        Log.d("lkjdlkjasdlk;", "akslfk");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewWorkoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewWorkoutFragment newInstance(String param1, String param2) {
        NewWorkoutFragment fragment = new NewWorkoutFragment();
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
    public void checkEditText(){
        if(WorkoutTitleEdittext.getText().toString().equals("")){
            searchRecycler.setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_workout, container, false);
        setNewWorkoutFragment(this);

        //recyclerview
        recyclerView = (RecyclerView)v.findViewById(R.id.new_workout_fragment_recyclerview);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //UI
        WorkoutTitleEdittext = (EditText)v.findViewById(R.id.new_workout_fragment_workout_title);
        WorkoutWeightEdittext = (EditText)v.findViewById(R.id.new_workout_fragment_workout_weight);
        WorkoutSetsEdittext = (EditText)v.findViewById(R.id.new_workout_fragment_workout_sets);
        WorkoutRepsEdittext = (EditText)v.findViewById(R.id.new_workout_fragment_workout_reps);
        WorkoutWeightTypeLbs = (CheckBox)v.findViewById(R.id.new_workout_fragment_workout_lbs);
        WorkoutWeightTypeKgs = (CheckBox)v.findViewById(R.id.new_workout_fragment_workout_kgs);
        WorkoutTitle = (EditText)v.findViewById(R.id.fragment_new_workout_workout_title_edittext);

        //Buttons
        AddWorkoutButton = (Button)v.findViewById(R.id.new_workout_fragment_workout_add_workout_button);
        FinishAddNewWorkoutButton = (Button)v.findViewById(R.id.new_workout_fragment_button_ok);
        CancelAddNewWorkoutButton = (Button)v.findViewById(R.id.new_workout_fragment_button_cancel);

        //highest workout
        highestWeight = (LinearLayout) v.findViewById(R.id.previous_highest_weight_new_workout_fragment);
        highestSets = (LinearLayout) v.findViewById(R.id.previous_highest_sets_new_workout_fragment);
        highestReps = (LinearLayout) v.findViewById(R.id.previous_highest_reps_new_workout_fragment);

        highestWeightTextview = (TextView)v.findViewById(R.id.previous_highest_weight_new_workout_textview);
        highestSetsTextview = (TextView)v.findViewById(R.id.previous_highest_sets_new_workout_textview);
        highestRepsTextview = (TextView)v.findViewById(R.id.previous_highest_reps_new_workout_textview);

        //search
        ArrayList arrayList = new ArrayList();
        arrayList.add(0, "HELLO CAN YOU HEAR ME!?");
        newWorkoutSearchAdapter = new NewWorkoutSearchAdapter(arrayList);
        setNewWorkoutSearchAdapter(newWorkoutSearchAdapter);
        searchRecycler = (RecyclerView)v.findViewById(R.id.new_workout_search_recycler);
        setSearchRecycler(searchRecycler);
        searchlayoutmanager = new LinearLayoutManager(getContext());
        searchRecycler.setLayoutManager(searchlayoutmanager);
        searchRecycler.setAdapter(newWorkoutSearchAdapter);




        adapter = new NewWorkoutAdapter(workouts);
        recyclerView.setAdapter(adapter);

        AddWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean nonechecked = true;
                Workout nworkout = new Workout(WorkoutTitleEdittext.getText().toString());
                nworkout.setWeight(Integer.parseInt(WorkoutWeightEdittext.getText().toString()));
                nworkout.setSets(Integer.parseInt(WorkoutSetsEdittext.getText().toString()));
                nworkout.setReps(Integer.parseInt(WorkoutRepsEdittext.getText().toString()));

                adapter.addItemtoRecycler(nworkout);

                WorkoutTitleEdittext.setText("");
                WorkoutWeightEdittext.setText("");
                WorkoutSetsEdittext.setText("");
                WorkoutRepsEdittext.setText("");

                highestWeight.setVisibility(View.GONE);
                highestSets.setVisibility(View.GONE);
                highestReps.setVisibility(View.GONE);

            }
        });

        WorkoutWeightTypeLbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(WorkoutWeightTypeKgs.isChecked()){
                    WorkoutWeightTypeKgs.toggle();
                }
            }
        });

        WorkoutWeightTypeKgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(WorkoutWeightTypeLbs.isChecked()){
                    WorkoutWeightTypeLbs.toggle();
                }
            }
        });

        FinishAddNewWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExercisesDB exercisesDB = new ExercisesDB(getContext());
                for(int i = 0;i<adapter.workouts1.size();i++){
                    exercisesDB.addExercise(adapter.workouts1.get(i));
                    Log.d("Exercise name: ", adapter.workouts1.get(i).getWorkoutTitle());
                }

                WorkoutsDB workoutsDB = new WorkoutsDB(getContext());
                workoutsDB.addWorkout(adapter.workouts1, WorkoutTitle.getText().toString());
                MainActivity mainActivity = new MainActivity();
                Intent intent = new Intent(mainActivity.getMainActivity(), MainActivity.class);
                startActivity(intent);
            }
        });


        CancelAddNewWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        final RxSearch rxSearch = new RxSearch();
        rxSearch.fromEditText(WorkoutTitleEdittext)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("PLEASE WORK: ", "PLEASE");
                    }

                    @Override
                    public void onNext(String value) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });



        WorkoutTitleEdittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            }
        });


        return v;
    }

    public class NewWorkoutAdapter extends RecyclerView.Adapter<NewWorkoutAdapter.ViewHolder> {

        public ArrayList<Workout> workouts1 = new ArrayList<>();
        public NewWorkoutAdapter(ArrayList arrayList) {
            workouts1 = arrayList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView workoutTitle;
            public TextView workoutWeight;
            public TextView workoutSets;
            public TextView workoutReps;
            public TextView workoutType;
            public ViewHolder(View View) {
                super(View);
                workoutTitle = (TextView)View.findViewById(R.id.new_workout_fragment_card_workout_title);
                workoutWeight = (TextView)View.findViewById(R.id.new_workout_fragment_cardview_weight);
                workoutSets = (TextView)View.findViewById(R.id.new_workout_fragment_cardview_sets);
                workoutReps = (TextView)View.findViewById(R.id.new_workout_fragment_cardview_reps);
                workoutType = (TextView)View.findViewById(R.id.new_workout_fragment_weight_type);

            }
        }

        @Override
        public NewWorkoutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.newworkoutfragmentcard, parent, false);

            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.workoutTitle.setText(workouts.get(position).getWorkoutTitle());
            holder.workoutWeight.setText(Integer.toString(workouts.get(position).getWeight()));
            holder.workoutSets.setText(Integer.toString(workouts.get(position).getSets()));
            holder.workoutReps.setText(Integer.toString(workouts.get(position).getReps()));
            if(WorkoutWeightTypeLbs.isChecked()){
                holder.workoutType.setText(" lbs");
            }
            if(WorkoutWeightTypeKgs.isChecked()){
                holder.workoutType.setText(" kgs");
            }
        }

        @Override
        public int getItemCount() {
            return workouts1.size();
        }

        public void addItemtoRecycler(Workout workout){
            workouts1.add(workout);
            adapter.notifyDataSetChanged();
        }

    }
    public class NewWorkoutSearchAdapter extends RecyclerView.Adapter<NewWorkoutSearchAdapter.ViewHolder> {

        public ArrayList<String> results = new ArrayList<>();
        public NewWorkoutSearchAdapter(ArrayList arrayList) {
            results = arrayList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView result;
            public ViewHolder(View View) {
                super(View);
                result = (TextView)View.findViewById(R.id.new_workout_fragment_search_result);
            }
        }

        @Override
        public NewWorkoutSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.new_workout_fragment_search_card, parent, false);

            return new NewWorkoutSearchAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.result.setText(results.get(position));
            holder.result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WorkoutTitleEdittext.setText(results.get(position));
                    searchRecycler.setVisibility(View.GONE);
                    ExercisesDB exercisesDB = new ExercisesDB(getContext());
                    Workout workout = exercisesDB.getHighestWorkoutWeight(results.get(position));
                    highestWeight.setVisibility(View.VISIBLE);
                    highestWeightTextview.setText(String.valueOf(workout.getWeight()));
                    //Log.d("highestWeight:", " "+workout);

                    highestSets.setVisibility(View.VISIBLE);
                    highestSetsTextview.setText(String.valueOf(workout.getSets()));

                    highestReps.setVisibility(View.VISIBLE);
                    highestRepsTextview.setText(String.valueOf(workout.getReps()));

                }
            });
        }

        @Override
        public int getItemCount() {
            return results.size();
        }


    }
}


