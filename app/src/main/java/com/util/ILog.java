package com.util;

import android.util.Log;

/**
 * Created by duke on 16-7-31.
 */
public class ILog {
    private static final boolean DEBUG_ENABLE = true;
    private static final String LOG_TAG = "IPhoto.log";

    public static void d(String msg) {
        if (DEBUG_ENABLE) {
            Log.d(LOG_TAG, msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG_ENABLE) {
            Log.e(LOG_TAG, msg);
        }
    }

    public static void i(String msg) {
        if (DEBUG_ENABLE) {
            Log.i(LOG_TAG, msg);
        }
    }

    public static void w(String msg) {
        if (DEBUG_ENABLE) {
            Log.w(LOG_TAG, msg);
        }
    }
}
