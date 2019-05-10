package com.example.mayank.mediaplayer;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import static android.R.drawable.ic_media_pause;
import static android.R.drawable.ic_media_play;



public class MainActivity extends AppCompatActivity {

    background background;

public  static Context context1;
    private  GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       context1=getApplicationContext();


        universal.play = (ImageView) findViewById(R.id.play);
        universal.max = (TextView) findViewById(R.id.textView2);
       universal.curr = (TextView) findViewById(R.id.textView4);
        universal.songname = (TextView) findViewById(R.id.song);
        universal.image = (ImageView) findViewById(R.id.imageView);
        universal.minute = (TextView) findViewById(R.id.textView5);
       universal. maxsec = (TextView) findViewById(R.id.textView7);
       universal. seekBar= (SeekBar) findViewById(R.id.seekBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            universal.play.setImageDrawable(getDrawable(ic_media_pause));
        }




        universal.meta = new MediaMetadataRetriever();
        Bundle bundle = getIntent().getExtras();
        String name = (String) bundle.get("name");
        String path = (String) bundle.get("path");
        TranslateAnimation animation =new TranslateAnimation(30,-universal.songname.getWidth()-60,0,0);
        animation.setDuration(10000);
        universal.songname.startAnimation(animation);
        //notification
      universal.notification =new NotificationCompat.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            universal.notification.setSmallIcon(ic_media_play);
        }
        universal.notification.setContentTitle(" currently playing song");
        universal.notification.setContentText(universal.currentsongname);
        universal.notification.setOngoing(true);
        Intent notificationIntent = new Intent(getApplicationContext(),MainActivity.class);
        notificationIntent.putExtra("path",bundle.get("path").toString());
        notificationIntent.putExtra("name",bundle.get("name").toString());
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent intent = PendingIntent.getActivity(getApplicationContext(),0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        universal.notification.setContentIntent(intent);
        universal.notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
      //  universal.notificationManager.notify(001,universal.notification.build());
//startService(new Intent(this,PullService.class));

        if(universal.isplaying) {
            if (name.equalsIgnoreCase(universal.currentsongname)) {

                universal.songname.setText(name);
                universal.currentsongname=name;

            }

            else {
                universal.mediaPlayer.stop();
                universal.songname.setText(name);
                universal.currentsongname=name;
                universal.mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(path));
                universal.mediaPlayer.start();
                universal.notification.setContentText(universal.currentsongname);
                universal.notification.setOngoing(true);
                universal.notificationManager.notify(001,universal.notification.build());
                universal.isplaying= true;
            }
        }

        else
        {

            universal.songname.setText(name);
            universal.currentsongname=name;
            universal.mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(path));
            universal.mediaPlayer.start();
            universal.notification.setContentText(universal.filelist.get(universal.currentindex).get("name"));
            universal.notification.setOngoing(true);
            universal.notificationManager.notify(001,universal.notification.build());
            universal.isplaying= true;
        }

        universal.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                next(new View(context1));
            }
        });

        universal.meta.setDataSource(universal.filelist.get(universal.currentindex).get("path"));
        universal.art = universal.meta.getEmbeddedPicture();

       // startService(new Intent(getApplicationContext(),PullService.class));


        background = new background() ;
        background.execute();




        if (universal.art != null) {

            Bitmap songimage = BitmapFactory.decodeByteArray(universal.art, 0, universal.art.length);
            universal.image.setImageBitmap(songimage);

        }
        else {
            universal.image.setImageResource(R.drawable.def);
        }

        universal.current = universal.mediaPlayer.getCurrentPosition();
