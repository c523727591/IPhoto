package com.viewstudy;

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
import com.iphoto.R;

import java.util.ArrayList;

/**
 * Created by duke on 16-10-31.
 */

public class ViewStudyActivity extends Activity {
    private ListView mListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_study_activity_layout);

        mListView = (ListView)findViewById(R.id.view_study_activity_list_view_id);
        mListView.setAdapter(new SampleItemListAdapter(this));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null != parent) {
                    SampleItemListAdapter adapter = (SampleItemListAdapter) parent.getAdapter();
                    SampleItemInfo info = (SampleItemInfo)adapter.getItem(position);
                    if (null != info) {
                        Intent intent = new Intent();
                        intent.setClassName(ViewStudyActivity.this, info.mClassName);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}

class SampleItemInfo {
    public int mOrder;
    public int mTitleResId;
    public String mClassName;

    SampleItemInfo() {}
    SampleItemInfo(int order, int resId, String name) {
        this.mOrder = order;
        this.mTitleResId = resId;
        this.mClassName = name;
    }
}

class SampleItemHolder {
    public TextView mOrderTextView;
    public TextView mTitleTextView;
}

class SampleItemListAdapter extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<SampleItemInfo> mSampleList = new ArrayList<SampleItemInfo>();

    private void createSampleList() {
        SampleItemInfo itemInfo = null;
        int order = 0;

        itemInfo = new SampleItemInfo(++order, R.string.view_study_focus_test, "com.viewstudy.FocusTestActivity");
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