package com.example.magician.usercommunications.Dialogs;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;


public class SimpleDialogFragment extends DialogFragment {
    private final String TAG = "AUC_SIMPLE";
    private SimpleDialogListener dialoghost;//hold a refernce to the activity that calls this dialog

    // TODO: Implement an interface for hosts to get callbacks
    public interface SimpleDialogListener{
        public void onPositiveResult(DialogFragment df);
        public void onNegativeResult(DialogFragment df);
        public void onNeutralResult(DialogFragment df);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //TODO: Create an AlertDialog.Builder instance
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //TODO: Set builder properties
        builder.setTitle("Pass Preference").setMessage("Do u like Sugar Snap Peas ?");
        builder.setPositiveButton("Sure!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Log.i(TAG, "Positive Button Clicked");
               // Toast.makeText(getContext(), "Positive Button Clicked", Toast.LENGTH_SHORT).show();
                dialoghost.onPositiveResult(SimpleDialogFragment.this);
            }
        });

        builder.setNegativeButton("No Way!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Log.i(TAG, "Negative Button Clicked");
                //Toast.makeText(getContext(), "Negative Button Clicked", Toast.LENGTH_SHORT).show();
                dialoghost.onNegativeResult(SimpleDialogFragment.this);
            }
        });

        builder.setNeutralButton("Not Sure!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Log.i(TAG, "Neutral Button Clicked");
                //Toast.makeText(getContext(), "Neutral Button Clicked", Toast.LENGTH_SHORT).show();
                dialoghost.onNeutralResult(SimpleDialogFragment.this);
            }
        });

        // TODO: return the created dialog
        return builder.create();
    }

    // TODO: Listen for cancel message by overriding onCancel
    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.i(TAG, "Dialog was Cancled");
        Toast.makeText(getContext(), "Cancled", Toast.LENGTH_SHORT).show();
    }


    // TODO: Override onAttach to get Activity instance
    // important to get result from dialog to the acticity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //now we have the reference to the host activity (=context)
        dialoghost=(SimpleDialogListener)context;
    }
}
