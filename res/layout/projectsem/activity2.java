package com.example.projectsem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        AppCompatButton btnadmin = findViewById(R.id.btnadmin);
        AppCompatButton btnuser = findViewById(R.id.btnuser);
        btnadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity2.this, activity3.class);
                startActivity(i);
                finish();
            }
            });


        btnuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity2.this, login.class);
                startActivity(i);
                finish();


            }
        });
    }}