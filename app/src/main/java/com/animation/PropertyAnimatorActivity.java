package com.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.iphoto.R;

/**
 * Created by duke on 16-10-24.
 */

public class PropertyAnimatorActivity extends Activity {
    private Button mButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_animator_activity_layout);

        mButton = (Button) findViewById(R.id.property_animator_ok_button_id);
        // test1();
        test2();
    }

    private void test1() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 500f);

                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float deltaY = (float)animation.getAnimatedValue();
                        mButton.setTranslationY(deltaY);
                        mButton.setTranslationX(deltaY);
                    }
                });

                valueAnimator.setDuration(3000);  // default 300ms
                valueAnimator.start();
                */
                if (Build.VERSION.SDK_INT >= 21) {
                    int startColor = 0xffff0000;  // red
                    int endColor = 0xff00ff00;  // green
                    ValueAnimator valueAnimator = ValueAnimator.ofArgb(startColor, endColor);
                    valueAnimator.setDuration(3000);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int color = (int)animation.getAnimatedValue();
                            mButton.setBackgroundColor(color);
                        }
                    });
                    valueAnimator.start();
                }
            }
        });
    }

    private void test2() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animator animator = AnimatorInflater.loadAnimator(PropertyAnimatorActivity.this, R.animator.incorporate_animator);
                animator.setTarget(mButton);
                animator.start();
            }
        });
    }
}
