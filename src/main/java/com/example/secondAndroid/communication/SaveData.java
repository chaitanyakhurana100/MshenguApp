package com.example.secondAndroid.communication;

import android.database.Cursor;
import android.os.AsyncTask;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;
import com.example.secondAndroid.MainActivity;
import com.example.secondAndroid.database.DBAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: chaitanya
 * Date: 2013/09/05
 * Time: 8:53 PM
 * To change this template use File | SettingsActivity | File Templates.
 */
public class SaveData extends AsyncTask<String,Void,String> {
    Context ctx;
    DBAdapter myDb;
    MainActivity activity;
    public   SaveData(Context context)
    {
        ctx=context;
        myDb=new DBAdapter(context);
    }

    @Override
    protected String doInBackground(String... params)
    {
        myDb.open();
        String table,unit_id,pump_out,wash_bucket,suction_out,recharge_bucket,scrub_floor,clean_perimeter,report_incident,unit_id_dep,latitude,longitude,site,date;
        table= params[0];

        if(table.equals("services".toString()))
        {
            unit_id=params[1];
            pump_out=params[2];
            wash_bucket=params[3];
            suction_out=params[4];
            recharge_bucket=params[5];
            scrub_floor=params[6];
            clean_perimeter=params[7];
            report_incident=params[8];
            latitude=params[9];
            longitude=params[10];
            date=params[11];
            myDb.insertRowInTableServices(unit_id,pump_out,wash_bucket,suction_out,recharge_bucket,scrub_floor,clean_perimeter,report_incident,latitude,longitude,date);
            myDb.close();
            return "services";
        }
        if(table.equals("deployment".toString()) )
        {
            unit_id=params[1];
            latitude=params[2];
            longitude=params[3];
            site=params[4];
            date=params[5];
            myDb.insertRowInTableDeployment(unit_id,latitude,longitude,site,date);
            myDb.close();
            return "deployment";
        }

        if(table.equals("geoplot".toString()) )
        {
            site=params[1];
            latitude=params[2];
            longitude=params[3];
            date=params[4];
            myDb.insertRowInTableGeoPlot(site,latitude,longitude,date);
            myDb.close();
            return "geoplot";
        }

        return null;

    }

    protected void onPostExecute(String tableName)
    {
        Toast.makeText(ctx,"Data saved on your device",Toast.LENGTH_SHORT).show();
        if(tableName.equals("services".toString()))
        {
            pullDataFromTableServices();
        }
        if(tableName.equals("deployment".toString()))
        {
            pullDataFromTableDeployment();
        }
        if(tableName.equals("geoplot".toString()))
        {
            pullDataFromTableGeoPlot();
        }


    }

    public void pullDataFromTableServices()
    {
         myDb.open();
        String row_id_tag,table_tag,unit_id_tag,pump_out_tag,wash_bucket_tag,suction_out_tag,recharge_bucket_tag,scrub_floor_tag,clean_perimeter_tag,report_incident_tag,latitude_tag,longitude_tag,date_tag;
        Cursor cursor=myDb.getAllRowsFromTableServices();
        if(cursor.moveToFirst())
        {
            do{

                row_id_tag=cursor.getString(0);
                unit_id_tag=cursor.getString(1);
                pump_out_tag=cursor.getString(2);
                wash_bucket_tag=cursor.getString(3);
                suction_out_tag=cursor.getString(4);
                recharge_bucket_tag=cursor.getString(5);
                scrub_floor_tag=cursor.getString(6);
                clean_perimeter_tag=cursor.getString(7);
                report_incident_tag=cursor.getString(8);
                latitude_tag=cursor.getString(9);
                longitude_tag=cursor.getString(10);
                date_tag=cursor.getString(11);
                String[] data=new String[]{"services",unit_id_tag,pump_out_tag,wash_bucket_tag,suction_out_tag,recharge_bucket_tag,scrub_floor_tag,clean_perimeter_tag,report_incident_tag,latitude_tag,longitude_tag,date_tag};
                PostData postData=new PostData(ctx);
                postData.execute(data[0],data[1],data[2],data[3],data[4],data[5],data[6],data[7],data[8],data[9],data[10],data[11]); //POSTING TO SERVER

            }while (cursor.moveToNext());
        }
        cursor.close();
        myDb.close();

    }

    public void pullDataFromTableDeployment()
    {


        myDb.open();
        String unit_id_tag,latitude_tag,longitude_tag,site_tag,date;
        Cursor cursor=myDb.getAllRowsFromTableDeployment();
        if(cursor.moveToFirst())
        {
            do{

                unit_id_tag=cursor.getString(1);
                latitude_tag=cursor.getString(2);
                longitude_tag=cursor.getString(3);
                site_tag=cursor.getString(4);
                date=cursor.getString(5);
                String[] data=new String[]{"deployment",unit_id_tag,latitude_tag,longitude_tag,site_tag,date};
                PostData postData=new PostData(ctx);
                postData.execute(data[0],data[1],data[2],data[3],data[4],data[5]); //POSTING TO SERVER

            }while (cursor.moveToNext());
        }
        cursor.close();
        myDb.close();

    }

    public void pullDataFromTableGeoPlot()
    {
        myDb.open();
        String latitude_tag,longitude_tag,site_tag,date;
        Cursor cursor=myDb.getAllRowsFromTableGeoPlot();
        if(cursor.moveToFirst())
        {
            do{
                site_tag=cursor.getString(1);
                latitude_tag=cursor.getString(2);
                longitude_tag=cursor.getString(3);
                date=cursor.getString(4);
                String[] data=new String[]{"geoplot",site_tag,latitude_tag,longitude_tag,date};
                PostData postData=new PostData(ctx);
                postData.execute(data[0],data[1],data[2],data[3],data[4]); //POSTING TO SERVER

            }while (cursor.moveToNext());
        }
        cursor.close();
        myDb.close();


    }




}
