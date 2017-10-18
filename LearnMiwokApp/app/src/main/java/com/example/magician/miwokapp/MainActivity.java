package com.example.magician.miwokapp;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;




public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set ActionBar UPButton true
       // getActionBar().setDisplayHomeAsUpEnabled(true);

        //find the view that shows the numbers category
        TextView numbers = (TextView) findViewById(R.id.textView_numbers);
        //set a clickListener on the view
        numbers.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(),"Open The List Of Numbers",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(intent);
            }
        });


        TextView familly = (TextView) findViewById(R.id.textView_familly);
        familly.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(),"Open The List Of Numbers",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, FamillyActivity.class);
                startActivity(intent);
            }
        });

        TextView colors = (TextView) findViewById(R.id.textView_colors);
        colors.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(),"Open The List Of Numbers",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, ColorsActivity.class);
                startActivity(intent);
            }
        });

        TextView phrases = (TextView) findViewById(R.id.textView_phrases);
        phrases.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(),"Open The List Of Numbers",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, PhrasesActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();// if this method take bundle mean that will show the last data

                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
        }

        return super.onOptionsItemSelected(item);
    }

}
