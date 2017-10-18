package com.example.magician.usercommunications.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.magician.usercommunications.R;


public class CustomDialogFragment extends DialogFragment {
    private final String TAG = "AUC_CUSTOM";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //TODO: Create an AlertDialog.Builder instance
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // TODO: Create the custom layout using the LayoutInflater class
        //getLayoutInflater(savedInstanceState) this also correct both try to use Bundle
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_dialog_layout, null);


        // TODO: Build the dialog
        builder.setTitle("Please enter your name")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG, "OK Clicked");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG, "Cancel clicked");
                    }
                }).setView(layout);

        return builder.create();
    }
}
