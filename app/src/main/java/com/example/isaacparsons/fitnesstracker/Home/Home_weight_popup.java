package com.example.isaacparsons.fitnesstracker.Home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.isaacparsons.fitnesstracker.EditWorkoutTitlesPopup;
import com.example.isaacparsons.fitnesstracker.R;

/**
 * Created by isaacparsons on 2017-08-09.
 */

public class Home_weight_popup extends DialogFragment {
    TextView homeFragmentWeightTextview;
    EditText homeFragmentPopupEdittext;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.homeweightpopup, null);
        homeFragmentWeightTextview = (TextView)v.findViewById(R.id.home_fragment_weight_textview);
        homeFragmentPopupEdittext = (EditText)v.findViewById(R.id.home_weight_popup_edittext);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(v)

                .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Home_weight_popup.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}
