package com.example.mayank.mediaplayer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class Main2Activity extends AppCompatActivity {

   static String path;
  ListView listview ;
   static TextView number;
static Context context;

static  MyCustomAdapter myCustomAdapter;
static Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

universal.filelist= new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_SETTINGS}, 1);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
context=getApplicationContext();
        final Intent main = new Intent(this, MainActivity.class);
       resources=getResources();
        path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
         universal.progressBar= (ProgressBar) findViewById(R.id.progress);
         universal.progressBar.setVisibility(View.GONE);
        number = (TextView) findViewById(R.id.textView3);
        listview = (ListView) findViewById(R.id.listview);
        File filename= new File(path+"filename.ser");


if(!filename.exists()  ) {
  Getfiles obj = new Getfiles();
    obj.execute();


}
        else
{
    getfrommemory();
    getpicturesfrommemory();
    number.setText(Integer.toString(universal.filelist.size()));
}



       myCustomAdapter= new MyCustomAdapter(getApplicationContext());
        listview.setAdapter(myCustomAdapter);







        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                         universal.currentindex=i;
                                                    String path = universal.filelist.get(i).get("path");
                                                            String name = universal.filelist.get(i).get("name");;

                                                    main.putExtra("path", path);
                                                    main.putExtra("name", name);
                                                    startActivity(main);
                                                }
                                            }


            );

    }



 static   ArrayList<HashMap<String,String>>   getplayfilename(String rootpath)
    {


        try
        {
            File rootFolder = new File(rootpath);
            File[] files = rootFolder.listFiles();

            for(File file : files)
            {

                if(file.isDirectory()){
                    if(file.listFiles()!= null)
                    {getplayfilename(file.getAbsolutePath());}
                    else
                    {break;}
                }
                else if (file.getName().endsWith(".mp3"))
                {
                    HashMap hashMap= new HashMap();
                    hashMap.put("name",file.getName().toString());
                    hashMap.put("path",file.getAbsolutePath().toString());

                    universal.filelist.add(hashMap);

                }
            }

            return universal.filelist;

        }catch (Exception e)
        {
            return null;
        }

    }
  static  void senttomemory()
    {

        try{


            FileOutputStream filenameOut = new FileOutputStream(path+"filename.ser");


            ObjectOutputStream nameout = new ObjectOutputStream(filenameOut);


            nameout.writeObject(universal.filelist);

            nameout.close();

            filenameOut.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    void getfrommemory()
    {
           try {
                FileInputStream filenameIn = new FileInputStream(path+"filename.ser");

                ObjectInputStream filein = new ObjectInputStream(filenameIn);

             universal.filelist= (ArrayList<HashMap<String,String>>) filein.readObject();

                filein.close();

                filenameIn.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


    }

    static void  getpicturesfrommemory()
    {
        File path2 = new  File(path+"/albumart/");
        if(!path2.exists())
        {
            path2.mkdirs();
        }
        for (int i=0;i<universal.filelist.size();i++)
        {
            try {
                FileInputStream filein= new FileInputStream(path2.getAbsolutePath()+"/"+i+".png");
                BufferedInputStream buff= new BufferedInputStream(filein);
                universal.images.add(BitmapFactory.decodeStream(buff)) ;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }



    }



   public void refresh(View view) throws InterruptedException {
       universal.progressBar.setVisibility(View.VISIBLE);
universal.filelist.clear();
       universal.images.clear();
    final Getfiles obj= new Getfiles();
     obj.execute();
       Main2Activity.number.setText(Integer.toString(universal.filelist.size()));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        universal.notificationManager.cancel(001);
    }



static void getpicture()
{
    try{
    for(int i=0;i<universal.filelist.size();i++) {
    universal.meta.setDataSource(universal.filelist.get(i).get("path"));
    universal.art = universal.meta.getEmbeddedPicture();



    if (universal.art != null) {
        Bitmap songimage = BitmapFactory.decodeByteArray(universal.art, 0, universal.art.length);

       universal.images.add(songimage);


    } else {
      Bitmap song= BitmapFactory.decodeResource(resources,R.drawable.def);
       universal.images.add(song);

    }
}}
    catch (RuntimeException e)
    {
        Bitmap song= BitmapFactory.decodeResource(resources,R.drawable.def);
        universal.images.add(song);
    }
}



}
