package com.iphoto;

import android.app.Activity;
import android.os.Bundle;

import com.jsonparse.MenuDataManager;

/**
 * Created by duke on 16-8-20.
 */
public class GSONActivity extends Activity {
    private MenuDataManager mDataManager = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gson_activity_layout);

        mDataManager = new MenuDataManager();
        mDataManager.getMenuData();

        mDataManager.test1();
    }
}