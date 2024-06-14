package com.example.projectsem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class dash2 extends AppCompatActivity {
    FirebaseAuth mAuth;
    ProgressDialog p;
CardView card1,card2,card3,card4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash2);
        card1=findViewById(R.id.cardU1);
        card4=findViewById(R.id.cardU4);
        card3=findViewById(R.id.cardU3);
card2=findViewById(R.id.cardU2);
        card1.setOnClickListener(v->{
            Intent i=new Intent(dash2.this,Mainpage2.class);
            startActivity(i);
        });
        card2.setOnClickListener(v->{
            startActivity(new Intent(dash2.this,realprofile.class));
        });
        card3.setOnClickListener(v->{
            startActivity(new Intent(dash2.this,about.class));
        });
        card4.setOnClickListener(v->{
            startActivity(new Intent(dash2.this,particularecycler.class));
//            mAuth.signOut();
//            p.setMessage("Logout");
//            p.show();
        });
    }
}