package com.example.lee.remote_android;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;

public class MyLocationListener implements LocationListener {

    double latitude;
    double  longitude;


    public MyLocationListener(Context myContext){
        LocationManager locationManager = (LocationManager) myContext.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    public  double getLatitude(){
        return latitude; }

    public  double getLongitude(){
        return longitude; }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub

       latitude =  location.getLatitude();
        longitude =  location.getLongitude();

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

}