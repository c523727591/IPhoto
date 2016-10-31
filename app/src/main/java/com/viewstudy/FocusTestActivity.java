package com.viewstudy;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iphoto.R;

/**
 * Created by duke on 16-10-31.
 */

// see documents/ViewStudy/view_focus_study.png

public class FocusTestActivity extends Activity {
    private static final String LOG_TAG = "Focus_Test_Activity";

    private View mRootView = null;
    private LinearLayout mLinearLayout1 = null;
    private Button mButtonOK = null;
    private Button mButtonCancel = null;

    private LinearLayout mLinearLayout2 = null;
    private Button mButtonConfirm = null;
    private TextView mTextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRootView = LayoutInflater.from(this).inflate(R.layout.focus_test_activity_layout, null);
        setContentView(mRootView);

        mLinearLayout1 = (LinearLayout) findViewById(R.id.focus_test_view_group1);
        mButtonOK = (Button) findViewById(R.id.focus_test_button1);
        mButtonOK.requestFocus();
        mButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonOK.requestFocus();
                updateTextView("OK Button Clicked");
            }
        });

        mButtonCancel = (Button) findViewById(R.id.focus_test_button2);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonCancel.requestFocus();
                updateTextView("Cancel Button Clicked");
            }
        });

        mLinearLayout2 = (LinearLayout) findViewById(R.id.focus_test_view_group2);
        mButtonConfirm = (Button) findViewById(R.id.focus_test_button3);
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonConfirm.requestFocus();
                updateTextView("Confirm Button Clicked");
            }
        });

        mTextView = (TextView) findViewById(R.id.focus_test_text_view);
        updateTextView("init:");
    }

    private void updateTextView(String description) {
        String text = description;
        text += "\nOK Button has focus = " + mButtonOK.hasFocus();
        text += "\nOK Button parent layout has focus = " + mLinearLayout1.hasFocus();
        text += "\nRoot View has focus = " + mRootView.hasFocus();
        text += "\nCancel Button has focus = " + mButtonCancel.hasFocus();
        text += "\nView Group2 has focus = " + mLinearLayout2.hasFocus();
        text += "\nConfirm Button has focus = " + mButtonConfirm.hasFocus();

        mTextView.setText(text);
    }
}
