package com.example.positivepathways;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by macbookpro on 11/17/17.
 */

public abstract class OptionsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //locks the screen to portrait, since we do not have pretty landscape view of the app
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //initializes the menu with the option "Login"
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        //if the user selects "Login", then the Login page is run.
        if(item.getItemId()==R.id.login_menu){
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
        }
        if(item.getItemId()== android.R.id.home)
            onBackPressed();
        return true;
    }
}
