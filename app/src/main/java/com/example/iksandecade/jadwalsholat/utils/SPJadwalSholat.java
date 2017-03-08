package com.example.iksandecade.jadwalsholat.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by iksandecade on 27/02/17.
 */

public class SPJadwalSholat {
    private static final String LAST_SECOND = "LAST_SECOND";
    private static final String TODAY_SUBUH = "TODAY_SUBUH";
    private static final String TODAY_DZUHUR = "TODAY_DZUHUR";
    private static final String TODAY_ASHAR = "TODAY_ASHAR";
    private static final String TODAY_MAGHRIB = "TODAY_MAGHRIB";
    private static final String TODAY_ISYA = "TODAY_ISYA";
    private static final String IS_FIRST = "IS_FIRST";
    private static final String KOTA = "KOTA";
    private static final String REGION = "REGION";


    private static SharedPreferences getSharedPrefences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLastSecond(Context context, long lastSecond) {
        getSharedPrefences(context).edit().putLong(LAST_SECOND, lastSecond).commit();
    }

    public static long getLastSecond(Context context) {
        return getSharedPrefences(context).getLong(LAST_SECOND, 0);
    }

    public static void setTodaySubuh(Context context, long todaySubuh) {
        getSharedPrefences(context).edit().putLong(TODAY_SUBUH, todaySubuh).commit();
    }

    public static long getTodaySubuh(Context context) {
        return getSharedPrefences(context).getLong(TODAY_SUBUH, 0);
    }

    public static void setTodayDzuhur(Context context, long todayDzyhur) {
        getSharedPrefences(context).edit().putLong(TODAY_DZUHUR, todayDzyhur).commit();
    }

    public static long getTodayDzuhur(Context context) {
        return getSharedPrefences(context).getLong(TODAY_DZUHUR, 0);
    }

    public static void setTodayAshar(Context context, long todayAshar) {
        getSharedPrefences(context).edit().putLong(TODAY_ASHAR, todayAshar).commit();
    }

    public static long getTodayAshar(Context context) {
        return getSharedPrefences(context).getLong(TODAY_ASHAR, 0);
    }

    public static void setTodayMaghrib(Context context, long todayMaghrib) {
        getSharedPrefences(context).edit().putLong(TODAY_MAGHRIB, todayMaghrib).commit();
    }

    public static long getTodayMaghrib(Context context) {
        return getSharedPrefences(context).getLong(TODAY_MAGHRIB, 0);
    }

    public static void setTodayIsya(Context context, long todayIsya) {
        getSharedPrefences(context).edit().putLong(TODAY_ISYA, todayIsya).commit();
    }

    public static long getTodayIsya(Context context) {
        return getSharedPrefences(context).getLong(TODAY_ISYA, 0);
    }

    public static void setIsFirst(Context context, boolean isFirst) {
        getSharedPrefences(context).edit().putBoolean(IS_FIRST, isFirst).commit();
    }

    public static boolean getIsFirst(Context context) {
        return getSharedPrefences(context).getBoolean(IS_FIRST, true);
    }

    public static void setKota(Context context, String kota) {
        getSharedPrefences(context).edit().putString(KOTA, kota).commit();
    }

    public static String getKota(Context context) {
        return getSharedPrefences(context).getString(KOTA, "Surabaya");
    }

    public static void setRegion(Context context, String region) {
        getSharedPrefences(context).edit().putString(REGION, region).commit();
    }

    public static String getRegion(Context context) {
        return getSharedPrefences(context).getString(REGION, "Cimahi");
    }
}
