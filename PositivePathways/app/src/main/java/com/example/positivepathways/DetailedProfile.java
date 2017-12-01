package com.example.positivepathways;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import layout.OvalImageView;

public class DetailedProfile extends OptionsActivity implements CheckDelete.CheckDeleteListener {
    private JSONObject profile_info;
    private String name = "", institution = "", position = "", email = "", phone = "", info = "",
    school_url = "", profile_pic = "", token = "", modifyProfileId = "", userType = "";
    private HttpURLConnection connection = null;
    private URL addr;
    private Boolean match = false; //TODO only display EDIT button if it is user's profile
    private ViewSwitcher profileViewSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void callOnCreate(){
        setContentView(R.layout.activity_detailed_profile);

        LoginHandler loggedIn = ((LoginHandler) getApplicationContext());
        userType = loggedIn.getUserType();

        //loads the profile object from the MembersPage
        try{
            profile_info = new JSONObject(getIntent().getStringExtra("fullProfile"));
            modifyProfileId = profile_info.getString("_id");
            setTitle(profile_info.getString("name"));
            fillContent();
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        //assigns the viewswitcher, which switches between the normal and edit views
        profileViewSwitcher = (ViewSwitcher) findViewById(R.id.profile_viewswitcher);

        Button edit_button = (Button) findViewById(R.id.edit);
        if(userType.equals("")){
            edit_button.setVisibility(View.INVISIBLE);
        }
        edit_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    //fills the edit boxes.
                    fillEditableContent();
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                profileViewSwitcher.showNext();
            }
        });

        Button finish_button = (Button) findViewById(R.id.edit_done);
        finish_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                new sendProfiletoDatabase().execute();
                // show the next view of ViewSwitcher
                profileViewSwitcher.showNext();
            }
        });

        Button delete = (Button)findViewById(R.id.profile_delete);
        if(userType.equals("Member")){
            delete.setVisibility(View.INVISIBLE);
        }
        delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doubleCheckDelete();
                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        //resets the screen of profiles
        callOnCreate();
    }

    /**
     * This class loads the profile information into the content view.
     * Unused information is hidden from the user
     * @throws JSONException
     */
    private void fillContent () throws JSONException{
        System.out.println(profile_info == null);
        if(profile_info != null){
            name = profile_info.getString("name");
            if(profile_info.has("institution"))
                institution = profile_info.getString("institution");
            if(profile_info.has("position"))
                position = profile_info.getString("position");
            if(profile_info.has("email"))
                email = profile_info.getString("email");
            if(profile_info.has("phone"))
                phone = profile_info.getString("phone");
            if(profile_info.has("brief"))
                info = profile_info.getString("brief");
            if(profile_info.has("profile_pic"))
                profile_pic = profile_info.getString("profile_pic");

            TextView view = (TextView)findViewById(R.id.name);
            view.setText(name);
            view = (TextView)findViewById(R.id.institution);
            view.setText(institution);
            if(institution.equals(""))
                view.setVisibility(View.GONE);

            view = (TextView)findViewById(R.id.position);
            view.setText(position);
            if(position.equals(""))
                view.setVisibility(View.GONE);

            view = (TextView)findViewById(R.id.email);
            view.setText(email);
            if(email.equals(""))
                view.setVisibility(View.GONE);

            view = (TextView)findViewById(R.id.phone);
            view.setText(phone);
            if(phone.equals(""))
                view.setVisibility(View.GONE);

            view = (TextView)findViewById(R.id.info);
            view.setText(info);
            if(info.equals(""))
                view.setVisibility(View.GONE);

            ImageView photo = (ImageView)findViewById(R.id.profilePic);
            new DownloadImageTask(photo).execute(profile_pic);
            new databaseCall().execute();
        }
    }

    /**
     * This class loads the profile information into an editable content view.
     * All input areas are visible to the user.
     * @throws JSONException
     */
    private void fillEditableContent () throws JSONException{
        System.out.println(profile_info == null);
        if(profile_info != null){
            name = profile_info.getString("name");
            if(profile_info.has("institution"))
                institution = profile_info.getString("institution");
            if(profile_info.has("position"))
                position = profile_info.getString("position");
            if(profile_info.has("email"))
                email = profile_info.getString("email");
            if(profile_info.has("phone"))
                phone = profile_info.getString("phone");
            if(profile_info.has("brief"))
                info = profile_info.getString("brief");
            if(profile_info.has("profile_pic"))
                profile_pic = profile_info.getString("profile_pic");

            EditText view = (EditText) findViewById(R.id.edit_name);
            view.setText(name);
            view = (EditText) findViewById(R.id.edit_institution);
            view.setText(institution);

            view = (EditText) findViewById(R.id.edit_position);
            view.setText(position);

            view = (EditText) findViewById(R.id.edit_email);
            view.setText(email);

            view = (EditText) findViewById(R.id.edit_phone);
            view.setText(phone);

            view = (EditText) findViewById(R.id.edit_info);
            view.setText(info);

            ImageView photo = (ImageView)findViewById(R.id.edit_profilePic);
            new DownloadImageTask(photo).execute(profile_pic);
            new databaseCall().execute();
        }
    }

    /**
     * Builds the JSON object that will be copied into the database.
     * @throws JSONException
     */
    private void buildJSONsendObject() throws JSONException{
        EditText view = (EditText) findViewById(R.id.edit_name);
        profile_info.put("name",view.getText().toString().trim());

        view = (EditText) findViewById(R.id.edit_institution);
        profile_info.put("institution",view.getText().toString().trim());

        view = (EditText) findViewById(R.id.edit_position);
        profile_info.put("position",view.getText().toString().trim());

        view = (EditText) findViewById(R.id.edit_email);
        profile_info.put("email",view.getText().toString().trim());

        view = (EditText) findViewById(R.id.edit_phone);
        profile_info.put("phone",view.getText().toString().trim());

        view = (EditText) findViewById(R.id.edit_info);
        profile_info.put("brief",view.getText().toString().trim());

        profile_info.put("token", token);
    }

    /**
     * I don't think each program should be a separate model
     * @throws Exception
     */
    private void getUniversityImage() throws Exception, JSONException{
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

        //matches the university logo to the correct profile
        JSONArray fetched = new JSONArray(response);
        for(int i = 0; i < fetched.length(); i ++){
            String university_name = fetched.getJSONObject(i).getString("name");
            if (institution.equals(university_name)){
                school_url = fetched.getJSONObject(i).getString("picUrl");
                break;
            }
        }

        connection.disconnect();
    }

    //This class is used because all http calls must be made asynchronously
    private class databaseCall extends AsyncTask<Void, Void, String>{
        protected String doInBackground(Void... params){
            try{
                getUniversityImage();
            }
            catch(Exception e){
                System.out.println("JSON" + e.toString());
            }
            return "Blank message";
        }

        //set the university image to institutionLogo
        protected void onPostExecute(String result){
            ImageView photo = (ImageView)findViewById(R.id.institutionLogo);
            new DownloadImageTask(photo).execute(school_url);

            photo = (ImageView)findViewById(R.id.edit_institutionLogo);
            new DownloadImageTask(photo).execute(school_url);
        }
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
                //loads the image from whatever website it's being hosted on
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
                //this is the placeholder image. If there is no image, we use the Positive Pathways logo
                bmImage.setImageResource(R.drawable.ic_final_logo);
            else
                bmImage.setImageBitmap(result);
        }
    }

    /**
     * Deletes the current meeting from the database
     * @throws IOException
     * @throws JSONException
     */
    private void deleteProfile() throws IOException, JSONException{
        if(checkLogin()) {
            addr = new URL(this.getString(R.string.server_url) + this.getString(R.string.profiles) + "/" + modifyProfileId);
            connection = (HttpURLConnection) addr.openConnection();

            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("x-access-token", token);

            if(connection.getResponseCode()==200)
                finish();
            /*else
                Toast.makeText(CreateNewMeeting.this, "Insufficient authorization to delete this meeting.",
                        Toast.LENGTH_LONG).show();*/
        }
    }

    /**
     * We have to make sure the user WANTS to delete.
     */
    private void doubleCheckDelete(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        CheckDelete checkDelete = new CheckDelete();
        checkDelete.setCancelable(false);
        checkDelete.setDialogTitle("Choose One");
        checkDelete.show(fragmentManager, "Yes/No Dialog");
    }

    @Override
    /**
     * For the dialog box. If the user clicks "Yes", then we delete the profile
     */
    public void onSelection(boolean choice){
        if(choice == true){
            new removeProfilefromDatabase().execute();
        }
    }

    //called when the user requests to update a profile or delete a profile
    public boolean checkLogin() {
        //loads the current context of the login handler
        LoginHandler loggedIn = ((LoginHandler)getApplicationContext());
        token = loggedIn.getLoginToken();
        if(token.equals("")) {
            System.out.println("User is not logged in");
            runOnUiThread(new Runnable(){
                public void run(){
                    Toast.makeText(DetailedProfile.this, "Please log in to modify a profile.", Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        }
        return true;
    }

    public void addProfile() throws IOException, JSONException {
        //if the user is logged in, then they can post to the server.
        //The server address can be modified in strings.xml. That way we don't have to modify its use throughout the app
        if (checkLogin()) {
            //if we're modifying a existing profile
            addr = new URL(this.getString(R.string.server_url) + this.getString(R.string.profiles) + "/" + modifyProfileId);
            connection = (HttpURLConnection) addr.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-type", "application/json; charset=UTF-8");

            //TODO: create new profiles from within the app
            /*else {
                addr = new URL(this.getString(R.string.server_url) + this.getString(R.string.meeting));
                connection = (HttpURLConnection) addr.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
            }*/
            connection.setRequestProperty("Content-length", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            //builds the json object
            buildJSONsendObject();

            OutputStream os = connection.getOutputStream();
            os.write(profile_info.toString().getBytes("UTF-8"));
            os.close();

            // read the response
            InputStream in = new BufferedInputStream(connection.getInputStream());
            String response = org.apache.commons.io.IOUtils.toString(in, "UTF-8");

            //whether the input was successful.
            JSONObject reply = new JSONObject(response);

            in.close();
            connection.disconnect();

            System.out.println(reply.getString("message"));

            if(reply.getString("message").equals("Failed to authenticate token.")){
                System.out.println("Failed to Add meeting");
                runOnUiThread(new Runnable(){
                    public void run(){
                        Toast.makeText(DetailedProfile.this, "Changes have not been applied.\n" +
                                "Insufficient user credentials.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
                finish();

        }
    }

    private class sendProfiletoDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                addProfile();
            } catch (Exception e) {
                System.out.println("JSON" + e.toString());
            }
            return "Blank message";
        }
    }

    private class removeProfilefromDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                deleteProfile();
            } catch (Exception e) {
                System.out.println("JSON" + e.toString());
            }
            return "Blank message";
        }
    }

}
