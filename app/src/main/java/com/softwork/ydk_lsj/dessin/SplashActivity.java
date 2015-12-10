package com.softwork.ydk_lsj.dessin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class SplashActivity extends Activity {
    private ImageView splashImage;

    private int openningTime = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashImage = (ImageView) findViewById(R.id.splash_image);

        startSplash();
    }

    public void startSplash() {
        splashImage.animate().alpha(1.0f)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(openningTime)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        Intent scanActivity = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(scanActivity);
                        SplashActivity.this.finish();
                    }
                })
                .start();
    }

}
