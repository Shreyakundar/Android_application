package com.example.projectsem;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class onboarding1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding1);
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent =new Intent(onboarding1.this,onboarding2.class);
                startActivity(intent);
                finish();
            }
        },1000);
    }
}