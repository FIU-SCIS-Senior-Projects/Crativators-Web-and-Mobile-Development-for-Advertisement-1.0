package com.example.positivepathways;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.main_page_dropdown, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        OnSwipeTouchListener onSwipeTouchListener = new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onSwipeLeft() {
                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
        };

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
    public void openAnnouncements(View view){
        Intent intent = new Intent(this, AnnouncementsPage.class);
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
}
