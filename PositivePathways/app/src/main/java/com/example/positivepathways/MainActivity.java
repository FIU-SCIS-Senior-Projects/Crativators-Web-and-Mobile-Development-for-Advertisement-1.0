package com.example.positivepathways;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*try{
            login();
        }
        catch(Exception e){
            System.out.println("Exception" + e.toString());
        }*/

        OnSwipeTouchListener onSwipeTouchListener = new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onSwipeLeft() {
                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
        };

    }

    private void login() throws JSONException, Exception{

        LoginHandler loggedIn = ((LoginHandler)getApplicationContext());
        try {
            loggedIn.loginHandler(1);
            try {
                loggedIn.loginServer();
            }
            catch(Exception e){
                System.out.println("Exception" + e.toString());
            }
        }
        catch (JSONException e){
            System.out.println("JSON" + e.toString());
        }
    }

    /**
     * Links to the University profile page on the presss of a button
     * @param view
     */
    public void openProfile(View view){
        Intent intent = new Intent(this, UniversityListPage.class);
        startActivity(intent);
    }

    /**
     * Open announcements.
     *
     * Future conditions: Only show button if there is a new announcement
     * @param view
     */
    public void openMeetings(View view){
        Intent intent = new Intent(this, MeetingsPage.class);
        startActivity(intent);
    }


    /**
     * Open the view page
     * @param view
     */
    public void openMissionStatement(View view){
        Intent intent = new Intent(this, AboutPage.class);
        startActivity(intent);
    }

    /**
     * Opens the program list page.
     * @param view
     */
    public void openProgramList(View view){


    }

    public void openLoginPage(View view){


    }
}
