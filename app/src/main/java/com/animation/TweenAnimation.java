package com.animation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.iphoto.R;

/**
 * Created by duke on 16-10-16.
 */

public class TweenAnimation extends Activity implements View.OnClickListener {
    private Button mAlphaAnimationButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_tween_animation_layout);

        mAlphaAnimationButton = (Button)findViewById(R.id.tween_alpha_animation_button_id);
        mAlphaAnimationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tween_alpha_animation_button_id: {
                break;
            }
        }
    }
}