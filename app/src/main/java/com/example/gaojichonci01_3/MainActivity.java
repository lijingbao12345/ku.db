package com.example.gaojichonci01_3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tv;

    private ImageView img;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            int i = msg.what;
            tv2.setText("倒计时"+i);
            if (i == 4){
                final AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
                alphaAnimation.setDuration(1000);
                alphaAnimation.setFillAfter(true);
                img.startAnimation(alphaAnimation);
            }else if (i == 3){
                final ScaleAnimation scaleAnimation = new ScaleAnimation(1, 2, 1, 2
                        , Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setFillAfter(true);
                scaleAnimation.setDuration(1000);
                img.startAnimation(scaleAnimation);
            }else if (i == 2){
                final RotateAnimation rotateAnimation = new RotateAnimation(0, 720,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setDuration(1000);
                img.startAnimation(rotateAnimation);
            }else if (i == 1){

                final TranslateAnimation translateAnimation = new
                        TranslateAnimation(1, 360, 2, 1);
                translateAnimation.setDuration(1000);
                translateAnimation.setFillAfter(true);
                img.startAnimation(translateAnimation);
            }
            return false;
        }
    });
    private TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("dd");
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv = (TextView) findViewById(R.id.tv);
        tv2 = (TextView) findViewById(R.id.tv2);
        img = (ImageView) findViewById(R.id.img);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 4;
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i == 0) {
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        finish();
                    }
                    handler.sendEmptyMessage(i);
                    i--;
                }
            }
        }).start();


    }
}
