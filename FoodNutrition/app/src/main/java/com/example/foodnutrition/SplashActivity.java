package com.example.foodnutrition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                nextActivityTimer();
            }
        }.start();
    }

    private void nextActivityTimer() {
        Intent main = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(main);
        finish();
    }
}