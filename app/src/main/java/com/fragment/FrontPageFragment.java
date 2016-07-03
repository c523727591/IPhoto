package com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adapter.FrontPageViewPagerAdapter;
import com.iphoto.R;

/**
 * Created by duke on 16-7-3.
 */
public class FrontPageFragment extends BaseFragment {
    private ViewPager mViewPager = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootLayout = inflater.inflate(R.layout.front_page_fragment_layout, container, false);
        mViewPager = (ViewPager)rootLayout.findViewById(R.id.front_page_view_pager_id);

        int[] resourceId = {R.drawable.front_page_image3, R.drawable.front_page_image1, R.drawable.front_page_image2, R.drawable.front_page_image3, R.drawable.front_page_image1};
        FrontPageViewPagerAdapter adapter = new FrontPageViewPagerAdapter(getActivity(),resourceId);
        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int count = mViewPager.getAdapter().getCount();
                if (count < 3) {
                    return;
                }

                if (position == count - 1) {
                    mViewPager.setCurrentItem(1, false);  // false，不显示跳转动画
                } else if (position == 0) {
                    mViewPager.setCurrentItem(count - 2, false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return rootLayout;
    }
}
