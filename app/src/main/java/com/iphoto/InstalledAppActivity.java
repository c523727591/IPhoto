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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class InstalledAppActivity extends Activity {
    private static final boolean DEBUG_ENABLE = true;
    private static final String DEBUG_TAG = "Duke_Ding";

    private static final int MSG_CODE_UPDATE_GRID_VIEW = 0;

    private BroadcastReceiver mReceiver = null;
    private UIHandler mHandler = null;

    private GridView mGridView = null;
    private InstalledAppInfoGridAdapter mGridAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.installed_app_activity_layout);

        mGridView = (GridView)findViewById(R.id.installed_app_grid_view_id);
        mGridAdapter = new InstalledAppInfoGridAdapter(this);
        mGridView.setAdapter(mGridAdapter);

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
        LoadInstalledAppInfoTsk task = new LoadInstalledAppInfoTsk(mGridAdapter);
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
        private InstalledAppInfoGridAdapter mAdapter = null;
        private ArrayList<AppInfo> mList = null;

        public LoadInstalledAppInfoTsk(InstalledAppInfoGridAdapter adapter) {
            mAdapter = adapter;
            mList = new ArrayList<AppInfo>();
        }

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
                mList.add(appInfo);
                if (DEBUG_ENABLE) {
                    Log.d(DEBUG_TAG, "app name = " + appInfo.appName);
                }
            }
            if (DEBUG_ENABLE) {
                Log.d(DEBUG_TAG, "installed app size = " + mList.size());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (mList.size() > 0) {
                mAdapter.setAdapterData(mList);
            }
        }
    }
}

class ItemHolder {
    public ImageView mIconImageView;
    public TextView mNameTextView;
}

class AppInfo {
    public String appName = "";
    public String packageName = "";
    public Drawable appIcon = null;
}

class InstalledAppInfoGridAdapter extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<AppInfo> mAppList = null;

    public InstalledAppInfoGridAdapter(Context context) {
        mContext = context;
    }

    public void setAdapterData(ArrayList<AppInfo> list) {
        mAppList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (null == mAppList) {
            return 0;
        }
        return mAppList.size();
    }

    @Override
    public AppInfo getItem(int position) {
        if (null == mAppList) {
            return null;
        }

        if (position > -1 && position < mAppList.size()) {
            return mAppList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == mAppList) {
            return null;
        }

        if (null == convertView) {
            View root = LayoutInflater.from(mContext).inflate(R.layout.installed_app_grid_view_item_layout, null);

            ItemHolder itemHolder = new ItemHolder();
            itemHolder.mNameTextView = (TextView)root.findViewById(R.id.installed_app_item_text_id);
            itemHolder.mIconImageView = (ImageView)root.findViewById(R.id.installed_app_item_image_id);
            root.setTag(itemHolder);

            itemHolder.mNameTextView.setText(mAppList.get(position).appName);
            itemHolder.mIconImageView.setImageDrawable(mAppList.get(position).appIcon);
            return root;
        } else {
            ItemHolder holder = (ItemHolder)convertView.getTag();
            if (null != holder) {
                holder.mNameTextView.setText(mAppList.get(position).appName);
                holder.mIconImageView.setImageDrawable(mAppList.get(position).appIcon);
            }
            return convertView;
        }
    }
}