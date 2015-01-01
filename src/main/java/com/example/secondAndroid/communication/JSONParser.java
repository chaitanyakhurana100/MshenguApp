package com.example.secondAndroid.communication;

/**
 * Created with IntelliJ IDEA.
 * User: chaitanya
 * Date: 2014/05/08
 * Time: 5:15 PM
 * To change this template use File | Settings | File Templates.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

public class JSONParser {

    static InputStream iStream = null;
    static JSONArray jarray = null;
    static String json = "";

    public JSONParser() {
    }

    public String getJSONFromUrl(String url) {

        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        try {

            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.e("==>", "Failed to download file");
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
                /*
        // Parse String to JSON object
        try {
            jarray = new JSONArray( builder.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }     */

        // return JSON Object
        return builder.toString();
    }
}


