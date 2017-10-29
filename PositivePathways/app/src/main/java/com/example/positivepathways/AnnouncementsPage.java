package com.example.positivepathways;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AnnouncementsPage extends AppCompatActivity {

    private static final String TAG = "AnnouncementsPage";
    private String input;
    private String title, agenda, date, minutes;
    private HttpURLConnection connection = null;
    private URL addr;
    private ArrayList<String[]> announcements = new ArrayList<String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements_page);
        ListView list = (ListView) findViewById(R.id.recentAnnouncements);

        //onCreate, the program loads all the previous announcements from the database
        try{
            addr = new URL(this.getString(R.string.server_url)+this.getString(R.string.announcement));
            connection = (HttpURLConnection) addr.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-length", "0");
            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);
            connection.connect();
            int status = connection.getResponseCode();

            try{
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                System.out.println(sb.toString());
                try {
                    JSONObject fetched = new JSONObject(sb.toString());
                    title = fetched.getString("title");
                    agenda = fetched.getString("agenda");
                    minutes = fetched.getString("minutes");
                    date = fetched.getString("date");
                    String[] fetched_announcement = {title, agenda, minutes, date};
                    announcements.add(fetched_announcement);
                }
                catch(JSONException e){
                    System.out.println("JSON" + e.toString());
                }


            }
            finally{
                connection.disconnect();
            }

        }
        catch (IOException e){
            throw new RuntimeException(e);
        }

        //this is supposed to display them, but I need to find a way to load the three variables properly
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, announcements);
        list.setAdapter(adapter);
    }

    //called when the user requests to add an announcement
    public boolean checkLogin(){
        return true;
    }

    //call the fragment to create a new announcement
    public void addAnnouncement(View view) throws IOException, JSONException {
        //if the user is logged in, then they can post to the server.
        //The server address can be modified in strings.xml. That way we don't have to modify its use throughout the app
        if(checkLogin()){
            String strDate, user_title = "", user_agenda = "", user_minutes = "";

            addr = new URL(this.getString(R.string.server_url)+this.getString(R.string.announcement));
            connection = (HttpURLConnection) addr.openConnection();

            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-length", "0");
            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);
            connection.connect();

            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");//dd/MM/yyyy
            Date now = new Date();
            strDate = sdfDate.format(now);

            JSONObject send = new JSONObject();
            send.put("title", user_title);
            send.put("agenda", user_agenda);
            send.put("minutes", user_minutes);
            send.put("date", strDate);

            connection.disconnect();
        }
    }
}
