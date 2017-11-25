package com.example.positivepathways;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class MembersPage extends AppCompatActivity{
    private String input;
    private String profile_pic_url = "", name = "", phone = "", email = "", token;
    private HttpURLConnection connection = null;
    private URL addr;
    private static ArrayList<String[]> profiles = new ArrayList<String[]>();
    //previously, we used the jsonarray retrieved from the http call
    //but in a search, the results will not map correctly with the jsonarray
    //so we insert each jsonobject into the array.
    private ArrayList<JSONObject> visibleProfiles = new ArrayList<JSONObject>();
    private ListView list;  //the adapter inflater to see the simple profiles
    private boolean search = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().getIntExtra("memberOrPartner", 1) == 0)
            setTitle("Partners");
        else if(getIntent().getIntExtra("memberOrPartner", 1) == 1)
            setTitle("Members");
        else {
            setTitle("Search Results");
            input = getIntent().getStringExtra("userSearch");
            search = true;
        }

        setContentView(R.layout.activity_members_page);


        list = (ListView) findViewById(R.id.membersList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> l, View v, int position, long id){
                Intent intent = new Intent();
                intent.setClass(MembersPage.this, DetailedProfile.class);
                System.out.println("You clicked " + position + "!!");
                //passes the profile to the DetailedProfile page
                intent.putExtra("fullProfile", visibleProfiles.get(position).toString());
                System.out.println(visibleProfiles.get(position).toString());
                startActivity(intent);
            }
        });
        new retrieveProfiles().execute();
    }

    //Loads the values into the listview
    public void populateListView() throws JSONException, Exception{
        //onCreate, the program loads all the previous announcements from the database
        try {
            profiles.clear();
            //addr = new URL(this.getString(R.string.server_url) + this.getString(R.string.memberProfiles));
            if(getIntent().getIntExtra("memberOrPartner", 1) == 1) {
                addr = new URL(this.getString(R.string.server_url) + this.getString(R.string.memberProfiles));
            }
            else if(getIntent().getIntExtra("memberOrPartner", 1) == 0){
                addr = new URL(this.getString(R.string.server_url) + this.getString(R.string.partnerProfiles));
            }
            else{
                addr = new URL(this.getString(R.string.server_url) + this.getString(R.string.profiles));
            }

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
                JSONArray fetched;
                fetched = new JSONArray(response);
                for(int i = 0; i < fetched.length(); i ++) {
                    name = "";  profile_pic_url = ""; phone = ""; email = "";
                    name = fetched.getJSONObject(i).getString("name");
                    profile_pic_url = fetched.getJSONObject(i).getString("picUrl");
                    if(fetched.getJSONObject(i).has("phone"))
                        phone = fetched.getJSONObject(i).getString("phone");
                    if(fetched.getJSONObject(i).has("email"))
                        email = fetched.getJSONObject(i).getString("email");
                    String[] fetched_profile = {profile_pic_url, name, phone, email};

                    //runs only if this is a search function
                    if(search){
                        //checks if the input does not partially match with either the name or school
                        if(!(name.toLowerCase().contains(input.toLowerCase()) ||
                                fetched.getJSONObject(i).getString("institution").
                                        toLowerCase().contains(input.toLowerCase())))
                            //if neither match, go to the next iteration
                            continue;
                    }

                    visibleProfiles.add(fetched.getJSONObject(i));
                    profiles.add(fetched_profile);
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

    private class retrieveProfiles extends AsyncTask<Void, Void, String> {
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

        //after the list is filled
        protected void onPostExecute(String result){
            Resources res = MembersPage.this.getResources();
            if(profiles.size()>0) {
                ProfileAdapter adapter = new ProfileAdapter(MembersPage.this, MembersPage.profiles, res);
                MembersPage.this.list.setAdapter(adapter);
            }
        }
    }
}
