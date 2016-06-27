package com.iphoto;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.fragment.TestFragment;

import java.util.ArrayList;

/**
 * Created by duke on 16-6-25.
 */
public class TabLayoutActivity extends FragmentActivity {
    private TabLayout mTabLayout = null;
    private ViewPager mViewPager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout_activity_layout);

        mTabLayout = (TabLayout)findViewById(R.id.tab_layout_id);

        mViewPager = (ViewPager)findViewById(R.id.tab_layout_view_pager_id);
        TabLayoutViewPagerAdapter adapter = new TabLayoutViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        mTabLayout.setTabTextColors(Color.BLACK, Color.RED);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private class TabLayoutViewPagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<String> mTitles = null;

        public TabLayoutViewPagerAdapter(FragmentManager fm) {
            super(fm);
            mTitles = new ArrayList<String>();
            mTitles.add(0, "TabLayout0");
            mTitles.add(1, "TabLayout1");
            mTitles.add(2, "TabLayout2");
            mTitles.add(3, "TabLayout3");
            mTitles.add(4, "TabLayout4");
            mTitles.add(5, "TabLayout5");
            mTitles.add(6, "TabLayout6");
            mTitles.add(7, "TabLayout7");
            mTitles.add(8, "TabLayout8");
            mTitles.add(9, "TabLayout9");
            mTitles.add(10, "TabLayoutA");
        }

        @Override
        public Fragment getItem(int position) {
            TestFragment f = new TestFragment();
            f.setContent(mTitles.get(position));
            Log.d("DukeDing", mTitles.get(position) + ", hashCode = " + f.hashCode());
            return f;
        }

        @Override
        public int getCount() {
            return mTitles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }
}
