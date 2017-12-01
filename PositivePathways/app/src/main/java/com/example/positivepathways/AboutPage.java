package com.example.positivepathways;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutPage extends OptionsActivity {
    private String about = "Positive Pathways is a program sponsored by Educate Tomorrow Corps. " +
            "and the State of Florida, Department of Children and Families.\n" +
            "\n" +
            "The program’s mission is to help increase the number of " +
            "former foster students who graduate with post-secondary credentials " +
            "by uniting and supporting the student support professionals who work with these young adults.\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);
        TextView about_info = (TextView) findViewById(R.id.about_info);
        about_info.setText(about);
    }
}
