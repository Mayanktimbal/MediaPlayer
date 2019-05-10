package com.example.mayank.mediaplayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.mayank.mediaplayer.Main2Activity.path;
import static com.example.mayank.mediaplayer.Main2Activity.resources;

/**
 * Created by mayank on 12/30/2018.
 */

public class SendPic  extends Thread{
    @Override
    public void run() {
        super.run();
        File path2 = new  File(path+"/albumart/");
        if(!path2.exists())
        {
            path2.mkdirs();
        }

        for (int i = 0; i < universal.images.size(); i++) {
            try {

                FileOutputStream outputStream = new FileOutputStream(path + "albumart/" + i + ".png");
                universal.images.get(i).compress(Bitmap.CompressFormat.PNG, 50, outputStream);
                outputStream.flush();
                outputStream.close();
            }
            catch(FileNotFoundException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            catch (NullPointerException e)
            {
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(path + "albumart/" + i + ".png");
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                Bitmap song= BitmapFactory.decodeResource(resources,R.drawable.def);
                song.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            }
        }
    }
    }

