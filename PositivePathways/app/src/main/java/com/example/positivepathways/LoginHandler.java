package com.example.positivepathways;

import android.app.Application;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class keeps the logged in token if there is one.
 */

public class LoginHandler extends Application {
    private String loginToken = "";
    private JSONObject login = new JSONObject();
    private URL url;

    /**
     * for testing purposes
     * @param bleh
     * @throws JSONException
     */
    public void loginHandler(int bleh) throws JSONException{
        if(bleh == 1) //admin
            login = new JSONObject().put("username", "SampleAdmin")
                    .put("password", "administration");
        else if (bleh == 2) //member
            login = new JSONObject().put("username", "SampleMember")
                    .put("password", "member");
        else if (bleh == 3) //moderator
            login = new JSONObject().put("username", "SampleMod")
                    .put("password", "moderator");
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
    }

    /**
     * Connects into the server and retrieves the login token for use in the rest of the app.
     * @throws Exception
     * @throws JSONException
     */
    public void loginServer() throws Exception, JSONException{
        url =  new URL(this.getString(R.string.server_url));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", this.getString(R.string.login)+"; charset=UTF-8");
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
        if(reply.getBoolean("success") == true)
            setLoginToken(reply.getString("token"));

        else
            System.out.println(reply.getString("message"));

    }

    public String getLoginToken(){
        return loginToken;
    }

    public void setLoginToken(String token){
        loginToken = token;
    }
}
