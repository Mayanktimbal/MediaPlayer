package com.example.mayank.mediaplayer;


import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

/**
 * Created by mayank on 12/18/2018.
 */

public class Getfiles extends AsyncTask
{

    @Override
    protected Object doInBackground(Object[] objects) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";

        Main2Activity.getplayfilename(path);
       Main2Activity.getpicture();
        Main2Activity.senttomemory();
SendPic obj = new SendPic();
        obj.start();

        return null;
    }



    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Main2Activity.myCustomAdapter.notifyDataSetChanged();
        universal.progressBar.setVisibility(View.GONE);
        Main2Activity.number.setText(Integer.toString(universal.filelist.size()));
        Toast.makeText(Main2Activity.context, "updated", Toast.LENGTH_SHORT).show();

    }
}

