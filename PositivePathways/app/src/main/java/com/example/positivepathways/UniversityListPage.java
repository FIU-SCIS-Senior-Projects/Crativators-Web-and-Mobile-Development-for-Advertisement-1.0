package com.example.positivepathways;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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

/**
 * This class loads the list of university images and connects them to the corresponding profile.
 */
public class UniversityListPage extends OptionsActivity {
    private String input;
    private String picURL = "", name = "";
    private HttpURLConnection connection = null;
    private URL addr;
    private static ArrayList<String[]> universities = new ArrayList<String[]>();
    private ListView list;
    private JSONObject profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_list_page);
        list = (ListView) findViewById(R.id.university_logos);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> l, View v, int position, long id) {

                //gets the corresponding profile and loads it in the detailed profile view.
                name = universities.get(position)[1];
                new databaseCall().execute();
                if (profile != null) {
                    Intent intent = new Intent();
                    intent.setClass(UniversityListPage.this, DetailedProfile.class);
                    System.out.println("You clicked " + position + "!!");
                    //TODO: Get corresponding personal information and send to the detailed profile page
                    intent.putExtra("fullProfile", profile.toString());
                    System.out.println(profile.toString());
                    startActivity(intent);
                }
            }
        });
        new retrieveInstitutions().execute();
    }

    //Loads the values into the listview
    public void populateListView() throws JSONException, Exception {
        //onCreate, the program loads all the previous announcements from the database
        try {
            universities.clear();
            addr = new URL(this.getString(R.string.server_url) + this.getString(R.string.program));
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
                for (int i = 0; i < fetched.length(); i++) {
                    name = fetched.getJSONObject(i).getString("name");
                    picURL = fetched.getJSONObject(i).getString("picUrl");
                    String[] fetched_info = {picURL, name};
                    universities.add(fetched_info);
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

    /**
     * I don't think each program should be a separate model
     *
     * @throws Exception
     */
    private void getProfile() throws Exception, JSONException {
        addr = new URL(this.getString(R.string.server_url) + this.getString(R.string.memberProfiles));
        connection = (HttpURLConnection) addr.openConnection();
        //this is a get request
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setDoInput(true);
        //fills it into an input
        InputStream in = new BufferedInputStream(connection.getInputStream());
        String response = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
        in.close();

        //matches the university logo to the correct profile
        JSONArray fetched = new JSONArray(response);
        for (int i = 0; i < fetched.length(); i++) {
            String university_name = fetched.getJSONObject(i).getString("institution");
            if (name.equals(university_name)) {
                profile = fetched.getJSONObject(i);
                break;
            }
        }

        connection.disconnect();
    }

    //asynchronously gets the corresponding profile. All http calls MUST be asynchronous
    private class databaseCall extends AsyncTask<Void, Void, String> {
        protected String doInBackground(Void... params) {
            try {
                getProfile();
            } catch (Exception e) {
                System.out.println("JSON" + e.toString());
            }
            return "Blank message";
        }

    }

    private class retrieveInstitutions extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                populateListView();
            } catch (Exception e) {
                System.out.println("JSON" + e.toString());
            }
            return "Blank message";
        }

        protected void onPostExecute(String result) {
            Resources res = UniversityListPage.this.getResources();
            //this is supposed to display them, but I need to find a way to load the three variables properly
            UniversityAdapter adapter = new UniversityAdapter(UniversityListPage.this, UniversityListPage.universities, res);
            list.setAdapter(adapter);
        }
    }
}
