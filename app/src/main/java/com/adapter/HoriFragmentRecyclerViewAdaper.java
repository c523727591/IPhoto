package com.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iphoto.R;

/**
 * Created by duke on 16-6-13.
 */
public class HoriFragmentRecyclerViewAdaper extends RecyclerView.Adapter<HoriFragmentRecyclerViewAdaper.HoriFragmentViewHolder> {
    private String[] mDataValue;

    public HoriFragmentRecyclerViewAdaper(String[] value) {
        mDataValue = value;
    }

    public class HoriFragmentViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public HoriFragmentViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    @Override
    public HoriFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.hori_fragment_recylerview, parent, false);
        TextView textView = (TextView)layout.findViewById(R.id.recycler_view_textview_id);
        HoriFragmentViewHolder vh = new HoriFragmentViewHolder(textView);
        return vh;
    }

    @Override
    public void onBindViewHolder(HoriFragmentViewHolder holder, int position) {
        holder.mTextView.setText(mDataValue[position]);


    }

    @Override
    public int getItemCount() {
        Log.i("Duke_TAG", "getItemCount = " + mDataValue.length);
        return mDataValue.length;
    }
}
