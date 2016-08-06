package com.iphoto;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.util.ILog;

import java.util.ArrayList;

/**
 * Created by duke on 16-8-6.
 */
public class ViewStyleActivity extends Activity {
    private Button mLeftButton = null;
    private Button mMiddleButton = null;
    private Button mRightButton = null;
    private ArrayList<Button> mCombinationButtonOrder = new ArrayList<Button>();

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
                setButtonSelected(0);
            }
        });

        mMiddleButton = (Button)findViewById(R.id.view_style_middle_button_id);
        mMiddleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ILog.d("ViewStyleActivity middle button is clicked.");
                setButtonSelected(1);
            }
        });

        mRightButton = (Button)findViewById(R.id.view_style_right_button_id);
        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ILog.d("ViewStyleActivity right button is clicked.");
                setButtonSelected(2);
            }
        });

        // add it by order, from left to right
        mCombinationButtonOrder.add(mLeftButton);
        mCombinationButtonOrder.add(mMiddleButton);
        mCombinationButtonOrder.add(mRightButton);
    }

    private void setButtonSelected(int position) {
        int buttonCount = mCombinationButtonOrder.size();
        for (int i = 0; i < buttonCount; ++i) {
            if (i == position) {
                mCombinationButtonOrder.get(i).setSelected(true);
            } else {
                mCombinationButtonOrder.get(i).setSelected(false);
            }
        }
    }
}