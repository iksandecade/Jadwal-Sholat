package com.example.iksandecade.jadwalsholat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.iksandecade.jadwalsholat.greendao.DaoHandler;
import com.example.iksandecade.jadwalsholat.greendao.DaoSession;
import com.example.iksandecade.jadwalsholat.greendao.Kota;
import com.example.iksandecade.jadwalsholat.utils.JadwalUtils;
import com.example.iksandecade.jadwalsholat.utils.SPJadwalSholat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SplashActivity extends AppCompatActivity implements LocationListener {

    private static final int PERMISSION_REQUEST_CODE = 1;
    DaoSession daoSession;
    String[] namaKota;
    String[] idKota;
    String[] timeZone;
    LocationManager locationManager;
    String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (SPJadwalSholat.getIsFirst(this)) {
            insertCity();
            if (isGranted()) {
                firstTime();
            } else {
                requestPermission();
            }
        } else {
            goToHome();

        }

    }

    private void insertCity() {
        daoSession = DaoHandler.getInstance(this);
        int avaiable = daoSession.getKotaDao().queryBuilder().list().size();

        if (avaiable <= 0) {

            namaKota = new String[]{"Jakarta", "Banda Aceh", "Medan", "Padang", "Pekanbaru", "Jambi", "Palembang", "Bengkulu",
                    "Bandar Lampung", "Pangkal Pinang", "Tanjung Pinang", "Yogyakarta", "Bandung", "Semarang", "Surabaya", "Serang",
                    "Denpasar", "Kupang", "Mataram", "Pontianak", "Palangka Raya", "Banjarmasin", "Samarinda", "Tanjung Selor",
                    "Manado", "Palu", "Makassar", "Kendari", "Mamuju", "Gorontalo", "Ambon", "Sofifi", "Jayapura", "Manokwari"};

            idKota = new String[]{"JKT", "ACH", "MDN", "PDG", "PKB", "JMB", "PLB", "BKL", "BL", "PP", "TP", "YKT", "BDG", "SRG",
                    "SBY", "SAG", "DPR", "KPG", "MTM", "PON", "PR", "BJN", "SMR", "TS", "MND", "PAL", "MKS", "KDI", "MMU", "GOR",
                    "MBO", "SFF", "JPR", "MKI"};

            timeZone = new String[]{"WIB", "WIB", "WIB", "WIB", "WIB", "WIB", "WIB", "WIB", "WIB", "WIB", "WIB", "WIB", "WIB", "WIB",
                    "WITA", "WIB", "WITA", "WITA", "WITA", "WITA", "WITA", "WITA", "WITA", "WITA", "WITA", "WITA", "WITA", "WITA", "WITA",
                    "WITA", "WIT", "WIT", "WIT", "WIT"};

            for (int i = 0; i < namaKota.length; i++) {
                Kota kota = new Kota();
                kota.setIdKota(idKota[i]);
                kota.setNamaKota(namaKota[i]);
                kota.setTimeZone(timeZone[i]);
                daoSession.getKotaDao().insert(kota);
            }
        }
    }

    private void firstTime() {

        if (JadwalUtils.hasGPSDevice(this)) {
            setLocation();
        } else {
            goToHome();
        }
    }

    private void setLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else {
            Location location = locationManager.getLastKnownLocation(provider);
            locationManager.requestLocationUpdates(provider, 1000, 1, this);
            if (location != null) {
                onLocationChanged(location);
            } else {
                goToSetting();
            }
        }
    }

    private void goToHome() {
        Intent i = new Intent(this, CalendarV2Activity.class);
        startActivity(i);
    }

    private void goToSetting() {
        Intent i = new Intent(this, SettingActivity.class);
        startActivity(i);
    }

    private boolean isGranted() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int resultSecond = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED && resultSecond == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            if (SPJadwalSholat.getIsFirst(this)) {
                insertCity();
                goToSetting();
            } else {
                goToHome();
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (SPJadwalSholat.getIsFirst(this)) {
                        firstTime();
                    } else {
                        goToHome();
                    }
                } else {
                    if (SPJadwalSholat.getIsFirst(this)) {
                        insertCity();
                        firstTime();
                    } else {
                        goToHome();
                    }
                }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            String region = addresses.get(0).getLocality();
            String city = addresses.get(0).getSubAdminArea();
            SPJadwalSholat.setRegion(this, region);
            SPJadwalSholat.setKota(this, city);
        } catch (IOException e) {
            goToSetting();
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
