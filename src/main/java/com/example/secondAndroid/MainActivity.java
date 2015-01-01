package com.example.secondAndroid;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.*;
import android.view.Menu;
import com.example.secondAndroid.database.DBAdapter;
import com.example.secondAndroid.fragment.DeploymentFragment;
import com.example.secondAndroid.fragment.ServiceFragment;
import com.example.secondAndroid.resource.SiteResource;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.viewpagerindicator.TitlePageIndicator;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MenuInflater;

import java.util.ArrayList;
import java.util.List;

import com.example.secondAndroid.communication.*;
import com.example.secondAndroid.sites.Sites;

/**
 * Created with IntelliJ IDEA.
 * User: chaitanya
 * Date: 2013/08/29
 * Time: 4:35 PM
 * To change this template use File | SettingsActivity | File Templates.
 */



public class MainActivity extends FragmentActivity
{

   ServiceFragment fragment_a;
   DeploymentFragment fragment_b;
   DBAdapter myDb;
   private List<String> list = new ArrayList<String>();

    public final static String EXTRA_MESSAGE="Extra message 0.1";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        //Swapping fragments-start
        FragmentPagerAdapter adapter = new TestFragmentAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);
        TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        //Swapping fragments-end


        //Open connection with Database
        openDB();

        FragmentManager manager= getSupportFragmentManager();
        fragment_a=(ServiceFragment) manager.findFragmentById(R.id.fragment1);
        fragment_b=(DeploymentFragment) manager.findFragmentById(R.id.fragment2);



        //Sites
        String chk_url="";
        Cursor cursor=myDb.getAllRowsFromTableSettings();
        if(cursor.moveToFirst())
        {
            do{

                chk_url=cursor.getString(1);
            }while (cursor.moveToNext());
        }
        if((chk_url!="")&&(chk_url!=null))
        {
        Sites sites=new Sites(getApplicationContext());
        sites.execute();
        SaveData saveData=new SaveData(getApplicationContext());
        saveData.pullDataFromTableServices();
        }else{

            Toast.makeText(getApplicationContext(),"Please configure server address",Toast.LENGTH_LONG).show();
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            EditText unitId = (EditText) findViewById(R.id.unitID);
            unitId.setText(scanResult.getContents());
            EditText unitIdDeployment = (EditText) findViewById(R.id.unitIdDeployment);
            unitIdDeployment.setText(scanResult.getContents());
        }

    }


    @Override
    protected void onDestroy()
    {
     closeDB();     //Close connection with Database.
    }


    private void closeDB() {
        super.onDestroy();
        myDb.close();
    }

    private void openDB() {
        myDb=new DBAdapter(this);
        myDb.open();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.map:
                Intent intent1=new Intent(this, MapActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                return true;

            case R.id.menu_settings:
                Intent intent=new Intent(this, SettingsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
             default:
                return super.onOptionsItemSelected(item);

        }



    }



    public void saveData(Bundle bundle)
    {
        String table_tag,unit_id_tag,pump_out_tag,wash_bucket_tag,suction_out_tag,recharge_bucket_tag,scrub_floor_tag,clean_perimeter_tag,report_incident_tag,latitude_tag,longitude_tag,date_tag;


        table_tag= bundle.getString("table");
        unit_id_tag=bundle.getString("unit_id");
        pump_out_tag=bundle.getString("pump_out");
        wash_bucket_tag=bundle.getString("wash_bucket");
        suction_out_tag=bundle.getString("suction_out");
        recharge_bucket_tag=bundle.getString("recharge_bucket");
        scrub_floor_tag=bundle.getString("scrub_floor");
        clean_perimeter_tag=bundle.getString("clean_perimeter");
        report_incident_tag=bundle.getString("report_incident");
        latitude_tag=bundle.getString("latitude");
        longitude_tag=bundle.getString("longitude");
        date_tag=bundle.getString("date");

        SaveData saveData=new SaveData(getApplicationContext());
        saveData.execute(table_tag,unit_id_tag,pump_out_tag,wash_bucket_tag,suction_out_tag,recharge_bucket_tag,scrub_floor_tag,clean_perimeter_tag,report_incident_tag,latitude_tag,longitude_tag,date_tag);
    }

    public void saveDataDeployment(Bundle bundle)
    {
       String table_tag,unit_id_dep_tag, latitude_tag,longitude_tag,site,date_tag;
        table_tag=bundle.getString("table");
        unit_id_dep_tag=bundle.getString("unit_id");
        latitude_tag=bundle.getString("latitude");
        longitude_tag=bundle.getString("longitude");
        site=bundle.getString("site");
        date_tag=bundle.getString("date");
        SaveData saveData=new SaveData(getApplicationContext());
        saveData.execute(table_tag,unit_id_dep_tag,latitude_tag,longitude_tag,site,date_tag);


    }

    public void saveDataGeoPlot (Bundle bundle)
    {

        String table_tag,latitude_tag,longitude_tag,site,date_tag;
        table_tag=bundle.getString("table");
        site=bundle.getString("site");
        latitude_tag=bundle.getString("latitude");
        longitude_tag=bundle.getString("longitude");
        date_tag=bundle.getString("date");
        SaveData saveData=new SaveData(getApplicationContext());
        saveData.execute(table_tag,site,latitude_tag,longitude_tag,date_tag);

    }


}