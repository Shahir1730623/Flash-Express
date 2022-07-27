package com.example.flashexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.flashexpress.LoginSignup.Login;

public class IntroductoryScreen extends AppCompatActivity {

    ImageView logo;
    Animation topanim;
    private static int SPLASH_SCREEN = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory_screen);

        topanim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        logo = findViewById(R.id.logo);
        logo.setAnimation(topanim);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(IntroductoryScreen.this, Login.class);
            startActivity(intent);
            finish();

        }, SPLASH_SCREEN);

    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}