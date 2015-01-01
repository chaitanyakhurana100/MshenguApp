package com.example.secondAndroid;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
//import android.support.v4.app.NavUtils;
//import android.view.MenuItem;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.example.secondAndroid.communication.Connection;
import com.example.secondAndroid.database.DBAdapter;
//import android.support
import android.support.v4.app.NavUtils;
//import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;


//import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.ActionBar;
/**
 * Created with IntelliJ IDEA.
 * User: chaitanya
 * Date: 2013/09/19
 * Time: 11:43 PM
 * To change this template use File | SettingsActivity | File Templates.
 */
public class SettingsActivity extends SherlockFragmentActivity {
    EditText url_text;
    DBAdapter mydb;
    View layout_red;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        LayoutInflater inflater=getLayoutInflater();
        final View layout1= inflater.inflate(R.layout.toast_fail,(ViewGroup) findViewById(R.id.toast_layout_red));
        final View layout2= inflater.inflate(R.layout.toast_success,(ViewGroup) findViewById(R.id.toast_layout_green));


        mydb=new DBAdapter(getApplicationContext());
        ActionBar actionBar=getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView tv=(TextView) findViewById(R.id.current_url);
        TextView tv_label=(TextView) findViewById(R.id.current_url_label);

        int num=checkTotalURL();
        tv_label.setText("Current Server Address");
        tv_label.setTypeface(null, Typeface.BOLD);
        mydb=new DBAdapter(this);
        mydb.open();
        String chk_url="";
        Cursor cursor=mydb.getAllRowsFromTableSettings();
        if(cursor.moveToFirst())
        {
            do{

                chk_url=cursor.getString(1);
            }while (cursor.moveToNext());
        }
        mydb.close();


        if((chk_url!="")&&(chk_url!=null))
        {}else{

           Toast.makeText(getApplicationContext(),"Please configure server address",Toast.LENGTH_SHORT).show();
        }


        if(num==0)
        {
            tv.setText("Address not specified");
            tv.setTextColor(Color.RED);

        }
        if(num==1)
        {
            Connection connection=new Connection(getApplicationContext());
            String url=connection.getURL();
            tv.setText(url);
            tv.setTextColor(Color.rgb(0,114,187));

        }

        Button add_address=(Button) findViewById(R.id.url_add_button);
        url_text=(EditText) findViewById(R.id.url_editText);
        add_address.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                int ret=0;
                ret=saveURL(url_text.getText().toString(),layout1,layout2);
                if(ret!=0){
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);}

            }
        });

        Button cancel=(Button) findViewById(R.id.url_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //To change body of implemented methods use File | Settings | File Templates.
              Intent intent=new Intent(getApplicationContext(),MainActivity.class);
              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              startActivity(intent);

           }
       });

    }




   public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        if (item.getTitle().equals("Settings"))
        {
            Intent intent= new Intent(getApplicationContext(),MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public int saveURL(String url, View layout_red, View layout_green)
    {
        int ret=0;
        if(url.length()!=0)
        {
        try {



            Connection connection=new Connection(getApplicationContext());
            String connected=connection.testConnection(url);
            if(connected.equals("true"))
            {
                ret=1;
                TextView text = (TextView) layout_green.findViewById(R.id.text);
                text.setText("Connected to server successfully!");
                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout_green);
                toast.show();


                int rows=checkTotalURL();
                if(rows==0)
                {   mydb.open();
                    mydb.insertRowInTableSettings(url);
                    mydb.close();
                }
                else {
                    mydb.open();
                    mydb.updateRowInTableSettings(1,url);
                    mydb.close();
                }

            }else{


                TextView text = (TextView) layout_red.findViewById(R.id.text);
                text.setText("Server not found!");
                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout_red);
                toast.show();

            }
            //Toast.makeText(getApplicationContext(),"Server address configured to: "+url+" Note: Application will not work with incorrect configuration.",Toast.LENGTH_LONG).show();
        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

        }
        }
            else{

            ret=0;
            TextView text = (TextView) layout_red.findViewById(R.id.text);
            text.setText("Sorry! This is a blank address. Please re-type it");
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout_red);
            toast.show();

        }

            return ret;


    }
    public int checkTotalURL() {

        mydb.open();
        int i=0;
        Cursor cursor= mydb.getAllRowsFromTableSettings();
         if (cursor.moveToFirst())
           {
            i++;
           }
        cursor.close();
        mydb.close();
        return i;

    }



}
