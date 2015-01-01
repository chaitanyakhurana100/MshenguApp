package com.example.secondAndroid.fragment;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import com.example.secondAndroid.MainActivity;
import com.example.secondAndroid.R;
import com.example.secondAndroid.database.DBAdapter;
import com.example.secondAndroid.location.LocationListenerClass;
import com.google.zxing.integration.android.IntentIntegrator;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: chaitanya
 * Date: 2013/08/30
 * Time: 5:23 PM
 * To change this template use File | SettingsActivity | File Templates.
 */

public class ServiceFragment extends Fragment {

    public static Fragment ServFragment = null;

    CheckBox pumpOut,washBucket,suctionOut,rechargeBucket,scrubFloor,cleanPerimeter;
    String pump_out="no",wash_bucket="no",suction_out="no",recharge_bucket="no",scrub_floor="no",clean_perimeter="no";
    Location location;
    private double latitude=0;
    private double longitude=0;
    LocationListenerClass locationListenerClass;
    View v1,v2;
    DBAdapter myDb;
    View layout_green,layout_red;


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
    {

       v1= inflater.inflate(R.layout.servicefragment,container,false);
       v2=inflater.inflate(R.layout.deploymentfragment,container,false);
       LayoutInflater inflater1 = getLayoutInflater(savedInstanceState);
       layout_green  = inflater1.inflate(R.layout.toast_success,(ViewGroup) v1.findViewById(R.id.toast_layout_green));
       layout_red =inflater1.inflate(R.layout.toast_fail,(ViewGroup) v1.findViewById(R.id.toast_layout_red));
        //Checkbox Listeners


        myDb=new DBAdapter(getActivity());
        pumpOutCheckBoxListener();
        washBucketCheckBoxListener();
        suctionOutCheckBoxListener();
        rechargeBucketCheckBoxListener();
        scrubFloorCheckBoxListener();
        cleanPerimeterCheckBoxListener();

        calculateLocation(getActivity().getApplicationContext(),"");


        Button scanButton = (Button) v1.findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {

                IntentIntegrator integrator = new IntentIntegrator(
                        getActivity());
                integrator.initiateScan();

                calculateLocation(getActivity().getApplicationContext(),"button");

                if(latitude==0)
                {
                    Button btn= (Button) v1.findViewById(R.id.submitButton);
                    btn.setTextColor(Color.WHITE);
                    String str=String.valueOf(latitude);
                }else{
                    Button btn= (Button) v1.findViewById(R.id.submitButton);
                    btn.setTextColor(Color.GREEN);
                    String str=String.valueOf(latitude);
                }


            }
        });



        Button submitButton= (Button) v1.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new OnClickListener(){
              @Override
        public void onClick(View arg0)
              {
                  EditText unitId = (EditText) getView().findViewById(R.id.unitID);
                  EditText reportIncident=(EditText) getView().findViewById(R.id.reportIncident);
                  MainActivity mainActivity=(MainActivity) getActivity();



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
                 if(latitude==0)
                 {
                     TextView text = (TextView) layout_red.findViewById(R.id.text);
                     text.setText("Data cannot be saved on device without location");
                     Toast toast = new Toast(getView().getContext());
                     toast.setDuration(Toast.LENGTH_LONG);
                     toast.setView(layout_red);
                     toast.show();

                 }else
                 {
                      Date date=new Date();
                      String str_date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                      Bundle bundle=new Bundle();
                      bundle.putString("table", "services");
                      bundle.putString("unit_id", unitId.getText().toString());
                      bundle.putString("pump_out", pump_out);
                      bundle.putString("wash_bucket", wash_bucket);
                      bundle.putString("suction_out", suction_out);
                      bundle.putString("recharge_bucket", recharge_bucket);
                      bundle.putString("scrub_floor", scrub_floor);
                      bundle.putString("clean_perimeter", clean_perimeter);
                      bundle.putString("report_incident",reportIncident.getText().toString());
                      bundle.putString("latitude",String.valueOf(latitude));
                      bundle.putString("longitude",String.valueOf(longitude));
                      bundle.putString("date",str_date);

                      mainActivity.saveData(bundle);
                  }

                  unitId.setText("");
                  pumpOut.setChecked(false);
                  washBucket.setChecked(false);
                  suctionOut.setChecked(false);
                  rechargeBucket.setChecked(false);
                  scrubFloor.setChecked(false);
                  cleanPerimeter.setChecked(false);
                  reportIncident.setText("");

              }else{

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
            Button btn= (Button) v1.findViewById(R.id.submitButton);
            btn.setTextColor(Color.WHITE);
            String str=String.valueOf(latitude);
         }else{
            Button btn= (Button) v1.findViewById(R.id.submitButton);
            btn.setTextColor(Color.GREEN);
            String str=String.valueOf(latitude);
        }


        return v1;

     }

    public void pumpOutCheckBoxListener()
    {
        pumpOut=(CheckBox)v1.findViewById(R.id.pump_out);
        pumpOut.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    pump_out="yes";
                } else {
                    pump_out="no";
                }
            }
        });





    }

    public void washBucketCheckBoxListener()
    {
        washBucket=(CheckBox)v1.findViewById(R.id.wash_bucket);
        washBucket.setOnClickListener(new OnClickListener(){
            public void onClick(View v)
            {
                if (((CheckBox) v).isChecked())
                {
                    wash_bucket="yes";
                } else
                {
                    wash_bucket="no";
                }

            }});


    }

    public void suctionOutCheckBoxListener()
    {
        suctionOut=(CheckBox)v1.findViewById(R.id.suction_out);
        suctionOut.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    suction_out="yes";
                } else {
                    suction_out="no";
                }
            }
        });


    }

    public void rechargeBucketCheckBoxListener()
    {
        rechargeBucket=(CheckBox)v1.findViewById(R.id.recharge_bucket);
        rechargeBucket.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
            if (((CheckBox) v).isChecked()) {
                recharge_bucket="yes";
            } else {
                recharge_bucket="no";
            }
        }
    });
    }

    public void scrubFloorCheckBoxListener()
    {
        scrubFloor=(CheckBox)v1.findViewById(R.id.scrub_floor);
        scrubFloor.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
            if (((CheckBox) v).isChecked()) {
                scrub_floor="yes";
            } else {
                scrub_floor="no";
            }
        }
    });
    }

    public void cleanPerimeterCheckBoxListener()
    {
        cleanPerimeter=(CheckBox)v1.findViewById(R.id.clean_perimeter);
        cleanPerimeter.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
            if (((CheckBox) v).isChecked()) {
                clean_perimeter="yes";
            } else {
                clean_perimeter="no";
            }
        }
    });


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
           // Toast.makeText(ctx, "Could not find location.", Toast.LENGTH_LONG).show();
            //Toast.makeText(ctx, "Turn on location services of the device", Toast.LENGTH_LONG).show();

        }

    }


    @Override
    public void onStart()
    {
        super.onStart();
        locationListenerClass=new LocationListenerClass(v1.getContext());

    }

    @Override
    public void onStop()
    {
        locationListenerClass.stopUpdates();
        super.onStop();
    }

}

