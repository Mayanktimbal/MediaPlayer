package com.example.mayank.mediaplayer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;



public class MyCustomAdapter extends BaseAdapter implements ListAdapter {

    private Context context;
    String path;



    public MyCustomAdapter( Context context) {

        this.context = context;
    }

    @Override
    public int getCount() {
        return universal.filelist.size();
    }

    @Override
    public Object getItem(int pos) {
        return universal.filelist.get(pos);
    }

    @Override
    public long getItemId(int pos) {
       return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.my_custom_list_layout, null);
        }


        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(universal.filelist.get(position).get("name"));
        View finalView = view;

        ImageView addBtn = (ImageView) finalView.findViewById(R.id.imageView3);
        try {
            addBtn.setImageBitmap(universal.images.get(position));
        }
        catch(Exception e)
        {

        }




        //Handle buttons and add onClickListeners



        path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";

        final File location = new File (path+"/notes");




        return view;
    }
}
