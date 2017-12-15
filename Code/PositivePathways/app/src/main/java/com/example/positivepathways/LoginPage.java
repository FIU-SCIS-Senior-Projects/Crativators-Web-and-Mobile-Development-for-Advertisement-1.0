package com.example.positivepathways;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;

public class LoginPage extends AppCompatActivity {
    private String username = "";
    private String password = "";
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        final EditText userInput = (EditText) findViewById(R.id.user_input);
        final EditText userPass = (EditText) findViewById(R.id.password_input);
        error = (TextView) findViewById(R.id.login_incorrect);

        Button login = (Button)findViewById(R.id.login_button);
        login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        username = userInput.getText().toString().trim();
                        password = userPass.getText().toString().trim();
                        new login().execute();
                    }
                });

    }

    /**
     * This class runs the login task asynchronously. All http calls MUST be made through
     * AsyncTask or else the main thread will be backed up.
     */
    private class login extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... params) {
            LoginHandler loggedIn = ((LoginHandler) getApplicationContext());
            try {
                if(username.equals(""))
                    loggedIn.loginHandler(1);
                else
                    loggedIn.loginHandler(username, password);
                try {
                    loggedIn.loginServer();
                }
                catch (Exception e){
                    e.printStackTrace();
                    return e.toString();
                }
            } catch (JSONException e) {
                System.out.println("JSON" + e.toString());
                return e.toString();
            }
            if(loggedIn.getLoginToken().equals(""))
                return "Failure";

            return "Success";
        }

        protected void onPostExecute(String result){
            if(result.equals("Success")){
                //returns to the previous page if login was successful
                finish();
            }
            else{
                error.setVisibility(View.VISIBLE);
            }
        }
    }

}