universal.dur=universal.mediaPlayer.getDuration();
        universal.seekBar.setMax(universal.dur);

        universal.seekBar.setProgress(universal.current);
        universal.min = (universal.current / 60000);
        universal.sec = (universal.current / 1000) % 60;
        universal.curr.setText(Integer.toString(universal.sec));
        universal.minute.setText(Integer.toString(universal.min));
        universal.max.setText(Integer.toString(universal.dur / 60000));
       universal.maxsec.setText(Integer.toString((universal.dur / 1000) % 60));


        universal.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(universal.mediaPlayer != null && b)
                {
                   universal.mediaPlayer.seekTo(i);

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




    @Override
    protected void onStop() {
        super.onStop();
        background.cancel(true);


    }

    @Override
    protected void onResume() {
        super.onResume();
background obj;
        obj= new background();
        obj.execute();
    }

    public void play(View view) {


        if (!universal.mediaPlayer.isPlaying()) {
            universal.current = universal.mediaPlayer.getCurrentPosition();

            universal.mediaPlayer.start();

            universal.notification.setOngoing(true);
            universal.notificationManager.notify(001, universal.notification.build());


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                universal.play.setImageDrawable(getDrawable(ic_media_pause));
            }

        } else {
            universal.dur = universal.current;
            universal.curr.setText(Float.toString(universal.dur / 1000));
            universal.mediaPlayer.pause();
universal.notification.setOngoing(false);
            universal.notificationManager.notify(001, universal.notification.build());


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                universal.play.setImageDrawable(getDrawable(ic_media_play));
            }

        }
    }


    public void previous(View view) {


       if(universal.mediaPlayer.getCurrentPosition()>2000)
       {

           universal.mediaPlayer.seekTo(0);
       }

        else {

           if (universal.currentindex != 0) {
               universal.mediaPlayer.stop();
               universal.mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(universal.filelist.get(--universal.currentindex).get("path")));
               universal.mediaPlayer.start();
               universal.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                   @Override
                   public void onCompletion(MediaPlayer mediaPlayer) {
                       next(new View(context1));
                   }
               });

               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                   universal.play.setImageDrawable(getDrawable(ic_media_pause));
               }
               universal.songname.setText(universal.filelist.get(universal.currentindex).get("name"));
               universal.currentsongname = universal.filelist.get(universal.currentindex).get("name");
               universal.meta.setDataSource(universal.filelist.get(universal.currentindex).get("path"));

               universal.art = universal.meta.getEmbeddedPicture();
               Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
               universal.notification.setContentText(universal.currentsongname);
               universal.notification.setOngoing(true);
               notificationIntent.putExtra("path", universal.filelist.get(universal.currentindex).get("path"));
               notificationIntent.putExtra("name", universal.filelist.get(universal.currentindex).get("name"));
               notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);


               universal.notification.setContentIntent(intent);

               universal.notificationManager.notify(001, universal.notification.build());
               if (universal.art != null) {
                   Bitmap songimage = BitmapFactory.decodeByteArray(universal.art, 0, universal.art.length);
                   universal.image.setImageBitmap(songimage);
               } else {
                   universal.image.setImageResource(R.drawable.def);

               }
           }
       }
        TranslateAnimation animation =new TranslateAnimation(30,-universal.songname.getWidth()-60,0,0);
        animation.setDuration(10000);
        universal.songname.startAnimation(animation);

    }

    public static Context getAppContext() {
        return context1;
    }

    public  void next(View view) {
        if (universal.currentindex != universal.filelist.size() - 1) {
            universal.mediaPlayer.stop();
            universal.mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(universal.filelist.get(++universal.currentindex).get("path")));
            universal.mediaPlayer.start();
            universal.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    next(new View(context1));
                }
            });

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                universal.play.setImageDrawable(getDrawable(ic_media_pause));
            }
            universal.songname.setText(universal.filelist.get(universal.currentindex).get("name"));
            universal.currentsongname=universal.filelist.get(universal.currentindex).get("name");
            universal.meta.setDataSource(universal.filelist.get(universal.currentindex).get("path"));
            universal.art = universal.meta.getEmbeddedPicture();


            Intent notificationIntent = new Intent(getApplicationContext(),MainActivity.class);
            universal.notification.setContentText(universal.currentsongname);
            universal.notification.setOngoing(true);

            notificationIntent.putExtra("path",universal.filelist.get(universal.currentindex).get("path"));
            notificationIntent.putExtra("name",universal.filelist.get(universal.currentindex).get("name"));
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent intent = PendingIntent.getActivity(getApplicationContext(),0,notificationIntent,PendingIntent.FLAG_CANCEL_CURRENT);
            universal.notification.setContentIntent(intent);


            universal.notificationManager.notify(001,universal.notification.build());

            if (universal.art != null) {
                Bitmap songimage = BitmapFactory.decodeByteArray(universal.art, 0, universal.art.length);
               universal.image.setImageBitmap(songimage);
            } else {
                universal.image.setImageResource(R.drawable.def);

            }
        }

        TranslateAnimation animation =new TranslateAnimation(30,-universal.songname.getWidth()-60,0,0);
        animation.setDuration(10000);
        universal.songname.startAnimation(animation);


    }


}
