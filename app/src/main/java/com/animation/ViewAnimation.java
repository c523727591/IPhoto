package com.animation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.iphoto.R;

/**
 * Created by duke on 16-10-16.
 */

public class ViewAnimation extends Activity implements View.OnClickListener {
    private Button mAlphaAnimationButton = null;
    private Button mTranslateAnimationButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_tween_animation_layout);

        mAlphaAnimationButton = (Button)findViewById(R.id.view_alpha_animation_button_id);
        mAlphaAnimationButton.setOnClickListener(this);

        mTranslateAnimationButton = (Button)findViewById(R.id.view_translate_animation_button_id);
        mTranslateAnimationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_alpha_animation_button_id: {
                break;
            }
            case R.id.view_translate_animation_button_id: {
                TranslateAnimation animation = new TranslateAnimation(0f, 500f, 0f, 500f);
                animation.setDuration(2000);
                animation.setFillAfter(true);
                mTranslateAnimationButton.startAnimation(animation);
                break;
            }
        }
    }
}