package com.example.positivepathways;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MeetingsPage extends OptionsActivity {

    private String input;
    private String title, agenda, date, minutes, token;
    private HttpURLConnection connection = null;
    private URL addr;
    private static ArrayList<String[]> meetings = new ArrayList<String[]>();
    private ArrayList<JSONObject> visibleMeetings = new ArrayList<JSONObject>();
    private boolean checkOld = false;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("Meetings page");
        setContentView(R.layout.activity_meetings_page);
        if(getIntent().getIntExtra("future", 0) == 0) {
            checkOld = true;
            setTitle("Past Meetings");
        }
        else{
            setTitle("Future Meetings");
        }
        new retrieveMeetings().execute();

        //starts the class to create a new meeting
        Button newMeeting = (Button)findViewById(R.id.add_meeting);
        newMeeting.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MeetingsPage.this, CreateNewMeeting.class);
                        intent.putExtra("preFilled", 0);
                        startActivity(intent);
                    }
                }
        );

        list = (ListView) findViewById(R.id.recentMeetings);

        /**
         * If a meeting is clicked, the edit screen appears.
         */
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> l, View v, int position, long id){
                Intent intent = new Intent();
                intent.setClass(MeetingsPage.this, CreateNewMeeting.class);
                System.out.println("You clicked " + position + "!!");

                //sends the meeting information to CreateNewMeeting
                intent.putExtra("fullMeeting", visibleMeetings.get(position).toString());
                intent.putExtra("preFilled", 1);
                System.out.println(visibleMeetings.get(position).toString());
                startActivity(intent);
            }
        });
    }

    //Loads the values into the listview
    public void populateListView() throws JSONException, Exception{
        //onCreate, the program loads all the previous meetings from the database
        try {
            meetings.clear();
            addr = new URL(this.getString(R.string.server_url) + this.getString(R.string.meeting));
            connection = (HttpURLConnection) addr.openConnection();
            //this is a get request
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoInput(true);
            //fills it into an input
            InputStream in = new BufferedInputStream(connection.getInputStream());
            String response = org.apache.commons.io.IOUtils.toString(in, "UTF-8");

            in.close();

            try {
                //parses the array of data into the arraylist of string[]
                JSONArray fetched = new JSONArray(response);
                for(int i = 0; i < fetched.length(); i ++) {
                    title = fetched.getJSONObject(i).getString("title");
                    agenda = fetched.getJSONObject(i).getString("agenda");
                    minutes = fetched.getJSONObject(i).getString("minutes");
                    date = fetched.getJSONObject(i).getString("date");
                    String[] fetched_meeting = {title, agenda, minutes, date};

                    //if we're looking at past meetings, we ignore the future ones
                    if(checkOld){
                        if(fetched.getJSONObject(i).getBoolean("past") == false)
                            continue;
                    }
                    //if we're looking at future meetings, we must ignore the past ones
                    else{
                        if(fetched.getJSONObject(i).getBoolean("past") == true)
                            continue;
                    }
                    visibleMeetings.add(fetched.getJSONObject(i));
                    meetings.add(fetched_meeting);
                }
            } catch (JSONException e) {
                System.out.println("JSON" + e.toString());
            } finally {
                connection.disconnect();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private class retrieveMeetings extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params){
            try {
                populateListView();
            }
            catch(Exception e){
                System.out.println("JSON" + e.toString());
            }
            return "Blank message";
        }

        protected void onPostExecute(String result){
            Resources res = MeetingsPage.this.getResources();
            //this is supposed to display them, but I need to find a way to load the three variables properly
            MeetingAdapter adapter = new MeetingAdapter(MeetingsPage.this, MeetingsPage.meetings, res);
            list.setAdapter(adapter);
        }
    }
}
