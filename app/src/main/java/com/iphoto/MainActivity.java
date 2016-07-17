package com.iphoto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private Button mTabLayoutButton = null;
    private Button mDiskLruCacheButton = null;
    private Button mInstalledAppButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayoutButton = (Button)findViewById(R.id.button_tab_layout_id);
        mTabLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(MainActivity.this, "com.iphoto.TabLayoutActivity");
                startActivity(intent);
            }
        });

        mDiskLruCacheButton = (Button)findViewById(R.id.button_disk_lru_cache_id);
        mDiskLruCacheButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(MainActivity.this, "com.iphoto.DiskLruCacheTestActivity");
                startActivity(intent);
            }
        });

        mInstalledAppButton = (Button)findViewById(R.id.button_installed_app_id);
        mInstalledAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(MainActivity.this, "com.iphoto.InstalledAppActivity");
                startActivity(intent);
            }
        });
    }
}
