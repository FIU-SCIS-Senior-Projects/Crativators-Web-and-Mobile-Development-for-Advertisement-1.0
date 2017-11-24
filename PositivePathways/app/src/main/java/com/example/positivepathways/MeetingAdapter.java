package com.example.positivepathways;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Loads the meetings to fit into the meeting_layout.xml file.
 */
public class MeetingAdapter extends BaseAdapter {
    private Activity activity;  //current activity calling the adapter
    private ArrayList<String[]> data;   //the arraylist of string arrays
    private static LayoutInflater inflater = null;
    public Resources res;
    String [] entry = null;
    int i = 0;

    public MeetingAdapter(Activity activity, ArrayList<String[]> data, Resources res){
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
        public TextView date;
        public TextView title;
        public TextView message;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View rowView = convertView;
        ViewHolder holder;

        //attaches the values of the other layout to the holder.
        if(convertView == null){
            rowView = inflater.inflate(R.layout.meeting_layout, parent, false);

            holder = new ViewHolder();
            holder.date = (TextView)rowView.findViewById(R.id.dateText);
            holder.title = (TextView)rowView.findViewById(R.id.titleText);
            holder.message = (TextView)rowView.findViewById(R.id.meetingDescription);

            rowView.setTag(holder);

        }
        else{
            holder = (ViewHolder)rowView.getTag();
        }

        if(data.size()<=0){
            holder.message.setText("No Data");
        }

        else{
            entry = null;
            entry = data.get(position);
            holder.date.setText(entry[3]);
            holder.title.setText(entry[0]);
            holder.message.setText(entry[1] + "\n\n" + entry[2]);
        }

        return rowView;
    }
}
