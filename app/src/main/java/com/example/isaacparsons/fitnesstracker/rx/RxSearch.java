package com.example.isaacparsons.fitnesstracker.rx;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isaacparsons.fitnesstracker.Databases.ExercisesDB;
import com.example.isaacparsons.fitnesstracker.Fragments.NewWorkoutFragment;
import com.example.isaacparsons.fitnesstracker.MainActivity;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by isaacparsons on 2017-08-18.
 */

public class RxSearch {

    public static Observable<String> fromEditText(@NonNull final EditText editText){
        final BehaviorSubject<String> subject = BehaviorSubject.create();

        final NewWorkoutFragment newWorkoutFragment = NewWorkoutFragment.getNewWorkoutFragment();
        final NewWorkoutFragment.NewWorkoutSearchAdapter adapter = newWorkoutFragment.getNewWorkoutSearchAdapter();
        final RecyclerView recyclerView = newWorkoutFragment.getSearchRecycler();
        final ExercisesDB exercisesDB = new ExercisesDB(MainActivity.mainActivity.getApplicationContext());


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("henlo", "am small bean"+charSequence);
                ArrayList<String> arrayList = exercisesDB.getexerciseNames(charSequence.toString());
                if (arrayList.size()>=1) {
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.results = arrayList;
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                newWorkoutFragment.checkEditText();
                subject.onComplete();
            }
        });
        return subject;
    }
}
