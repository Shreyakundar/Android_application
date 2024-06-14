package com.example.projectsem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class customerdetails extends AppCompatActivity {
TextView custplace,custprice;
String randomkey;
Button custsubmit;
    String placeID="",imageurl;
    FirebaseAuth mAuth;
    private StorageReference homepic;
    DatabaseReference post;
    ProgressDialog pg;
    String randomKey;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerdetails);
        custplace=findViewById(R.id.custplace);
        custprice=findViewById(R.id.custprice);

        placeID=getIntent().getStringExtra("pId");
        getPlaceDetails(placeID);
    }
    private void getPlaceDetails(String placeID)
    {
        Toast.makeText(this, "toast msg", Toast.LENGTH_SHORT).show();
        DatabaseReference homeRef = FirebaseDatabase.getInstance().getReference().child("TravelPost");
        homeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    if (dataSnapshot.child("pId").getValue() != null) {
                        if (Objects.requireNonNull(dataSnapshot.child("pId").getValue()).toString().equals(placeID)) {

                            Toast.makeText(customerdetails.this, "Love", Toast.LENGTH_SHORT).show();
                            Model tour = dataSnapshot.getValue(Model.class);
                            custplace.setText(tour.getTplace());
                            custprice.setText(tour.getTdesc());
                           // Aactivities.setText(tour.getTacti());
                            //Aamount.append(tour.getTamnt());
                           // Picasso.get().load(tour.getTpic()).into(HDplacePic);
                            break;
                        }
                    }
                    Toast.makeText(customerdetails.this, "" + dataSnapshot.child("pId").getValue(), Toast.LENGTH_SHORT).show();
                }}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}