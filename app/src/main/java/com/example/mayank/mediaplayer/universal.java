package com.example.mayank.mediaplayer;

import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.support.v4.app.NotificationCompat;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mayank on 12/3/2018.
 */

public class universal {



   //current playing
   static int currentindex;
  static MediaPlayer mediaPlayer;
  static MediaMetadataRetriever meta=new MediaMetadataRetriever();
  static  int current;
   static int min, sec;
static  String currentsongname;
    static  ArrayList<HashMap<String,String>> filelist;
   static int dur;
   static ImageView play;
   static TextView max, maxsec;
   static TextView curr;
  static   TextView songname;
  static   TextView minute;
    static byte[] art;
  static   ImageView image;
    static SeekBar seekBar;
    static  boolean isplaying=false;
    static NotificationCompat.Builder notification;
    static NotificationManager notificationManager;
    static ProgressBar progressBar;
    static ArrayList<Bitmap> images= new ArrayList<>();
}
