package com.example.magician.soundplayerg;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

import static android.R.attr.src;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private boolean change = false;
    private static String mFileName = null;
    private ImageButton playPuseButton;
    private ImageButton stopButton;
    private boolean checkPrepare = false;
    private SeekBar soundBar = null;
    private Handler handler = new Handler();
    private double finalTime;
    private double startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stopButton = (ImageButton) findViewById(R.id.Stop_Button);
        stopButton.setEnabled(false);
        prepareMedia();
        playPuseButton = (ImageButton) findViewById(R.id.PlayPuse_Button);
        soundBar = (SeekBar) findViewById(R.id.sound_seekBar);
        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();
        soundBar.setMax((int) finalTime);

        // play and stop func
        playPuseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPrepare == false) {
                    prepareMedia();
                    Toast.makeText(getApplicationContext(), "PrepareDone", Toast.LENGTH_SHORT).show();
                }
                if (change == false) {
                    startPlaying();
                    change = true;
                    stopButton.setClickable(true);
                } else {
                    pausePlaying();
                    change = false;
                }


            }
        });

        // Stop Func
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlaying();
                change = false;
            }
        });




        // create thread to controll seekbar progrees
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    startTime = mediaPlayer.getCurrentPosition();
                    soundBar.setProgress((int)startTime);
                }
                handler.postDelayed(this, 1000);
            }
        });

        // Controll seekBar func
        soundBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (mediaPlayer != null && b) {
                    mediaPlayer.seekTo(i * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }



    //----------------------------------------------------------------------------
    //  Star / Pause Button Funs
    private void startPlaying() {
        Toast.makeText(getApplicationContext(), "Play", Toast.LENGTH_SHORT).show();
        playPuseButton.setImageResource(R.drawable.pause);
        mediaPlayer.start();
        // After Song end this func will be trigger
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(getApplicationContext(), "Media Completed", Toast.LENGTH_SHORT).show();
                stopPlaying();

            }
        });
    }

    private void pausePlaying() {
        Toast.makeText(getApplicationContext(), "Pause", Toast.LENGTH_SHORT).show();
        playPuseButton.setImageResource(R.drawable.play);
        mediaPlayer.pause();
    }

    // Stop Button fun
    private void stopPlaying() {
        Toast.makeText(getApplicationContext(), "stop", Toast.LENGTH_SHORT).show();
        playPuseButton.setImageResource(R.drawable.play);
        soundBar.setProgress((int)startTime);
        mediaPlayer.pause();
        mediaPlayer.release();
        mediaPlayer = null;
        stopButton.setEnabled(false);
        checkPrepare = false;
    }

    // Sound prepare Func
    private void prepareMedia() {
        // this use prepare() that tae long to buff
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.shape_of_you);

        /*
        // Record to the external cache directory for visibility
        mFileName = getExternalCacheDir().getAbsolutePath();
        mFileName += "/shape_of_you.mp3";

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(mFileName);
            mediaPlayer.prepareAsync();  //this better cause run on seperate thread
        } catch (IOException e) {
            Log.e("prepareMedia():", "prepare() failed");
        }
             */
        checkPrepare = true;
        stopButton.setEnabled(true);
        Toast.makeText(getApplicationContext(), "PrepareDone", Toast.LENGTH_SHORT).show();
    }
}
