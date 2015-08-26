package com.example.lee.remote_android;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

public class MyLocationListener implements LocationListener {

    private static final String LATITUDE_KEY = "nastyLatitude";
    private static final String LONGITUDE_KEY = "nastyLongitude";

    private Context context;
    private LocationManager locationManager;
    private Boolean isListening;

    public MyLocationListener(Context context) {
        this.context = context;
        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        isListening = false;
    }

    public Boolean startListening(){
        if (isListening) {return true;}

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            isListening = true;
        }

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            isListening = true;
        }

        return isListening;
    }

    public void stopListening() {
        locationManager.removeUpdates(this);
    }

    public static String getLatitude(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        String latitudeString = sharedPreferences.getString(LATITUDE_KEY, null);
        if (latitudeString == null) {
            Location lastKnownLocation = loadLastKnownLocation(context);
            if (lastKnownLocation != null) {
                latitudeString = String.valueOf(lastKnownLocation.getLatitude());
            }
        }

        return latitudeString;
    }

    public static String getLongitude(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        String longitudeString = sharedPreferences.getString(LONGITUDE_KEY, null);
        if (longitudeString == null) {
            Location lastKnownLocation = loadLastKnownLocation(context);
            if (lastKnownLocation != null) {
                longitudeString = String.valueOf(lastKnownLocation.getLongitude());
            }
        }

        return longitudeString;
    }

    private static Location loadLastKnownLocation (Context context) {
        LocationManager staticLocationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        Location currentLocation = staticLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (currentLocation == null) {currentLocation = staticLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);}
        staticLocationManager = null;

        saveLocation(context, currentLocation);

        return currentLocation;
    }

    private static void saveLocation (Context context, Location locationToSave) {
        if (locationToSave == null) {return;}

        String currentLatitude = String.valueOf(locationToSave.getLatitude());
        String currentLongitude = String.valueOf(locationToSave.getLongitude());

        SharedPreferences.Editor editor = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).edit();
        if (currentLatitude != null) {editor.putString(LATITUDE_KEY, currentLatitude);}
        if (currentLongitude != null) {editor.putString(LONGITUDE_KEY, currentLongitude);}
        editor.apply();
    }

    @Override
    public void onLocationChanged(Location location) {
        saveLocation(context, location);
    }

    @Override public void onStatusChanged(String provider, int status, Bundle extras) {}
    @Override public void onProviderEnabled(String provider) {}
    @Override public void onProviderDisabled(String provider) {}
}