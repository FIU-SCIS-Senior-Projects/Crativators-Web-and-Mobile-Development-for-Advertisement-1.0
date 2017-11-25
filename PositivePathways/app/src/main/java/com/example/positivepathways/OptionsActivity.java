package com.example.positivepathways;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by macbookpro on 11/17/17.
 */

public class OptionsActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.login_menu){
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
        }
        if(item.getItemId()== android.R.id.home)
            onBackPressed();
        return true;
    }
}
