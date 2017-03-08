package com.example.iksandecade.jadwalsholat.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.iksandecade.jadwalsholat.utils.SPJadwalSholat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by iksandecade on 07/03/17.
 */

public class LocationService extends Service implements LocationListener {
    LocationManager locationManager;
    String provider;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Handler h = new Handler();
        int delay = 60000;
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                getLocation();
                h.postDelayed(this, delay);
            }
        }, delay);
        return START_STICKY;
    }


    private void getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            onLocationChanged(location);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(lat, lng, 1);
            String region = addressList.get(0).getLocality();
            String city = addressList.get(0).getSubAdminArea();
            SPJadwalSholat.setRegion(this, region);
            SPJadwalSholat.setKota(this, city);
            Toast.makeText(this, region + " " + city, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
