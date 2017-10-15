package com.example.positivepathways;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AnnouncementsPage extends AppCompatActivity {

    private static final String TAG = "AnnouncementsPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements_page);
        ListView list = (ListView) findViewById(R.id.recentAnnouncements);

        ArrayList<String> announcements = new ArrayList<>();
        announcements.add("Login to the live conference!");
        announcements.add("Login to the live conference!");
        announcements.add("Login to the live conference!");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, announcements);
        list.setAdapter(adapter);
    }
}
