package com.example.iksandecade.jadwalsholat.utils;

import android.content.Context;
import android.location.LocationManager;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by iksandecade on 28/02/17.
 */

public class JadwalUtils {

    public static boolean isCondition(long data) {
        boolean result = false;
        long now = System.currentTimeMillis();
        if (now < data) {
            result = true;
        }
        return result;
    }

    public static String getSisa(long data) {
        String result = "";
        long milliseconds1 = System.currentTimeMillis();
        long milliseconds2 = data;

        long diff = milliseconds2 - milliseconds1;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = (diff / (60 * 60 * 1000)) + 1;

        if (diffHours != 1 && diffHours > 0) {
            result = diffHours + " Hour";
        } else if (diffMinutes > 0) {
            result = diffMinutes + " Minute";
        }

        return result;
    }

    public static String getNowYear() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getNowMonth() {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getNowDay() {
        DateFormat dateFormat = new SimpleDateFormat("dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static Long getDay(String day) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MMM dd", Locale.US);
        Date parsedDate = dateFormat.parse("2017 " + day);
        Timestamp timestamp = new Timestamp(parsedDate.getTime());
        return timestamp.getTime();
    }

    public static Long getJadwal(String date, String time) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MMM dd kk:mm", Locale.US);
        Date parsedDate = dateFormat.parse("2017 " + date + " " + time);
        Timestamp timestamp = new Timestamp(parsedDate.getTime());
        return timestamp.getTime();
    }

    public static String getHari(long data) {

        Date date = new Date(data);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd");
        return simpleDateFormat.format(date);
    }

    public static boolean hasGPSDevice(Context context) {
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (manager == null)
            return false;
        final List<String> provider = manager.getAllProviders();
        if (provider == null)
            return false;
        return provider.contains(LocationManager.GPS_PROVIDER);
    }
}
