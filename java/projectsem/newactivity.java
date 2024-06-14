package com.example.projectsem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class newactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newactivity);
        Button btn=findViewById(R.id.btn);
        btn.setOnClickListener(v->{
            Intent i=new Intent(newactivity.this,login.class);
            startActivity(i);
        });
    }
}