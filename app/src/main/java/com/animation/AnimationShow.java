package com.animation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.iphoto.R;

/**
 * Created by duke on 16-10-16.
 */

public class AnimationShow extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_study_layout);

        Button tweenAnimationButton = (Button) findViewById(R.id.tween_animation_button_id);
        tweenAnimationButton.setOnClickListener(this);

        Button viewAnimationButton = (Button) findViewById(R.id.property_animation_button_id);
        viewAnimationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tween_animation_button_id: {
                Intent intent = new Intent();
                intent.setClassName(AnimationShow.this, "com.animation.TweenAnimation");
                startActivity(intent);
                break;
            }
            case R.id.frame_animation_button_id: {
                break;
            }
            case R.id.property_animation_button_id: {
                Intent intent = new Intent();
                intent.setClassName(AnimationShow.this, "com.animation.PropertyAnimatorActivity");
                startActivity(intent);
                break;
            }
            default: {
                break;
            }
        }
    }
}