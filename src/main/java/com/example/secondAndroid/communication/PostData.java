package com.example.secondAndroid.communication;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.secondAndroid.R;
import com.example.secondAndroid.communication.Connection;
import com.example.secondAndroid.database.DBAdapter;
import com.example.secondAndroid.resource.GeoplotResource;
import com.example.secondAndroid.resource.UnitDeliveryResource;
import com.example.secondAndroid.resource.UnitServiceResource;
import org.springframework.http.*;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.HttpEntity;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
//import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: chaitanya
 * Date: 2013/09/12
 * Time: 11:32 PM
 * To change this template use File | SettingsActivity | File Templates.
 */
class PostData extends AsyncTask<String,Void,String[]> {
    //This class will post data to Server and it takes values from local database.
    Context context;
    DBAdapter myDb1;
    String[] response;
    String response_dep;
    View layout_green,layout_red,view_toast_success,view_toast_fail;


    Boolean pump_out_bool,wash_bucket_bool,suction_out_bool,rech_bucket_bool,scrub_floor_bool,clean_peri_bool;

    protected PostData(Context ctx)
    {
        context=ctx;
        myDb1=new DBAdapter(context);
        LayoutInflater inflater1 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view_toast_success=inflater1.inflate(R.layout.toast_success,null);
        view_toast_fail=inflater1.inflate(R.layout.toast_fail,null);
        layout_green  = inflater1.inflate(R.layout.toast_success,(ViewGroup) view_toast_success.findViewById(R.id.toast_layout_green));
        layout_red =inflater1.inflate(R.layout.toast_fail,(ViewGroup) view_toast_fail.findViewById(R.id.toast_layout_red));


    }

    @Override
    protected String[] doInBackground(String... params)
    {


        if(params[0].equals("services".toString()))
        {
            String unitId=params[1],pumpOut=params[2],washBucket=params[3],suctionOut=params[4],rechargeBucket=params[5],scrubFloor=params[6],cleanPerimeter=params[7],reportIncident=params[8],latitude=params[9],longitude=params[10],date=params[11];

            try {


                final UnitServiceResource  unitServiceResource = new UnitServiceResource();
                unitServiceResource.setLatitude(latitude);
                unitServiceResource.setLongitude(longitude);
                unitServiceResource.setUnitId(unitId);
                Map<String, Boolean> tasks = new HashMap<String, Boolean>();
                if(pumpOut.equals("yes"))
                {
                    pump_out_bool=true;
                }
                else {
                    pump_out_bool=false;
                }
                if(washBucket.equals("yes"))
                {
                    wash_bucket_bool=true;
                }
                else {
                    wash_bucket_bool=false;
                }

                if(suctionOut.equals("yes"))
                {
                    suction_out_bool=true;
                }
                else {
                    suction_out_bool=false;
                }
                if(scrubFloor.equals("yes"))
                {
                    scrub_floor_bool=true;
                }
                else {
                    scrub_floor_bool=false;
                }
                if(rechargeBucket.equals("yes"))
                {
                    rech_bucket_bool=true;
                }
                else {
                    rech_bucket_bool=false;
                }
                if(cleanPerimeter.equals("yes"))
                {
                    clean_peri_bool=true;
                }
                else {
                    clean_peri_bool=false;
                }

                tasks.put("pumpOut", pump_out_bool);
                tasks.put("washBucket", wash_bucket_bool);
                tasks.put("suctionOut", suction_out_bool);
                tasks.put("scrubFloor", scrub_floor_bool);
                tasks.put("rechargeBacket", rech_bucket_bool);
                tasks.put("cleanPerimeter", clean_peri_bool);

                unitServiceResource.setServices(tasks);
                unitServiceResource.setIncident(reportIncident);
                unitServiceResource.setDate(date);
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setContentType(new MediaType("application", "json"));

                /*Connection connection=new Connection(context);
                final  String url=connection.getURL()+"serviceunit";
                HttpEntity<UnitServiceResource> requestEntity = new HttpEntity<UnitServiceResource>(unitServiceResource, connection.getContentType());
                ResponseEntity<String> responseEntity = connection.getConnection().exchange(url, HttpMethod.POST, requestEntity, String.class);
                response= new String[]{"services",responseEntity.getBody(),date,unitId};
                return response;*/
                Connection connection=new Connection(context);
                //final  String url=connection.getURL()+"serviceunit";
                //HttpEntity<UnitServiceResource> requestEntity = new HttpEntity<UnitServiceResource>(unitServiceResource, connection.getContentType());
                //ResponseEntity<String> responseEntity = connection.getConnection().exchange(url, HttpMethod.POST, requestEntity, String.class);
                response_dep=connection.postServiceData(unitId,pumpOut,washBucket,suctionOut,rechargeBucket,scrubFloor,cleanPerimeter,reportIncident,latitude,longitude);


                response= new String[]{"services",response_dep,date,unitId};
                return response;

            } catch (Exception e) {
                android.util.Log.d("Message",e.getMessage()+e.toString());
            }

            return null;
        }
        if(params[0].equals("deployment".toString()))
        {
            String unitId=params[1],latitude=params[2],longitude=params[3],site=params[4],date=params[5];
            try {
                final UnitDeliveryResource unitDeliveryResource = new UnitDeliveryResource();
                unitDeliveryResource.setUnitId(unitId);
                unitDeliveryResource.setLatitude(latitude);
                unitDeliveryResource.setLongitude(longitude);
                unitDeliveryResource.setSiteId(site);
                unitDeliveryResource.setDate(date);
               /* Connection connection=new Connection(context);
                response_dep=connection.postDeployment(unitDeliveryResource);
                response= new String[]{"deployment",response_dep,date,unitId};

                return response;  */

                Connection connection=new Connection(context);
                response_dep=connection.postDeploymentData(unitDeliveryResource);
                return new String[]{"deployment",response_dep,date,unitId};

            } catch (Exception e) {
                 android.util.Log.d("Message2",e.getMessage()+e.toString());

            }

            return null;

        }

        if(params[0].equals("geoplot".toString()))
        {

            String site=params[1],latitude=params[2],longitude=params[3],date=params[4];

            try {

                final GeoplotResource geoplotResource = new GeoplotResource();
                geoplotResource.setSitename(site);
                geoplotResource.setLatitude(latitude);
                geoplotResource.setLongitude(longitude);
                geoplotResource.setDate(date);
                /*
                Connection connection=new Connection(context);
                response_dep=connection.postGeoPlot(geoplotResource);
                response= new String[]{"geoplot",response_dep,site,date};

                return response; */
                Connection connection=new Connection(context);
                response_dep=connection.postGeoPlotData(geoplotResource);
                response= new String[]{"geoplot",response_dep,site,date};
                return  response;



            } catch (Exception e) {
                 android.util.Log.d("Message2",e.getMessage()+e.toString());

            }

            return null;

        }

        return null;

    }


