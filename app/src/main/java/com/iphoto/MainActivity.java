package com.iphoto;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.adapter.HoriPhotoFragmentAdapter;
import com.fragment.HorizontalPhotoFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager = null;
    private ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void InitViewPager(){
        mViewPager = (ViewPager)findViewById(R.id.mainActivity_viewpager_id);
        fragmentList = new ArrayList<Fragment>();
        Fragment btFragment = new HorizontalPhotoFragment();
        fragmentList.add(btFragment);

        //给ViewPager设置适配器
        mViewPager.setAdapter(new HoriPhotoFragmentAdapter(getSupportFragmentManager(), fragmentList));
        mViewPager.setCurrentItem(0);
    }
}
