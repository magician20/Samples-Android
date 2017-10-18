package com.example.magician.miwokapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;

/**
 * Created by magic on 10/1/2017.
 */

public abstract class ReleaseMediaPlayer extends MediaPlayer {

     private static  MediaPlayer.OnCompletionListener onComplet =new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer(mediaPlayer);
        }
    };

    // clean the media player when done
    public static void releaseMediaPlayer(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }


    }
       public static void executeCompletRelease(MediaPlayer mediaPlayer){

           mediaPlayer.setOnCompletionListener(onComplet);

       }


}
