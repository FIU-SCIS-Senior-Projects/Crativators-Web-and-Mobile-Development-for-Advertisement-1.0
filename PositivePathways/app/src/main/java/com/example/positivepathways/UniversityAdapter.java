package com.example.positivepathways;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import layout.OvalImageView;

/**
 * Created by macbookpro on 11/16/17.
 */

public class UniversityAdapter extends BaseAdapter {
    private Activity activity;  //current activity calling the adapter
    private ArrayList<String[]> data;   //the arraylist of string arrays
    private static LayoutInflater inflater = null;
    public Resources res;
    String [] entry = null; //{image, name, university, position}
    int i = 0;

    public UniversityAdapter(Activity activity, ArrayList<String[]> data, Resources res){
        this.activity = activity;
        this.data = data;
        this.res = res;

        inflater = (LayoutInflater)this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //I think this is required when implementing BaseAdapter
    public int getCount(){
        if(data.size() <= 0)
            return 1;
        return data.size();
    }

    //I think this is also required for implementing BaseAdapter
    public Object getItem(int position){
        return position;
    }

    //I think this is also required...
    public long getItemId(int position){
        return position;
    }

    //small class to hold the three fields.
    //TODO: I must change this to match the current layout for the application
    public static class ViewHolder{
        public ImageView photo;
        public TextView name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View rowView = convertView;
        UniversityAdapter.ViewHolder holder;

        //attaches the values of the other layout to the holder.
        if(convertView == null){
            rowView = inflater.inflate(R.layout.university_layout, parent, false);

            holder = new UniversityAdapter.ViewHolder();
            holder.photo = (ImageView) rowView.findViewById((R.id.university_logo_pic));
            holder.name = (TextView)rowView.findViewById(R.id.university_name_adapter);
            rowView.setTag(holder);

        }
        else{
            holder = (UniversityAdapter.ViewHolder)rowView.getTag();
        }
        if(data.size()>0) {
            entry = null;
            entry = data.get(position);
            //set holder.photo set to entry[0]
            new UniversityAdapter.DownloadImageTask(holder.photo).execute(entry[0]);
            System.out.println(entry[1]);
            holder.name.setText(entry[1]);
        }
        return rowView;
    }

    //Asynchronously loads the image into the imageview
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap photo = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                photo = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return photo;
        }

        protected void onPostExecute(Bitmap result) {

            if(result == null)
                bmImage.setImageResource(R.drawable.ic_final_logo);
            else
                bmImage.setImageBitmap(result);
        }
    }
}
