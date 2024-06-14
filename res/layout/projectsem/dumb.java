package com.example.projectsem;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class dumb extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dumb);
        AppCompatButton registerbutn = findViewById(R.id.btnn2);
        registerbutn.setOnClickListener(v -> {
            Intent i = new Intent(dumb.this, register.class);
            startActivity(i);
            finish();

        });
    }
}