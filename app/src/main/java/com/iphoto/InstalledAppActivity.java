package com.iphoto;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

public class InstalledAppActivity extends Activity {
    private static final int MSG_CODE_UPDATE_GRID_VIEW = 0;

    private BroadcastReceiver mReceiver = null;
    private UIHandler mHandler = null;

    private GridView mGridView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.installed_app_activity_layout);

        mGridView = (GridView)findViewById(R.id.installed_app_grid_view_id);

        mHandler = new UIHandler(this);
    }

    @Override
    protected void onResume() {
        sendGetInstalledAppInfoMsg();
        super.onResume();
    }

    @Override
    protected void onStart() {
        registerMainActivityReceiver();
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterMainActivityReceiver();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void sendGetInstalledAppInfoMsg() {
        mHandler.removeMessages(MSG_CODE_UPDATE_GRID_VIEW);
        Message msg = Message.obtain();
        msg.what = MSG_CODE_UPDATE_GRID_VIEW;
        mHandler.sendMessageDelayed(msg, 100);
    }

    private void loadInstalledAppInfo() {
        LoadInstalledAppInfoTsk task = new LoadInstalledAppInfoTsk();
        PackageManager pm = getPackageManager();
        task.execute(pm);
    }

    private static class UIHandler extends Handler {
        private WeakReference<InstalledAppActivity> mWRActivity = null;

        public UIHandler(InstalledAppActivity activity) {
            mWRActivity = new WeakReference<InstalledAppActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            InstalledAppActivity activity = mWRActivity.get();
            if (null != activity) {
                switch (msg.what) {
                    case MSG_CODE_UPDATE_GRID_VIEW:
                        activity.loadInstalledAppInfo();
                        break;
                }
            }
        }
    }

    private void registerMainActivityReceiver() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)
                        || intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
                    mHandler.removeMessages(MSG_CODE_UPDATE_GRID_VIEW);
                    Message msg = Message.obtain();
                    msg.what = MSG_CODE_UPDATE_GRID_VIEW;
                    mHandler.sendMessage(msg);
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        registerReceiver(mReceiver, filter);
    }

    private void unregisterMainActivityReceiver() {
        unregisterReceiver(mReceiver);
        mReceiver = null;
    }

    private class LoadInstalledAppInfoTsk extends AsyncTask<PackageManager, Void, Void> {
        @Override
        protected Void doInBackground(PackageManager... params) {
            PackageManager pm = params[0];

            // get all Launcher apps
            Intent homeIntent = new Intent(Intent.ACTION_MAIN, null);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            List<ResolveInfo> homeAppList = pm.queryIntentActivities(homeIntent, 0);

            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            List<ResolveInfo> resolveInfoList = pm.queryIntentActivities(mainIntent, 0);

            for (ResolveInfo resolveInfo : resolveInfoList) {
                boolean isHomeApp = false;
                for (ResolveInfo homeAppInfo : homeAppList) {
                    if (homeAppInfo.activityInfo.packageName.equals(resolveInfo.activityInfo.packageName)) {
                        isHomeApp = true;
                        break;
                    }
                }

                if (isHomeApp) {
                    continue;
                }

                AppInfo appInfo = new AppInfo();
                appInfo.packageName = resolveInfo.activityInfo.packageName;
                appInfo.appName = resolveInfo.activityInfo.loadLabel(pm).toString();
                appInfo.appIcon = resolveInfo.activityInfo.loadIcon(pm);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }
}

class ItemHolder {
    public ImageView mIcon;
    public TextView mName;
}

class AppInfo {
    public String appName = "";
    public String packageName = "";
    public Drawable appIcon = null;
}