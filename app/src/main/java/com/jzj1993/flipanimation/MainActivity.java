package com.jzj1993.flipanimation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;


public class MainActivity extends Activity {

    private View v1;
    private View v2;
    private CheckBox scale;
    private CheckBox reverse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        v1 = findViewById(R.id.a);
        v2 = findViewById(R.id.b);
        scale = (CheckBox) findViewById(R.id.scale);
        reverse = (CheckBox) findViewById(R.id.reverse);

        findViewById(R.id.bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v1, v2, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v1;
                v1 = v2;
                v2 = v;
            }
        });
    }

    private void flip(final View v1, final View v2, final boolean scale, boolean reverse) {
        final int duration = 300;
        final int degree = reverse ? 90 : -90;
        final int degree2 = -degree;

        final ObjectAnimator a, b;
        if (!scale) {
            a = ObjectAnimator.ofFloat(v1, "rotationY", 0, degree);
            b = ObjectAnimator.ofFloat(v2, "rotationY", degree2, 0);
        } else {
            final float scaleX = 0.5f;
            final float scaleY = 0.5f;
            a = ObjectAnimator.ofPropertyValuesHolder(v1,
                    PropertyValuesHolder.ofFloat("rotationY", 0, degree),
                    PropertyValuesHolder.ofFloat("scaleX", 1, scaleX),
                    PropertyValuesHolder.ofFloat("scaleY", 1, scaleY));
            b = ObjectAnimator.ofPropertyValuesHolder(v2,
                    PropertyValuesHolder.ofFloat("rotationY", degree2, 0),
                    PropertyValuesHolder.ofFloat("scaleX", scaleX, 1),
                    PropertyValuesHolder.ofFloat("scaleY", scaleY, 1));
        }

        a.setInterpolator(new LinearInterpolator());
        b.setInterpolator(new LinearInterpolator());
        a.setDuration(duration);
        b.setDuration(duration);

        a.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v1.setVisibility(View.GONE);
                v2.setVisibility(View.VISIBLE);
                if (scale) { // 恢复scale
                    v1.setScaleX(1);
                    v1.setScaleY(1);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        v1.setVisibility(View.VISIBLE);
        v2.setVisibility(View.GONE);

        AnimatorSet set = new AnimatorSet();
        set.play(a).before(b);
        set.start();
    }
}
