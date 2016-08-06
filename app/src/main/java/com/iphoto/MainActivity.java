package com.iphoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.util.ILog;
import com.util.IUtils;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ListView mListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView)findViewById(R.id.main_activity_list_view_id);
        mListView.setAdapter(new SampleItemListAdapter(this));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null != parent) {
                    SampleItemListAdapter adapter = (SampleItemListAdapter) parent.getAdapter();
                    SampleItemInfo info = (SampleItemInfo)adapter.getItem(position);
                    if (null != info) {
                        Intent intent = new Intent();
                        intent.setClassName(MainActivity.this, info.mClassName);
                        startActivity(intent);
                    }
                }
            }
        });

        test();
    }

    private void test() {
        String s1 = new String("Duke123456789");
        String s2 = new String("Duke123456789");
        ILog.d("s1 hashcode = " + s1.hashCode() + ", s2 hashcode = " + s2.hashCode());
        if (s1.hashCode() == s2.hashCode()) {
            ILog.d("s1 hashcode == s2 hashcode");
        }

        ILog.d("0xFFFFFFFF = " + IUtils.hexIntStringToInt("0xFFFFFFFF", 0));
        ILog.d("0xFF112345 = " + IUtils.hexIntStringToInt("0xFF112345", 0));
        ILog.d("0x67112345 = " + IUtils.hexIntStringToInt("0x67112345", 0));
    }
}

class SampleItemInfo {
    public int mOrder;
    public int mTitleResId;
    public String mClassName;
}

class SampleItemHolder {
    public TextView mOrderTextView;
    public TextView mTitleTextView;
}

class SampleItemListAdapter extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<SampleItemInfo> mSampleList = new ArrayList<SampleItemInfo>();

    private void createSampleList() {
        SampleItemInfo itemInfo = new SampleItemInfo();
        itemInfo.mOrder = 1;
        itemInfo.mTitleResId = R.string.main_activity_tab_layout;
        itemInfo.mClassName = "com.iphoto.TabLayoutActivity";
        mSampleList.add(itemInfo);

        itemInfo = new SampleItemInfo();
        itemInfo.mOrder = 2;
        itemInfo.mTitleResId = R.string.main_activity_disk_lru_cache;
        itemInfo.mClassName = "com.iphoto.DiskLruCacheTestActivity";
        mSampleList.add(itemInfo);

        itemInfo = new SampleItemInfo();
        itemInfo.mOrder = 3;
        itemInfo.mTitleResId = R.string.main_activity_installed_app_info;
        itemInfo.mClassName = "com.iphoto.InstalledAppActivity";
        mSampleList.add(itemInfo);

        itemInfo = new SampleItemInfo();
        itemInfo.mOrder = 4;
        itemInfo.mTitleResId = R.string.main_activity_convert_view;
        itemInfo.mClassName = "com.iphoto.ConvertViewActivity";
        mSampleList.add(itemInfo);

        itemInfo = new SampleItemInfo();
        itemInfo.mOrder = 5;
        itemInfo.mTitleResId = R.string.main_activity_view_style;
        itemInfo.mClassName = "com.iphoto.ViewStyleActivity";
        mSampleList.add(itemInfo);
    }

    public SampleItemListAdapter(Context context) {
        mContext = context;
        createSampleList();
    }

    @Override
    public int getCount() {
        return mSampleList.size();
    }

    @Override
    public SampleItemInfo getItem(int position) {
        return mSampleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == mSampleList) {
            return null;
        }

        if (null == convertView) {
            View root = LayoutInflater.from(mContext).inflate(R.layout.activity_main_listview_item_layout, null);

            // TODO 这里使用ViewGroup.LayoutParams是不行的
            int height = mContext.getResources().getDimensionPixelOffset(R.dimen.main_activity_item_height);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, height);
            root.setLayoutParams(params);

            SampleItemHolder itemHolder = new SampleItemHolder();
            itemHolder.mOrderTextView = (TextView)root.findViewById(R.id.sample_item_order_text_view_id);
            itemHolder.mTitleTextView = (TextView)root.findViewById(R.id.sample_item_title_text_view_id);
            root.setTag(itemHolder);

            itemHolder.mOrderTextView.setText(String.valueOf(mSampleList.get(position).mOrder));
            itemHolder.mTitleTextView.setText(mSampleList.get(position).mTitleResId);
            return root;
        } else {
            SampleItemHolder holder = (SampleItemHolder)convertView.getTag();
            if (null != holder) {
                holder.mOrderTextView.setText(String.valueOf(mSampleList.get(position).mOrder));
                holder.mTitleTextView.setText(mSampleList.get(position).mTitleResId);
            }
            return convertView;
        }
    }
}