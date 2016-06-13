package com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adapter.HoriFragmentRecyclerViewAdaper;
import com.iphoto.R;

/**
 * Created by duke on 16-6-12.
 */
public class HorizontalPhotoFragment extends Fragment {
    private RecyclerView mRecyclerView = null;
    private RecyclerView.Adapter mAdapter = null;
    private RecyclerView.LayoutManager mLayoutManager = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootLayout = inflater.inflate(R.layout.hori_photo_fragment, container, false);

        mRecyclerView = (RecyclerView)rootLayout.findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        String[] value = {"ShangHai", "BeiJing", "JiangSu", "ZheJiang"};
        mAdapter = new HoriFragmentRecyclerViewAdaper(value);
        mRecyclerView.setAdapter(mAdapter);

        return rootLayout;
    }
}
