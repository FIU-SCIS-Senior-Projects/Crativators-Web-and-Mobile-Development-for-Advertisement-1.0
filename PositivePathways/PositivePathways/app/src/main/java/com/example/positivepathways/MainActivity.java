package com.example.positivepathways;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;

public class MainActivity extends OptionsActivity {
    private Spinner replacement_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        runSpinner();

        //assigns the searchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.searchBar);
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, MembersPage.class);
                intent.putExtra("memberOrPartner", 3); //search is 3
                intent.putExtra("userSearch", query);

                //starts the Search Results list
                startActivity(intent);
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getSupportMenuInflater().inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchBar)
                .getActionView();
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, MembersPage.class);
                intent.putExtra("memberOrPartner", 3); //members is 1
                intent.putExtra("userSearch", query);
                startActivity(intent);
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

    public void runSpinner(){
        replacement_menu = (Spinner) findViewById(R.id.main_spinner);
        replacement_menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> l, View v, int position, long id) {
                String choice = replacement_menu.getItemAtPosition(position).toString();
                System.out.println("LABEL:" + replacement_menu.getItemAtPosition(position).toString());
                switch (choice) {
                    case "Login":
                        Intent intent = new Intent(MainActivity.this, LoginPage.class);
                        startActivity(intent);
                        return;
                    default:
                        return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                return;
            }
        });
    }

    /**
     * Links to the University profile page on the presss of a button
     * @param view
     */
    public void openMembers(View view){
        Intent intent = new Intent(this, MembersPage.class);
        intent.putExtra("memberOrPartner", 1); //members is 1
        startActivity(intent);
    }

    public void openPartners(View view){
        Intent intent = new Intent(this, MembersPage.class);
        intent.putExtra("memberOrPartner", 0); //partner is 0
        startActivity(intent);
    }

    /**
     * Open announcements.
     *
     * Future conditions: Only show button if there is a new announcement
     * @param view
     */
    public void openPastMeetings(View view){
        Intent intent = new Intent(this, MeetingsPage.class);
        intent.putExtra("future", 0); //past is 0
        startActivity(intent);
    }

    public void openFutureMeetings(View view){
        Intent intent = new Intent(this, MeetingsPage.class);
        intent.putExtra("future", 1); //future is 1
        startActivity(intent);
    }


    /**
     * Open the about page
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
        Intent intent = new Intent(this, UniversityListPage.class);
        startActivity(intent);

    }
}
