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

        Button viewAnimationButton = (Button) findViewById(R.id.view_animation_button_id);
        viewAnimationButton.setOnClickListener(this);

        Button propertyAnimationButton = (Button) findViewById(R.id.property_animation_button_id);
        propertyAnimationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_animation_button_id: {
                Intent intent = new Intent();
                intent.setClassName(AnimationShow.this, "com.animation.ViewAnimation");
                startActivity(intent);
                break;
            }
            case R.id.drawable_animation_button_id: {
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