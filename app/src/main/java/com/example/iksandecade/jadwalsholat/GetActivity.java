package com.example.iksandecade.jadwalsholat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.iksandecade.jadwalsholat.service.LocationService;

public class GetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);

        startService(new Intent(getBaseContext(), LocationService.class));

    }

}
