package com.example.magician.usercommunications;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magician.usercommunications.R;

public class ToastActivity extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);

        findViewById(R.id.btnShowToast).setOnClickListener(this);
        findViewById(R.id.btnShowCustomToast).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnShowToast:
                showToast();
                break;
            case R.id.btnShowCustomToast:
                showCustomToast();
                break;
        }
    }

    private void showToast() {
        int toastDuration;
        RadioButton rbShort = (RadioButton) findViewById(R.id.rbShort);
        if (rbShort.isChecked())
            toastDuration = Toast.LENGTH_SHORT;
        else
            toastDuration = Toast.LENGTH_LONG;

        // TODO: Create and show the toast message
        Toast.makeText(this,"Simple Toast",toastDuration).show();

    }

    private void showCustomToast() {
        int toastDuration;
        RadioButton rbShort = (RadioButton) findViewById(R.id.rbShort);
        if (rbShort.isChecked())
            toastDuration = Toast.LENGTH_SHORT;
        else
            toastDuration = Toast.LENGTH_LONG;

        // TODO: Get the custom layout and inflate it
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_layout,
                (ViewGroup) findViewById(R.id.customToastLayout));

        // TODO: Build a toast message that uses the custom layout
        TextView tv = (TextView) layout.findViewById(R.id.textContent);
        tv.setText("this is a Toast");

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(toastDuration);
        toast.setGravity(Gravity.BOTTOM | Gravity.LEFT, 100, 100);
        toast.setView(layout);
        toast.show();
    }


}
