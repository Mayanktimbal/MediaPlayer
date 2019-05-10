package com.example.mayank.mediaplayer;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;

import java.io.IOException;

import static android.R.drawable.ic_media_pause;


public class background extends AsyncTask
{


    @Override
    protected Object doInBackground(Object[] objects) {

        while (universal.mediaPlayer != null && !isCancelled()) {


            universal.dur = universal.mediaPlayer.getDuration();


            universal.current = universal.mediaPlayer.getCurrentPosition();

            universal.min = (universal.current / 60000);
            universal.sec = (universal.current / 1000) % 60;

            this.publishProgress(Integer.toString(universal.sec), Integer.toString(universal.min),Integer.toString(universal.dur / 60000),(Integer.toString((universal.dur / 1000) % 60)));

        }
        return  null;

    }


    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
        universal.curr.setText((CharSequence) values[0]);
        universal.minute.setText((CharSequence) values[1]);
        universal.max.setText((CharSequence) values[2]);
        universal.maxsec.setText((CharSequence) values[3]);
        universal.seekBar.setMax(universal.dur);
        universal.seekBar.setProgress(universal.current);


        }



}


