package com.example.projectsem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

public class dash extends AppCompatActivity {
FirebaseAuth mAuth;
CardView c1,c2,c3,c4;
ProgressDialog p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        p=new ProgressDialog(this);
        c1=findViewById(R.id.card1);
        c2=findViewById(R.id.card2);
        c3=findViewById(R.id.card3);
        c4=findViewById(R.id.card4);
        c1.setOnClickListener(v->{
            startActivity(new Intent(dash.this,MainPage.class));
        });
        c3.setOnClickListener(v->{
            startActivity(new Intent(dash.this,allcustrecycler.class));
        });
        c2.setOnClickListener(v->{
            startActivity(new Intent(dash.this,imageupload.class));
        });
        c4.setOnClickListener(new View.OnClickListener()
                              {
                                  @Override
                                  public void onClick(View view){
                                      p.setMessage("Logging out");
                                      p.setCanceledOnTouchOutside(false);
                                      p.show();
                                      mAuth.signOut();
                                      p.dismiss();
                                  Intent j=new Intent(dash.this,login.class);
                                  startActivity(j);


            }

        });

//        ImageView imgout=findViewById(R.id.logout);
//
//        imgout.setOnClickListener(v->{
//mAuth.signOut();
//        });

    }
}