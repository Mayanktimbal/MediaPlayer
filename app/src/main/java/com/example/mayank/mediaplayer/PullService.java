package com.example.mayank.mediaplayer;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;


public class PullService extends Service {
public static  boolean isRunning = false;
    @Override
    public void onCreate() {
        super.onCreate();
        isRunning=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    Toast.makeText(getApplicationContext(), "news  song", Toast.LENGTH_SHORT).show();
                }
            }
        }).start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
return Service.START_STICKY;

    }





    @Override
    public IBinder onBind(Intent intent)  {
        return  null;
          }


}

