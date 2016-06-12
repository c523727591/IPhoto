package com.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by duke on 16-6-12.
 */
public class HoriPhotoFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mList = null;

    public HoriPhotoFragmentAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(0);
    }

    @Override
    public int getCount() {
        return 1;
    }
}
