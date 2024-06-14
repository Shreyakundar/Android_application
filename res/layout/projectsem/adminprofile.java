package com.example.projectsem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class adminprofile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminprofile);
        Button adminupdate=findViewById(R.id.adminupdate);
        adminupdate.setOnClickListener(v->
        {
            Intent i=new Intent(adminprofile.this,dumb.class);
            startActivity(i);
        });

    }
}