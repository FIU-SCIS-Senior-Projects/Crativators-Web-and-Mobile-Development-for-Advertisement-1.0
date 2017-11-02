package com.example.positivepathways;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MeetingsPage extends AppCompatActivity {

    private static final String TAG = "AnnouncementsPage";
    private String input;
    private String title, agenda, date, minutes, token;
    private HttpURLConnection connection = null;
    private URL addr;
    private ArrayList<String[]> meetings = new ArrayList<String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Meetings page");
        setContentView(R.layout.activity_meetings_page);
        new retrieveAnnouncements().execute();

        ListView list = (ListView) findViewById(R.id.recentMeetings);
        Resources res = getResources();
        //this is supposed to display them, but I need to find a way to load the three variables properly
        MeetingAdapter adapter = new MeetingAdapter(this, meetings, res);
        list.setAdapter(adapter);
    }

    //Loads the values into the listview
    public void populateListView() throws JSONException, Exception{
        //onCreate, the program loads all the previous announcements from the database
        try {
            //addr = new URL(this.getString(R.string.server_url));
            addr = new URL(this.getString(R.string.server_url) + "/" + this.getString(R.string.meeting));
            connection = (HttpURLConnection) addr.openConnection();
            //this is a get request
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            //connection.setRequestProperty("Content-Type", this.getString(R.string.meeting) + "; charset=UTF-8");
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

    //called when the user requests to add an announcement
    public boolean checkLogin() {
        //loads the current context of the login handler
        LoginHandler loggedIn = ((LoginHandler)getApplicationContext());
        token = loggedIn.getLoginToken();
        return true;
    }

    //call the fragment to create a new announcement
    public void addAnnouncement(View view) throws IOException, JSONException {
        //if the user is logged in, then they can post to the server.
        //The server address can be modified in strings.xml. That way we don't have to modify its use throughout the app
        if (checkLogin()) {
            String strDate, user_title = "", user_agenda = "", user_minutes = "";

            addr = new URL(this.getString(R.string.server_url));
            connection = (HttpURLConnection) addr.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-length", this.getString(R.string.meeting) + "; charset=UTF-8");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //builds the json object
            JSONObject send = new JSONObject();
            send.put("title", user_title);
            send.put("agenda", user_agenda);
            send.put("minutes", user_minutes);
            send.put("token", token);

            OutputStream os = connection.getOutputStream();
            os.write(send.toString().getBytes("UTF-8"));
            os.close();

            // read the response
            InputStream in = new BufferedInputStream(connection.getInputStream());
            String response = org.apache.commons.io.IOUtils.toString(in, "UTF-8");

            //whether the input was successful.
            JSONObject reply = new JSONObject(response);

            in.close();
            connection.disconnect();

            //TODO: At this point, I want to refresh the page to show the newest announcement
        }
    }

    private class retrieveAnnouncements extends AsyncTask<Void, Void, String> {
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
    }
}
