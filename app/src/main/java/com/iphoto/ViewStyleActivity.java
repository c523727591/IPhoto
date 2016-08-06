package com.iphoto;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.util.ILog;

/**
 * Created by duke on 16-8-6.
 */
public class ViewStyleActivity extends Activity {
    private Button mLeftButton = null;
    private Button mRightButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_style_activity_layout);

        mLeftButton = (Button)findViewById(R.id.view_style_left_button_id);
        mLeftButton.setSelected(true);

        mLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ILog.d("ViewStyleActivity left button is clicked.");
                mRightButton.setSelected(false);
                mLeftButton.setSelected(true);
            }
        });

        mRightButton = (Button)findViewById(R.id.view_style_right_button_id);
        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ILog.d("ViewStyleActivity right button is clicked.");
                mLeftButton.setSelected(false);
                mRightButton.setSelected(true);
            }
        });
    }
}