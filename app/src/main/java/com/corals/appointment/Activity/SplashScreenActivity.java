package com.corals.appointment.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.corals.appointment.R;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView imageView_logo;
    TextView textView_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageView_logo=findViewById(R.id.image_logo);
        textView_title=findViewById(R.id.text_app_title);

        Animation animation= AnimationUtils.loadAnimation(this,R.anim.fadein_anim);
        imageView_logo.startAnimation(animation);
        textView_title.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView_logo.clearAnimation();
                textView_title.clearAnimation();

                Intent in=new Intent(SplashScreenActivity.this,LoginActivity.class);
            /*    Pair[] pairs=new Pair[2];
                pairs[0] =new Pair<View,String>(imageView_logo,"image_logo");
                pairs[1] =new Pair<View,String>(textView_title,"text_app_title");
                ActivityOptions activityOptions=ActivityOptions.makeSceneTransitionAnimation(SplashScreenActivity.this,pairs);*/
                startActivity(in);
                finish();
            }
        },3000);
    }
}
