package com.example.isaacparsons.fitnesstracker;


import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;
import com.example.isaacparsons.fitnesstracker.Databases.GoalsDB;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_CODE = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //stuff
    public EditText goalTitleEditText;
    public TextView goalFinishDateTextview;
    public EditText goalWeightStartEditText;
    public EditText goalWeightEndEditText;

    public Button goalAddBtn;
    public Button goalCancelBtn;


    public GoalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GoalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GoalFragment newInstance(String param1, String param2) {
        GoalFragment fragment = new GoalFragment();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {

                    Bundle bundle = data.getExtras();
                    String resultDate = bundle.getString("selectedDate", "error");
                    goalFinishDateTextview.setText(resultDate);

                }
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_goal, container, false);
        goalTitleEditText = (EditText) v.findViewById(R.id.goal_fragment_title_edittext);
        goalFinishDateTextview = (TextView) v.findViewById(R.id.goal_fragment_end_date_textview);
        goalWeightStartEditText = (EditText) v.findViewById(R.id.goal_fragment_weight_start_edittext);
        goalWeightEndEditText = (EditText) v.findViewById(R.id.goal_fragment_weight_end_edittext);

        goalFinishDateTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoalDatePicker newFragment = new GoalDatePicker();
                newFragment.setTargetFragment(GoalFragment.this, REQUEST_CODE);
                newFragment.show(SecondaryActivity.secondaryActivity.getSupportFragmentManager(), "datePicker");
            }
        });

        goalAddBtn = (Button) v.findViewById(R.id.goal_fragment_add_btn);
        goalCancelBtn = (Button) v.findViewById(R.id.goal_fragment_cancel_btn);


        goalAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoalsDB goalsDB = new GoalsDB(getContext());
                Goal goal = new Goal();
                goal.setGoalTitle(goalTitleEditText.getText().toString());
                goal.setEndDate(goalFinishDateTextview.getText().toString());
                goal.setStartWeight(Integer.parseInt(goalWeightStartEditText.getText().toString()));
                goal.setEndWeight(Integer.parseInt(goalWeightEndEditText.getText().toString()));
                goal.setProgressPercent(30);
                goalsDB.addGoal(goal);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        goalCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondaryActivity.secondaryActivity, MainActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

}
