package com.example.secondAndroid.communication;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;
import com.example.secondAndroid.database.DBAdapter;
import com.example.secondAndroid.resource.GeoplotResource;
import com.example.secondAndroid.resource.SiteResource;
import com.example.secondAndroid.resource.UnitDeliveryResource;
import com.example.secondAndroid.resource.UnitServiceResource;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.DynamicAny.NameValuePair;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;
import sun.org.mozilla.javascript.internal.json.JsonParser;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chaitanya
 * Date: 2013/09/16
 * Time: 9:26 PM
 * To change this template use File | SettingsActivity | File Templates.
 */
public class Connection {
  DBAdapter mydb;
    Context ctx;

    public Connection(Context context)
    {
        mydb=new DBAdapter(context);
        ctx=context;

    }

    public HttpHeaders getContentType() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType("application", "json"));
        return requestHeaders;

    }



    public String postDeployment(UnitDeliveryResource unitDeliveryResource) {
        final String url =getURL()+"tagunit";
        HttpEntity<UnitDeliveryResource> requestEntity = new HttpEntity<UnitDeliveryResource>(unitDeliveryResource, getContentType());
        ResponseEntity<String> responseEntity = getConnection().exchange(url, HttpMethod.POST, requestEntity, String.class);
        return responseEntity.getBody();
    }

    public String postDeploymentData(UnitDeliveryResource unitDeliveryResource)
    {
        String data="";

        String unit_id=unitDeliveryResource.getUnitId().toString();
        String longitude=unitDeliveryResource.getLongitude().toString();
        String latitude=unitDeliveryResource.getLatitude().toString();
        String site_name=unitDeliveryResource.getSiteId();
        String  text="";
        // Create data variable for sent values to server
        try{
        data = URLEncoder.encode("unit_id", "UTF-8")
                + "=" + URLEncoder.encode(unit_id, "UTF-8");

        data += "&" + URLEncoder.encode("longitude", "UTF-8") + "="
                + URLEncoder.encode(longitude, "UTF-8");

        data += "&" + URLEncoder.encode("latitude", "UTF-8")
                + "=" + URLEncoder.encode(latitude, "UTF-8");

        data += "&" + URLEncoder.encode("site", "UTF-8")
                + "=" + URLEncoder.encode(site_name, "UTF-8");



        BufferedReader reader=null;

        // Send data

            // Defined URL  where to send data
            URL url = new URL(getURL()+"deployment.php");

            // Send POST data request
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;


            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line);
            }


            text = sb.toString();
            reader.close();

        }
        catch(Exception ex)
        {

        }
    //return text;
        return text;

    }

    public String testConnection(String param_url)
    {
        final String url =param_url+"test.php";
        String response="not found";
        InputStream inputStream=null;
        ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
        //http post
        try{


            DefaultHttpClient httpClient=new DefaultHttpClient();
            HttpResponse httpResponse= httpClient.execute(new HttpGet(url));
            inputStream=httpResponse.getEntity().getContent();
            if(inputStream!=null)
            {
                response=convertInputStreamToString(inputStream);

            }
        }catch(Exception e){
            Log.e("log_tag", "Error in http connection"+e.toString());
        }
            return response;

    }

    // convert inputstream to String
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


    public String postGeoPlot(GeoplotResource geoplotResource) {
        final String url =getURL()+"geoplot";
        HttpEntity<GeoplotResource> requestEntity = new HttpEntity<GeoplotResource>(geoplotResource, getContentType());
        ResponseEntity<String> responseEntity = getConnection().exchange(url, HttpMethod.POST, requestEntity, String.class);
        return responseEntity.getBody();
    }



    public String postServiceData(String... params) {
        String unitId=params[0],pumpOut=params[1],washBucket=params[2],suctionOut=params[3],rechargeBucket=params[4],scrubFloor=params[5],cleanPerimeter=params[6],reportIncident=params[7],latitude=params[8],longitude=params[9];

        String data="";
        String lon_data="",lat_data="",unit_id_data="",report_data="",pump_out_data="no",wash_bucket_data="no",suction_data="no",scrub_floor_data="no",clean_perimeter_data="no";
        //String unit_id=unitDeliveryResource.getUnitId().toString();
        lon_data=longitude;
        lat_data=latitude;
        unit_id_data=unitId;
        report_data=reportIncident;
        pump_out_data=pumpOut;
        wash_bucket_data=washBucket;
       suction_data=suctionOut;
        clean_perimeter_data=cleanPerimeter;
        scrub_floor_data=scrubFloor;

        String  text="";
        // Create data variable for sent values to server
        try{
            data = URLEncoder.encode("unit_id", "UTF-8")
                    + "=" + URLEncoder.encode(unit_id_data, "UTF-8");

            data += "&" + URLEncoder.encode("longitude", "UTF-8") + "="
                    + URLEncoder.encode(lon_data, "UTF-8");

            data += "&" + URLEncoder.encode("latitude", "UTF-8")
                    + "=" + URLEncoder.encode(lat_data, "UTF-8");

            data += "&" + URLEncoder.encode("pump", "UTF-8")
                    + "=" + URLEncoder.encode(pump_out_data, "UTF-8");

            data += "&" + URLEncoder.encode("wash", "UTF-8")
                    + "=" + URLEncoder.encode(wash_bucket_data, "UTF-8");

            data += "&" + URLEncoder.encode("suction", "UTF-8")
                    + "=" + URLEncoder.encode(suction_data, "UTF-8");

            data += "&" + URLEncoder.encode("clean", "UTF-8")
                    + "=" + URLEncoder.encode(clean_perimeter_data, "UTF-8");

            data += "&" + URLEncoder.encode("scrub", "UTF-8")
                    + "=" + URLEncoder.encode(scrub_floor_data, "UTF-8");

            data += "&" + URLEncoder.encode("report", "UTF-8")
                    + "=" + URLEncoder.encode(report_data, "UTF-8");

            BufferedReader reader=null;

            // Send data

            // Defined URL  where to send data
            URL url = new URL(getURL()+"service.php");

            // Send POST data request
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;


            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line);
            }


            text = sb.toString(); //text is 'ok' if data is received by server
            reader.close();

        }
        catch(Exception ex)
        {

        }

        return text;



    }




    public String postGeoPlotData(GeoplotResource geoplotResource) {


        String data="";

        //String unit_id=unitDeliveryResource.getUnitId().toString();
        String longitude=geoplotResource.getLongitude().toString();
        String latitude=geoplotResource.getLatitude().toString();
        String site_name=geoplotResource.getSitename().toString();
        String  text="";
        // Create data variable for sent values to server
        try{
            data = URLEncoder.encode("site", "UTF-8")
                    + "=" + URLEncoder.encode(site_name, "UTF-8");

            data += "&" + URLEncoder.encode("longitude", "UTF-8") + "="
                    + URLEncoder.encode(longitude, "UTF-8");

            data += "&" + URLEncoder.encode("latitude", "UTF-8")
                    + "=" + URLEncoder.encode(latitude, "UTF-8");

            BufferedReader reader=null;

            // Send data

            // Defined URL  where to send data
            URL url = new URL(getURL()+"geoplot.php");

            // Send POST data request
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;


            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line);
            }


            text = sb.toString(); //text is 'ok' if data is received by server
            reader.close();

        }
        catch(Exception ex)
        {

        }

        return text;



    }



    public RestTemplate getConnection() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        return restTemplate;
    }


    public List<SiteResource> getSites(int size) {
        String siteSize=String.valueOf(size);
        final String url =getURL()+"sites/"+siteSize;
        Log.d("url",url);
        List<SiteResource> sites = new ArrayList<SiteResource>();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
        HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        System.out.println("The URL IS !!!!!" + url);
        ResponseEntity<SiteResource[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, SiteResource[].class);
        SiteResource[] events = responseEntity.getBody();

        for (SiteResource event : events) {
            sites.add(event);
        }
        Log.d("sites",sites.toString());
        return sites;
    }


    public Object[] getSiteList() {

        //List<SiteResource> sites = new ArrayList<SiteResource>();
        ArrayList<String> site_names=new ArrayList<String>();
        ArrayList<String> site_ids=new ArrayList<String>();
        SiteResource[] events;
        //ArrayList<SiteResource> sites = new ArrayList<SiteResource>();
        final String url =getURL()+"sites.php";

        // get JSON data from URL
        JSONParser jParser = new JSONParser();
        String json = jParser.getJSONFromUrl(url);

        try{

            JSONArray jsonArray=new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {

            try {
                JSONObject c = jsonArray.getJSONObject(i);
                site_names.add(c.getString("name"));
                site_ids.add(c.getString("id"));
                //sites.add();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }   }catch (Exception e){return null;}
        return new Object[]{site_names,site_ids};
    }




    public String getURL()
    {
        String url_val="";
        mydb.open();
        Cursor cursor= mydb.getAllRowsFromTableSettings();
        if(cursor.moveToFirst())
        {
            url_val=cursor.getString(1);
        }
        mydb.close();
        return url_val;

    }


}
