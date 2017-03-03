package com.example.iksandecade.jadwalsholat.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by meridian on 02/03/17.
 */

public class ConnectionReceiver extends BroadcastReceiver {


    public static boolean isConnecting(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
            for (int i = 0; i < networkInfos.length; i++) {
                if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED)
                    return true;
            }
        }
        return false;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
