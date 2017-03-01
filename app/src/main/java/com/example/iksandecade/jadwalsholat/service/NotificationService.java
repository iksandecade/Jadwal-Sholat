package com.example.iksandecade.jadwalsholat.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.iksandecade.jadwalsholat.MainActivity;
import com.example.iksandecade.jadwalsholat.R;
import com.example.iksandecade.jadwalsholat.utils.SPJadwalSholat;

import static com.example.iksandecade.jadwalsholat.utils.JadwalUtils.getSisa;
import static com.example.iksandecade.jadwalsholat.utils.JadwalUtils.isCondition;

/**
 * Created by iksandecade on 27/02/17.
 */

public class NotificationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Handler h = new Handler();
        int delay = 5000;

        h.postDelayed(new Runnable() {
            public void run() {
                addNotification();
                h.postDelayed(this, delay);
            }
        }, delay);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void addNotification() {
        long lastSecond = SPJadwalSholat.getLastSecond(this);
        long subuh = SPJadwalSholat.getTodaySubuh(this);
        long dzuhur = SPJadwalSholat.getTodayDzuhur(this);
        long ashar = SPJadwalSholat.getTodayAshar(this);
        long maghrib = SPJadwalSholat.getTodayMaghrib(this);
        long isya = SPJadwalSholat.getTodayIsya(this);
        if (isCondition(subuh)) {
            lastSecond = subuh;
            SPJadwalSholat.setLastSecond(this, subuh);
        } else if (isCondition(dzuhur)) {
            lastSecond = dzuhur;
            SPJadwalSholat.setLastSecond(this, dzuhur);
        } else if (isCondition(ashar)) {
            lastSecond = ashar;
            SPJadwalSholat.setLastSecond(this, ashar);
        } else if (isCondition(maghrib)) {
            lastSecond = maghrib;
            SPJadwalSholat.setLastSecond(this, maghrib);
        } else if (isCondition(isya)) {
            lastSecond = isya;
            SPJadwalSholat.setLastSecond(this, isya);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Dzuhur")
                        .setContentText(getSisa(lastSecond) + " remaining");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }


}
