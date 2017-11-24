package com.example.positivepathways;

import android.app.Application;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The purpose of this class is to maintain a global (logged_in) token.
 *
 * The most important method is getLoginToken()
 *
 * To use this, this class needs
 */

public class LoginHandler extends Application {
    private String loginToken = "";
    private JSONObject login = new JSONObject();
    private URL url;

    /**
     * for testing purposes
     * @param bleh my testing variable
     * @throws JSONException
     */
    public void loginHandler(int bleh) throws JSONException{
        if(bleh == 1) //admin
            login = new JSONObject().put("username", "SampleAdmin")
                    .put("password", "administrator");
        else if (bleh == 2) //member
            login = new JSONObject().put("username", "SampleMember")
                    .put("password", "member");
        else if (bleh == 3) //moderator
            login = new JSONObject().put("username", "SampleMod")
                    .put("password", "moderator");

        //creates the login function asynchronously
        new login().execute();
    }

    /**
     * use for the real app
     * @param user user-entered username
     * @param pass user-entered password
     * @throws JSONException
     */
    public void loginHandler(String user, String pass) throws JSONException{
        login = new JSONObject().put("username", user)
                .put("password", pass);
        new login().execute();
    }

    /**
     * Connects into the server and retrieves the login token for use in the rest of the app.
     * @throws Exception
     * @throws JSONException
     */
    public void loginServer() throws Exception, JSONException{
        url =  new URL(this.getString(R.string.server_url) + this.getString(R.string.login));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");

        OutputStream os = connection.getOutputStream();
        os.write(login.toString().getBytes("UTF-8"));
        os.close();

        InputStream in = new BufferedInputStream(connection.getInputStream());
        String response = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
        JSONObject reply = new JSONObject(response);

        in.close();
        connection.disconnect();

        //Alternatively, one can check the requestcode.
        if(reply.getBoolean("success") == true) {
            setLoginToken(reply.getString("token"));
            System.out.println("Token success: " + reply.getString("token"));
        }

        else
            System.out.println(reply.getString("message"));

    }

    /**
     * Private class runs the http calls asynchronously, because http cannot be called on the main thread
     */
    private class login extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... params) {
            try {
                loginServer();
            } catch (Exception e) {
                System.out.println("JSON" + e.toString());
            }
            return "Blank message";
        }
    }
    public String getLoginToken() {
            return loginToken;
        }

    private void setLoginToken(String token) {
            loginToken = token;
        }
}
