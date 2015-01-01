package com.example.secondAndroid.sites;

import android.database.Cursor;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.example.secondAndroid.communication.Connection;
import com.example.secondAndroid.database.DBAdapter;
import com.example.secondAndroid.resource.SiteResource;
//import org.apache.http.HttpEntity;
import java.util.ArrayList;
import java.util.List;
//import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: chaitanya
 * Date: 2013/09/12
 * Time: 11:32 PM
 * To change this template use File | SettingsActivity | File Templates.
 */
public class Sites extends AsyncTask<String,Void,String> {
    //This class will post data to Server and it takes values from local database.
    Context context;

    DBAdapter myDb1;
    int i;


    public Sites(Context ctx)
    {
        context=ctx;
        myDb1=new DBAdapter(context);

    }

    @Override
    protected String doInBackground(String... params)
    {
        int total_sites=0;
        Object[] obj;
        //new code for PHP - start
        //ArrayList<String> sites_data=new ArrayList<String>();
        //ArrayList<String> ids=new ArrayList<String>();
        Connection con=new Connection(context);
        obj=con.getSiteList();

        ArrayList<String> sites_data=new ArrayList<String>((ArrayList)obj[0]);
        ArrayList<String> ids=new ArrayList<String>((ArrayList)obj[1]);

        total_sites=sites_data.size();

        myDb1.open();



               try{
                     if(total_sites!=0)
                     {
                            i=0;
                            Cursor cursor= myDb1.getAllRowsFromTableSite();
                            if(cursor.moveToFirst())
                            {
                                do{

                                    i++;
                                } while (cursor.moveToNext());
                            }
                            cursor.close();

                           // final Connection connection = new Connection(context);
                           // obj= connection.getSiteList();
                            if(sites_data.isEmpty())
                            {
                                myDb1.close();
                                return "empty";
                            }

                          if(total_sites!=i)
                          {
                            myDb1.deleteAllFromTableSite();

                            for (int i=0; i< sites_data.size();i++) {

                                myDb1.insertRowInTableSite(ids.get(i),sites_data.get(i));
                                //Log.d("chaitanya","insert row");
                            }
                            myDb1.close();

                            return "updated";
                          }else{

                              return "no change";
                          }
                     }
                }
                catch (Exception e)
                {     myDb1.close();
                    return e.getMessage();
                }
            return "updated";
        //new code for PHP - end


        //old code commented
       /* myDb1.open();
        try{
             i=0;
            Cursor cursor= myDb1.getAllRowsFromTableSite();
            if(cursor.moveToFirst())
            {
                do{

                    i++;
                } while (cursor.moveToNext());
            }


            cursor.close();
            final Connection connection = new Connection(context);
            List<SiteResource> sites = connection.getSites(i);
            if(sites.isEmpty())
            {
                myDb1.close();
                return "empty";
            }

            myDb1.deleteAllFromTableSite();

            for (SiteResource siteResource : sites) {

                myDb1.insertRowInTableSite(siteResource.getId(),siteResource.getName());
                Log.d("chaitanya","insert row");
            }
            myDb1.close();

            return "updated";
        }
        catch (Exception e)
        {     myDb1.close();
              return e.getMessage();
        }*/

    }


    protected void onPostExecute(String data)
    {
        try
        {
            Log.d("data0 ",data.toString());
            if(data.equals("empty".toString()))
            {
                Log.d("data1 ",data.toString());
             }

            if(data.equals("updated".toString()))
            {

                Toast.makeText(context,"Sites have been updated",Toast.LENGTH_LONG).show();
            }
            if(data.equals("no change".toString()))
            {

                //Nothing
            }
            }catch (Exception e)
        {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
       }

    }


}