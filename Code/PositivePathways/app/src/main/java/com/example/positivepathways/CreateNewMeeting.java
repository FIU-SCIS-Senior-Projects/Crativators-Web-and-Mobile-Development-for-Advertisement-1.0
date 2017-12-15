package com.example.positivepathways;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by macbookpro on 10/18/17.
 */

public class CreateNewMeeting extends OptionsActivity implements CheckDelete.CheckDeleteListener {
    private String title = "";
    private String date = "";
    private String message = "";
    private String minutes = "";
    private String token = "";  //we need to be logged in to update or create a meeting
    private String modify = ""; //if we're updating a meeting
    private boolean past = false; //to update the status of the meeting
    private boolean put_call = false;
    private HttpURLConnection connection = null;
    private URL addr;
    private JSONObject meetingToEdit; //to keep the json object throughout the activity
    private CheckBox pastBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputmeeting);

        //Matches with the edittext boxes
        final EditText userTitle = (EditText) findViewById(R.id.meeting_title);
        final EditText userBody = (EditText) findViewById(R.id.meeting_body);
        final EditText userMinutes = (EditText) findViewById(R.id.meeting_minutes);

        //TODO Possible future use is to plan meetings in advance.
        final EditText userDate = (EditText) findViewById(R.id.meeting_date);

        pastBox = (CheckBox)findViewById(R.id.pastCheck);

        //if we're updating a meeting
        if(getIntent().getIntExtra("preFilled", 0) == 1){
            put_call = true;
            try {
                meetingToEdit = new JSONObject(getIntent().getStringExtra("fullMeeting"));
                fillContent();
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }

        Button submit = (Button)findViewById(R.id.submit_meeting_button);

        submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //sets the title, message, and the minutes to build the json in addMeeting
                        title = userTitle.getText().toString().trim();
                        message = userBody.getText().toString().trim();
                        minutes = userMinutes.getText().toString().trim();
                        if(CreateNewMeeting.this.put_call) {
                            past = pastBox.isChecked();
                            System.out.println("Past meeting: " + past);
                        }
                        //checks if all the information is filled
                        if(title.equals("") || message.equals("")){
                            Toast.makeText(CreateNewMeeting.this, "Some information is missing.",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        new sendMeetingtoDatabase().execute();
                    }
                }
        );

        Button delete = (Button)findViewById(R.id.meeting_delete);
        delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doubleCheckDelete();
                    }
                }
        );
    }

    /**
     * Fills the boxes if the user wanted to edit the information.
     * @throws JSONException
     */
    private void fillContent() throws JSONException{
        if(meetingToEdit != null){
            title = meetingToEdit.getString("title");
            minutes = meetingToEdit.getString("minutes");
            message = meetingToEdit.getString("agenda");

            EditText view = (EditText)findViewById(R.id.meeting_title);
            view.setText(title);

            view = (EditText)findViewById(R.id.meeting_body);
            view.setText(message);

            view = (EditText)findViewById(R.id.meeting_minutes);
            view.setVisibility(View.VISIBLE);
            view.setText(minutes);

            if(meetingToEdit.getBoolean("past") == false)
                pastBox.setVisibility(View.VISIBLE);
            //pastBox.setChecked();

            Button delete = (Button)findViewById(R.id.meeting_delete);
            delete.setVisibility(View.VISIBLE);

            modify = meetingToEdit.getString("_id");
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
    public void onSelection(boolean choice){
        if(choice == true){
            new removeMeetingfromDatabase().execute();
        }
    }

    /**
     * Deletes the current meeting from the database
     * @throws IOException
     * @throws JSONException
     */
    private void deleteMeeting() throws IOException, JSONException{
        if(checkLogin()) {
            addr = new URL(this.getString(R.string.server_url) + this.getString(R.string.meeting) + "/" + modify);
            connection = (HttpURLConnection) addr.openConnection();

            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("x-access-token", token);

            if(connection.getResponseCode()==200)
                finish();
            else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(CreateNewMeeting.this, "Meeting could not be deleted.\n" +
                                "Insufficient user credentials.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    //called when the user requests to add a meeting
    public boolean checkLogin() {
        //loads the current context of the login handler
        LoginHandler loggedIn = ((LoginHandler)getApplicationContext());
        token = loggedIn.getLoginToken();
        if(token.equals("")) {
            System.out.println("User is not logged in");
            runOnUiThread(new Runnable(){
                public void run(){
                    Toast.makeText(CreateNewMeeting.this, "Please log in.", Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        }
        return true;
    }

    /**
     * Makes a database call and creates a new meeting
     * @throws IOException
     * @throws JSONException
     */
    public void addMeeting() throws IOException, JSONException {
        //if the user is logged in, then they can post to the server.
        //The server address can be modified in strings.xml. That way we don't have to modify its use throughout the app
        if (checkLogin()) {
            System.out.println("put_call test:" + put_call);
            //if we're modifying a existing meeting
            if(put_call) {
                addr = new URL(this.getString(R.string.server_url) + this.getString(R.string.meeting) + "/" + modify);
                connection = (HttpURLConnection) addr.openConnection();
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-type", "application/json; charset=UTF-8");
            }
            //if we're creating an entirely new meeting
            else {
                addr = new URL(this.getString(R.string.server_url) + this.getString(R.string.meeting));
                connection = (HttpURLConnection) addr.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setRequestProperty("Content-type", "application/json; charset=UTF-8");
            }
            connection.setRequestProperty("Content-length", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            //builds the json object
            JSONObject send = new JSONObject();
            send.put("title", title);
            send.put("agenda", message);
            send.put("token", token);
            send.put("minutes", minutes);
            send.put("past", past);

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

            System.out.println(reply.getString("message"));

            if(reply.getString("message").equals("Failed to authenticate token.")){
                System.out.println("Failed to Add meeting");
                runOnUiThread(new Runnable(){
                    public void run(){
                        Toast.makeText(CreateNewMeeting.this, "Changes have not been applied.\n" +
                                "Insufficient user credentials.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
                finish();

        }
    }

    /*
    These two classes are built so that the http calls are made asynchronously
     */

    private class sendMeetingtoDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                addMeeting();
            } catch (Exception e) {
                System.out.println("JSON" + e.toString());
            }
            return "Blank message";
        }
    }

    private class removeMeetingfromDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                deleteMeeting();
            } catch (Exception e) {
                System.out.println("JSON" + e.toString());
            }
            return "Blank message";
        }
    }
}