    protected void onPostExecute(String[] data)
    {   boolean value;


        try{
            if(data[0].isEmpty())
            {
                Toast.makeText(context,"No response",Toast.LENGTH_LONG).show();
            }
            if(data[0].equals("services".toString()))
            {
                //Toast.makeText(context,"Data uploaded on server for Unit Id "+data,Toast.LENGTH_LONG).show();
                try{
                    String resp=data[1],date=data[2],unitId=data[3];


                    if(resp.equals("WITHIN".toString()))
                    {
                        myDb1.open();
                        TextView text = (TextView) layout_green.findViewById(R.id.text);
                        text.setText("Service data uploaded on server for Unit Id "+unitId);
                        Toast toast = new Toast(context);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout_green);
                        toast.show();
                        value=myDb1.deleteRowFromTableServices(0,unitId,date);
                        myDb1.close();
                        MediaPlayer mp = MediaPlayer.create(context, R.raw.sound);
                        mp.start();
                    }
                    else if(resp.equals("OUT".toString()))
                    {
                        myDb1.open();
                        TextView text = (TextView) layout_red.findViewById(R.id.text);
                        text.setText("Sorry! You are away from deployment area. Change location and try again!");
                        Toast toast = new Toast(context);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout_red);
                        toast.show();
                        value=myDb1.deleteRowFromTableServices(0,unitId,date);
                        myDb1.close();
                    }else if(resp.equals("NOT".toString()))
                    {
                        myDb1.open();
                        TextView text = (TextView) layout_red.findViewById(R.id.text);
                        text.setText("Sorry, Toilet unit does not exist. Please deploy it and try again!");
                        Toast toast = new Toast(context);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout_red);
                        toast.show();
                        value=myDb1.deleteRowFromTableServices(0,unitId,date);
                        myDb1.close();

                    }
                    else
                    {
                        Toast.makeText(context,"Could not connect to server!"+resp,Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e)
                {
                    Toast.makeText(context,"Could not connect to Internet",Toast.LENGTH_LONG).show();

                }
            }

            if(data[0].equals("deployment".toString()))
            {
                try{
                    String resp=data[1],unitId=data[3],date=data[2];
                    if(resp.equals("ok"))
                    {
                        myDb1.open();


                        TextView text = (TextView) layout_green.findViewById(R.id.text);
                        text.setText("Deployment data uploaded on server for Unit Id "+unitId);
                        Toast toast = new Toast(context);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout_green);
                        toast.show();
                        value=myDb1.deleteRowFromTableDeployment(0,unitId,date);
                        myDb1.close();
                        MediaPlayer mp = MediaPlayer.create(context, R.raw.sound);
                        mp.start();
                    }
                    else if(resp.length()>2)
                    {
                        Toast.makeText(context,"Toilet is already deployed at "+resp,Toast.LENGTH_LONG).show();
                        myDb1.open();
                        value=myDb1.deleteRowFromTableDeployment(0,unitId,date);
                        myDb1.close();

                    }else{
                        Toast.makeText(context,"Connection to server failed",Toast.LENGTH_LONG).show();

                    }
                }catch (Exception e)
                {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();

                }

            }


            if(data[0].equals("geoplot".toString()))
            {
                 try{
                    String resp=data[1],site=data[2],date=data[3];
                    if(resp.equals("ok".toString()))
                    {
                        myDb1.open();
                        TextView text = (TextView) layout_green.findViewById(R.id.text);
                        text.setText("Geo-plot data uploaded on server for Site "+site);
                        Toast toast = new Toast(context);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout_green);
                        toast.show();
                        value=myDb1.deleteRowFromTableGeoPlot(date);
                        myDb1.close();
                        MediaPlayer mp = MediaPlayer.create(context, R.raw.sound);
                        mp.start();
                    }else if(resp.equals("fail".toString()))
                    {
                        myDb1.open();
                        TextView text = (TextView) layout_red.findViewById(R.id.text);
                        text.setText("Site data already exists on server ");
                        Toast toast = new Toast(context);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout_red);
                        toast.show();
                        value=myDb1.deleteRowFromTableGeoPlot(date);
                        myDb1.close();

                    }
                    else
                    {
                        Toast.makeText(context,"Connection to server failed",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e)
                {
                    Toast.makeText(context,"Could not connect to Internet",Toast.LENGTH_LONG).show();

                }

            }

        }
       catch (Exception e)
        {
            Toast.makeText(context,"Server unreachable",Toast.LENGTH_LONG).show();
        }

    }


}