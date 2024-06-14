package com.example.projectsem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        String url="android.resource://"+getPackageName()+"/"+R.raw.loading;
        Uri u=Uri.parse(url);
        VideoView videoView=findViewById(R.id.videoView3);
        videoView.setVideoURI(u);
        videoView.start();
       Handler handler=new Handler();
       handler.postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent i=new Intent(SplashScreen.this,dash2.class);
               startActivity(i);
               finish();
           }
       },5000);
//Intent i=new Intent(SplashScreen.this,recycler.class);
//startActivity(i);
    }

}