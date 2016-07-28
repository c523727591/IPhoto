package com.iphoto;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ConvertViewActivity extends Activity {
    private ListView mListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.convert_view_activity_layout);
        mListView = (ListView)findViewById(R.id.convert_view_activity_list_view_id);
    }
}

class ConvertAdapter extends BaseAdapter {
    private ArrayList<ItemData> mItemList = null;
    private Context mContext = null;

    public ConvertAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = null;

        if (convertView == null) {
            switch (mItemList.get(position).mType) {
                case ItemData.TYPE_SEPARATOR_UP:
                    LayoutInflater.from(mContext).inflate(R.layout.convert_view_item1_layout, null);
                    break;
                case ItemData.TYPE_CONTENT:
                    LayoutInflater.from(mContext).inflate(R.layout.convert_view_item2_layout, null);
                    break;
                case ItemData.TYPE_SEPARATOR_DOWN:
                    LayoutInflater.from(mContext).inflate(R.layout.convert_view_item3_layout, null);
                    break;
                default:
                    break;
            }
        } else {
            itemView = convertView;
        }

        return itemView;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return mItemList.get(position).mType;
    }
}

class ItemData {
    public static final int TYPE_SEPARATOR_UP = 0;
    public static final int TYPE_CONTENT = 1;
    public static final int TYPE_SEPARATOR_DOWN = 2;

    public int mType;
    public String mContent;
    public int mImageResId;
}

class ItemHolder1 {
    public ImageView mImageView;
    public TextView mTextView;
}

class ItemHolder2 {
    public TextView mTextView;
}

class ItemHolder3 {
    public TextView mTextView;
    public ImageView mImageView;
}