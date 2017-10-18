package com.example.magician.usercommunications;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class SnackbarActivity extends AppCompatActivity
        implements View.OnClickListener {

    private final String TAG = "AUC-SNACKBAR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snackbar);

        findViewById(R.id.btnShowSnackbar).setOnClickListener(this);
        findViewById(R.id.btnShowActionSnackbar).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnShowSnackbar:
                showSnackbar();// show   Snackbar
                break;
            case R.id.btnShowActionSnackbar:
                showActionSnackbar();
                break;
        }
    }

    private void showSnackbar() {
        // TODO: Create and show the snackbar
        Snackbar snackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout),
                "This is Snackbar", Snackbar.LENGTH_LONG);
        snackbar.show();

    }

    private void showActionSnackbar() {
        // TODO: Create a snackbar that has an action item
        Snackbar snackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout) ,
                "This is Snackbar", Snackbar.LENGTH_LONG);
        snackbar.setAction("My Action",new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Snackbar Action Tap!",Toast.LENGTH_LONG).show();
            }
        });
        snackbar.setActionTextColor(Color.BLUE);
        snackbar.show();

    }

}
