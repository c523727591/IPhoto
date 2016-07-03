package com.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by duke on 16-7-3.
 */
public class FrontPageViewPagerAdapter extends PagerAdapter {
    private ArrayList<ImageView> mImageList = new ArrayList<ImageView>();

    public FrontPageViewPagerAdapter(Context context, int[]imageResourceId) {
        int size = imageResourceId.length;
        for (int i = 0; i < size; i++) {
            ImageView image = new ImageView(context);
            image.setImageResource(imageResourceId[i]);
            mImageList.add(image);
        }

    }

    @Override
    public int getCount() {
        return mImageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return  view == (View) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view = mImageList.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mImageList.get(position));
    }
}
