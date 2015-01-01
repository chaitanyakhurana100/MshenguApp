package com.example.secondAndroid.location;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.app.Dialog;
import android.content.Context;
import android.content.IntentSender;
import android.location.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.actionbarsherlock.R;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.secondAndroid.MainActivity;
import com.example.secondAndroid.MapActivity;
import com.example.secondAndroid.fragment.DeploymentFragment;
import com.example.secondAndroid.fragment.GeoPlotFragment;
import com.example.secondAndroid.fragment.ServiceFragment;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
//import com.google.android.gms.location.LocationListener;
import android.location.LocationListener;
import android.location.Geocoder;
import android.location.LocationManager;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.provider.Settings;



/**
 * Created with IntelliJ IDEA.
 * User: chaitanya
 * Date: 2013/09/26
 * Time: 1:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class LocationListenerClass  {
        Context ctx;
    Location updatedLocation;
LocationListener gpsLocationListener,networkLocationListener;
LocationManager locationManager;
    public LocationListenerClass(Context context)
    {               ctx=context;
        // Define a listener that responds to location updates
        gpsLocationListener  = new android.location.LocationListener() {
            public void onLocationChanged(Location location) {
               if(location.getAccuracy()<100)
                {
                         updatedLocation=location;
                String msg = "Location updated by GPS. Latitude: " + Double.toString(location.getLatitude()) + "," +" Longitude: "+
                Double.toString(location.getLongitude());



                    // Toast.makeText(ctx, "GPS Accuracy: "+String.valueOf(location.getAccuracy()), Toast.LENGTH_SHORT).show();

                }

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {

                Toast.makeText(ctx, "GPS is now turned on.", Toast.LENGTH_LONG).show();
              /*  if(ServiceFragment.ServFragment!=null)
                {
                    Button btn= (Button) ServiceFragment.ServFragment.getView().findViewById(R.id.submitButton);
                    btn.setTextColor(Color.GREEN);
                }*/
                /*if(DeploymentFragment.DeployFragment!=null)
                {
                    Button deploy_btn= (Button) DeploymentFragment.DeployFragment.getView().findViewById(R.id.tagButton);
                    deploy_btn.setTextColor(Color.GREEN);
                }    */
                /*if(GeoPlotFragment.PlotFragment!=null)
                {
                    Button plot_btn= (Button) GeoPlotFragment.PlotFragment.getView().findViewById(R.id.geoPlotBtn);
                    plot_btn.setTextColor(Color.GREEN);
                }*/

            }

            public void onProviderDisabled(String provider) {

                Toast.makeText(ctx, "GPS is now turned off. Mshengu application will not work properly.", Toast.LENGTH_LONG).show();

            }
        };


        // Define a listener that responds to location updates
        networkLocationListener = new android.location.LocationListener() {
            public void onLocationChanged(Location location) {

                if(location.getAccuracy()<=100)
                {

                    updatedLocation=location;
                String msg = "Location updated by Network. Latitude: " + Double.toString(location.getLatitude()) + "," + " Longitude: "+
                        Double.toString(location.getLongitude());
                    Toast.makeText(ctx, "Network Accuracy: "+String.valueOf(location.getAccuracy()), Toast.LENGTH_SHORT).show();
                }



            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {

                Toast.makeText(ctx, "Network is now turned on.", Toast.LENGTH_LONG).show();

            }

            public void onProviderDisabled(String provider) {

                Toast.makeText(ctx, "Network is now turned off. Mshengu application will not work properly.", Toast.LENGTH_LONG).show();

            }
        };


       locationManager = (LocationManager) ctx.getSystemService(ctx.LOCATION_SERVICE);
       locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,10, gpsLocationListener);
       locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,2000,10, networkLocationListener);

    }


    public Location getUpdatedLocation()
    {
        return  updatedLocation;
    }

    public void stopUpdates()
    {
       locationManager.removeUpdates(gpsLocationListener);
       locationManager.removeUpdates(networkLocationListener);
    }
}
