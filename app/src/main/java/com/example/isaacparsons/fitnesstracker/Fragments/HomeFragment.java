package com.example.isaacparsons.fitnesstracker.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.isaacparsons.fitnesstracker.Databases.GoalsDB;
import com.example.isaacparsons.fitnesstracker.Goal;
import com.example.isaacparsons.fitnesstracker.Home.Home_weight_popup;
import com.example.isaacparsons.fitnesstracker.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Textview
    TextView WeightTextview;


    RecyclerView recyclerView;
    HomeAdapter homeAdapter;



    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        WeightTextview = (TextView)v.findViewById(R.id.home_fragment_weight_textview);
        WeightTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Home_weight_popup home_weight_popup = new Home_weight_popup();
                home_weight_popup.show(getActivity().getFragmentManager(), "");
            }
        });

        GoalsDB goalsDB = new GoalsDB(getContext());
        //Goal goal = new Goal();
        //goal.setGoalTitle("TEST GOAL");
        //goal.setStartDate("08/19/2017");
        //goal.setEndDate("09/19/2017");
        //goal.setStartWeight(100);
        //goal.setEndWeight(200);
        //goal.setProgressPercent(50);

        //goalsDB.addExercise(goal);

        //recycler & adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView)v.findViewById(R.id.home_recycler);
        recyclerView.setLayoutManager(linearLayoutManager);
        homeAdapter = new HomeAdapter(goalsDB.getGoals());
        recyclerView.setAdapter(homeAdapter);


        return v;
    }


    public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

        ArrayList<Goal> goals = new ArrayList<>();
        public HomeAdapter(ArrayList arrayList) {
            goals = arrayList;
            Collections.reverse(goals);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView GoalTitle;
            public ProgressBar GoalProgress;
            public TextView goalStartDate;
            public TextView goalEndDate;
            public ViewHolder(View View) {
                super(View);
                GoalTitle = (TextView)View.findViewById(R.id.home_recycler_card_title);
                GoalProgress = (ProgressBar)View.findViewById(R.id.home_recycler_progress_bar);
                goalStartDate = (TextView)View.findViewById(R.id.goal_recycler_start_date_textview);
                goalEndDate = (TextView)View.findViewById(R.id.goal_recycler_end_date_textview);
            }
        }

        @Override
        public HomeFragment.HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.homerecyclercardview, parent, false);

            return new HomeFragment.HomeAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(HomeFragment.HomeAdapter.ViewHolder holder, final int position) {
            holder.GoalTitle.setText(goals.get(position).getGoalTitle());
            holder.goalStartDate.setText(goals.get(position).getStartDate());
            holder.goalEndDate.setText(goals.get(position).getEndDate());
            holder.GoalProgress.setProgress(goals.get(position).getProgressPercent());
        }

        @Override
        public int getItemCount() {
            return goals.size();
        }


    }
}


