package com.example.secondAndroid.fragment;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.secondAndroid.MainActivity;
import com.example.secondAndroid.R;
import com.example.secondAndroid.database.DBAdapter;
import com.example.secondAndroid.location.LocationListenerClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chaitanya
 * Date: 2013/08/30
 * Time: 5:23 PM
 * To change this template use File | SettingsActivity | File Templates.
 */
public class GeoPlotFragment extends Fragment {

    public static Fragment PlotFragment = null;

    AutoCompleteTextView actv;
     Button geoPlotButton;
    DBAdapter myDb;
    List<String> sites=new ArrayList<String>();
    View v,layout_green,layout_red;
    private double latitude=0;
    private double longitude=0;
    LocationListenerClass locationListenerClass;
    Location location;
    Context ctx;

    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.geoplotfragment,container,false);
        LayoutInflater inflater1 = getLayoutInflater(savedInstanceState);
        layout_green  = inflater1.inflate(R.layout.toast_success,(ViewGroup) v.findViewById(R.id.toast_layout_green));
        layout_red =inflater1.inflate(R.layout.toast_fail,(ViewGroup) v.findViewById(R.id.toast_layout_red));
        geoPlotButton=(Button) v.findViewById(R.id.geoPlotBtn);
        actv=(AutoCompleteTextView) v.findViewById(R.id.actv_site);

        autoCompleteTextBox();
        calculateLocation(getActivity().getApplicationContext(),"");




        geoPlotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                calculateLocation(getActivity().getApplicationContext(),"button");

                if(latitude==0)
                {
                    Button plot_btn= (Button) v.findViewById(R.id.geoPlotBtn);
                    plot_btn.setTextColor(Color.WHITE);
                }else{
                    Button plot_btn= (Button) v.findViewById(R.id.geoPlotBtn);
                    plot_btn.setTextColor(Color.GREEN);

                }

                int exist=checkSitesInDB(ctx,actv.getText().toString());
                myDb=new DBAdapter(getActivity());
                myDb.open();
                String chk_url="";
                Cursor cursor=myDb.getAllRowsFromTableSettings();
                if(cursor.moveToFirst())
                {
                    do{

                        chk_url=cursor.getString(1);
                    }while (cursor.moveToNext());
                }
                myDb.close();


                if((chk_url!="")&&(chk_url!=null))
                {

                    if(exist==1)
                {
                   if (latitude == 0) {
                       //Toast.makeText(getView().getContext(), actv.getText().toString(), Toast.LENGTH_LONG).show();
                       TextView text = (TextView) layout_red.findViewById(R.id.text);
                       text.setText("Data cannot be saved on device without location");
                       Toast toast = new Toast(getView().getContext());
                       toast.setDuration(Toast.LENGTH_LONG);
                       toast.setView(layout_red);
                       toast.show();
                   }
                   else {

                        Date date=new Date();
                        String str_date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                        MainActivity mainActivity = (MainActivity) getActivity();
                        Bundle bundle = new Bundle();
                        bundle.putString("table", "geoplot");
                        bundle.putString("site", actv.getText().toString());
                        bundle.putString("latitude", String.valueOf(latitude));
                        bundle.putString("longitude", String.valueOf(longitude));
                        bundle.putString("date",str_date);
                        mainActivity.saveDataGeoPlot(bundle);
                    }

                    actv.setText("");


                }else
                {
                    TextView text = (TextView) layout_red.findViewById(R.id.text);
                    text.setText("Site does not exist. Please submit correct site!");
                    Toast toast = new Toast(getView().getContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout_red);
                    toast.show();

                }
                } else{

                    Toast.makeText(getView().getContext(),"Please configure server address",Toast.LENGTH_SHORT).show();
                }
            }
        });

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        if(latitude==0)
        {
            Button plot_btn= (Button) v.findViewById(R.id.geoPlotBtn);
            plot_btn.setTextColor(Color.WHITE);
        }else{
            Button plot_btn= (Button) v.findViewById(R.id.geoPlotBtn);
            plot_btn.setTextColor(Color.GREEN);

        }


        return v;

    }

    @Override
    public void onStart()
    {    super.onStart();

        locationListenerClass=new LocationListenerClass(v.getContext());

    }
    @Override
    public void onStop()
    {
        locationListenerClass.stopUpdates();
        super.onStop();
    }



    public void autoCompleteTextBox()
    {
        ctx=getActivity().getApplicationContext();
        AutoCompleteTextView actv=(AutoCompleteTextView) v.findViewById(R.id.actv_site);
        getDataFromDB(ctx);
        actv.setAdapter(new ArrayAdapter<String>(ctx,R.layout.list_detail,sites));
  }

    public void getDataFromDB(Context ctx)
    {
        String str="";
        myDb=new DBAdapter(ctx);
        myDb.open();
        sites.clear();
        Cursor cursor=myDb.getAllRowsFromTableSite();
        if(cursor.moveToFirst())
        {
            do{

                str=cursor.getString(DBAdapter.COL_SITE_NAME);
                sites.add(str);
            }while (cursor.moveToNext());
        }
        cursor.close();
        myDb.close();
    }


    public int checkSitesInDB(Context ctx, String site)
    {
        String str="";
        int exist=0;
        myDb=new DBAdapter(ctx);
        myDb.open();

        Cursor cursor=myDb.getAllRowsFromTableSite();
        if(cursor.moveToFirst())
        {
            do{

                str=cursor.getString(DBAdapter.COL_SITE_NAME);
                if(str.equals(site.toString()))
                {
                    exist=1;
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        myDb.close();
        return  exist;
    }

    public void calculateLocation(Context ctx, String where)
    {
        try{


            location=locationListenerClass.getUpdatedLocation();
            latitude=location.getLatitude();
            longitude=location.getLongitude();
            locationListenerClass.stopUpdates();
        }
        catch (Exception e)
        {
            if(where.equals("button".toString()))
            {
            Toast.makeText(ctx, "No GPS signal!", Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(ctx, "Could not find location.", Toast.LENGTH_LONG).show();
            //Toast.makeText(ctx, "Turn on location services of the device", Toast.LENGTH_LONG).show();

        }

    }






}