package com.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

/*
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
*/

public class NetworkUtils {
    private static final String LOG_TAG = "NetworkUtils";

    // NOTE: network is available, maybe not be usable.
    public static boolean isNetworkAvailable(Context context) {
        if (null == context) {
            Log.e(LOG_TAG, "The context is null.");
            return false;
        }

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == connectivityManager) {
            Log.e(LOG_TAG, "The ConnectivityManager is null.");
            return false;
        }

        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (null == info) {
            Log.e(LOG_TAG, "The ActiveNetworkInfo is null.");
            return false;
        }
        return info.isAvailable();
    }

    public static boolean isNetworkAvailableExt(Context context) {
        if (null == context) {
            Log.e(LOG_TAG, "The context is null.");
            return false;
        }

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == connectivityManager) {
            Log.e(LOG_TAG, "The ConnectivityManager is null.");
            return false;
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            Network[] networkList = connectivityManager.getAllNetworks();
            for (Network network : networkList) {
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
                if (networkInfo.isAvailable()) {
                    return true;
                }
            }
        } else {
            NetworkInfo[] networkInfoList = connectivityManager.getAllNetworkInfo();
            for (NetworkInfo networkInfo : networkInfoList) {
                if (networkInfo.isAvailable()) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isNetworkConnected(Context context) {
        if (null == context) {
            Log.e(LOG_TAG, "The context is null.");
            return false;
        }

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == connectivityManager) {
            Log.e(LOG_TAG, "The ConnectivityManager is null.");
            return false;
        }

        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (null == info) {
            Log.e(LOG_TAG, "The ActiveNetworkInfo is null.");
            return false;
        }
        return info.isConnected();
    }

    public static boolean isNetworkConnectedExt(Context context) {
        if (null == context) {
            Log.e(LOG_TAG, "The context is null.");
            return false;
        }

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == connectivityManager) {
            Log.e(LOG_TAG, "The ConnectivityManager is null.");
            return false;
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            Network[] networkList = connectivityManager.getAllNetworks();
            for (Network network : networkList) {
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
                if (networkInfo.isConnected()) {
                    return true;
                }
            }
        } else {
            NetworkInfo[] networkInfoList = connectivityManager.getAllNetworkInfo();
            for (NetworkInfo networkInfo : networkInfoList) {
                if (networkInfo.isConnected()) {
                    return true;
                }
            }
        }

        return false;
    }

    public static int getNetworkType(Context context) {  // TODO
        if (null == context) {
            Log.e(LOG_TAG, "The context is null.");
            return -1;
        }

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == connectivityManager) {
            Log.e(LOG_TAG, "The ConnectivityManager is null.");
            return -1;
        }

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return 1;
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
            int subType = networkInfo.getSubtype();
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    break;
            }
        }

        return -1;
    }
}
