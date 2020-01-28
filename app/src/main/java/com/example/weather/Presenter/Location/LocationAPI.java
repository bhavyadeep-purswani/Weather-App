package com.example.weather.Presenter.Location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import io.reactivex.SingleObserver;

import static android.content.Context.LOCATION_SERVICE;

public class LocationAPI implements LocationListener {
    private LocationManager mLocationManager;
    private static LocationAPI mLocationAPI;
    private SingleObserver<Location> mObserver;
    private LocationAPI() {

    }
    public static LocationAPI getInstance(Context context) {
        if(mLocationAPI == null) {
            mLocationAPI = new LocationAPI();
        }
        mLocationAPI.mLocationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        return mLocationAPI;
    }

    public boolean checkLocationEnabled() {
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void getLocation(SingleObserver<Location> observer) {
        mObserver = observer;
        try {
            mLocationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, Looper.getMainLooper());
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mObserver.onSuccess(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
