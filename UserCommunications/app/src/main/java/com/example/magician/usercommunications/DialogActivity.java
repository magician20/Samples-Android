package com.example.magician.usercommunications;


import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.nfc.Tag;
import android.os.Build;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.magician.usercommunications.Dialogs.CustomDialogFragment;
import com.example.magician.usercommunications.Dialogs.SimpleDialogFragment;
import com.example.magician.usercommunications.Dialogs.SingleChoiceDialogFragment;

import static java.security.AccessController.getContext;


public class DialogActivity extends AppCompatActivity
        implements View.OnClickListener ,SimpleDialogFragment.SimpleDialogListener {

    private final String TAG = "AUC_DLG_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        // TODO: set up button click handlers
        findViewById(R.id.btnSimpleDialog).setOnClickListener(this);
        findViewById(R.id.btnShowDatePicker).setOnClickListener(this);
        findViewById(R.id.btnShowTimePicker).setOnClickListener(this);
        findViewById(R.id.btnShowChoiceDialog).setOnClickListener(this);
        findViewById(R.id.btnShowCustomDialog).setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSimpleDialog:
                // Show Simple Dialog
                showSimpleDialog();
                break;
            case R.id.btnShowDatePicker:
                // TODO: Get a calendar instance
                Calendar calendar = Calendar.getInstance();

                // TODO: Create a DatePickerDialog >> @4 paramters passes
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int mOfYear, int dOfMonth) {
                                Log.i(TAG, String.format("Date Chosen--day:%d,month:$d, year:%d",
                                        dOfMonth, mOfYear, year));
                                Toast.makeText(getApplicationContext(), String.format("Date Chosen--day:%d,month:%d, year:%d",
                                        dOfMonth, mOfYear, year), Toast.LENGTH_SHORT).show();
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                // TODO: Set the title and show the dialog
                datePickerDialog.setTitle("Choose a Date");
                datePickerDialog.show();

                break;
            case R.id.btnShowChoiceDialog:
                showChoiceDialog();
                break;
            case R.id.btnShowCustomDialog:
                showCustomDialog();
                break;
        }
    }

    // TODO: Show Simple Dialog
    private void showSimpleDialog() {
        SimpleDialogFragment simpleDialog = new SimpleDialogFragment();
        // TODO: Use setCancelable() to make the dialog non-cancelable
        simpleDialog.setCancelable(false);
        simpleDialog.show(getSupportFragmentManager(), "SimpleDialogFragment");
    }

    //
    private void showCustomDialog() {
        CustomDialogFragment customDialog = new CustomDialogFragment();
        customDialog.show(getSupportFragmentManager(), "CustomDialogFragment");
    }

    private void showChoiceDialog() {
        SingleChoiceDialogFragment complexDialog = new SingleChoiceDialogFragment();
        complexDialog.show(getSupportFragmentManager(), "SingleChoiceDialogFragment");
    }


    //TODO: implement dialog listener interface functions
    @Override
    public void onPositiveResult(DialogFragment df) {
        Toast.makeText(this, "Positive Button Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNegativeResult(DialogFragment df) {
        Toast.makeText(this, "Negative Button Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNeutralResult(DialogFragment df) {
        Toast.makeText(this, "Neutral Button Clicked", Toast.LENGTH_SHORT).show();
    }



}
