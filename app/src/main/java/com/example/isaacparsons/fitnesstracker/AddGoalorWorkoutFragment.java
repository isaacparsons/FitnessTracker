package com.example.isaacparsons.fitnesstracker;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddGoalorWorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddGoalorWorkoutFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FrameLayout frameLayout;
    public TextView addWorkoutBtn;
    public TextView addGoalBtn;
    public RelativeLayout relativeLayout;


    public AddGoalorWorkoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddGoalorWorkoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddGoalorWorkoutFragment newInstance(String param1, String param2) {
        AddGoalorWorkoutFragment fragment = new AddGoalorWorkoutFragment();
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
        View v = inflater.inflate(R.layout.fragment_add_goalor_workout, container, false);
        frameLayout = (FrameLayout)v.findViewById(R.id.fragment_add_goal_or_workout_frame_for_buttons);
        addGoalBtn = (TextView) v.findViewById(R.id.add_new_goal_btn);
        addWorkoutBtn = (TextView) v.findViewById(R.id.add_new_workout_btn);
        relativeLayout = (RelativeLayout)v.findViewById(R.id.fragment_add_goal_or_workout_relative_layout);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.mainActivity.getSupportFragmentManager().beginTransaction().remove(MainActivity.mainActivity.getAddGoalorWorkoutFragment()).commit();
                MainActivity.mainActivity.fab.setVisibility(View.VISIBLE);
            }
        });
        addWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.mainActivity.getMainActivity(), SecondaryActivity.class);
                intent.putExtra("choice", "newworkout");
                startActivity(intent);
            }
        });

        addGoalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.mainActivity.getMainActivity(), SecondaryActivity.class);
                intent.putExtra("choice", "addgoal");
                startActivity(intent);
            }
        });

        Animation anim = new ScaleAnimation(
                1f, 1f, // Start and end values for the X axis scaling
                0f, 1f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 1f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(200);

        frameLayout.startAnimation(anim);
        MainActivity.mainActivity.fab.setVisibility(View.GONE);

        return v;

    }

}
