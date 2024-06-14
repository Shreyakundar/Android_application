package com.example.projectsem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
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

public class placedetailsadmin extends AppCompatActivity {
    private TextView Aplaceadmin,Adesadmin,Aactivitiesadmin,Aamountadmin,Amealsadmin;
    private ImageView HDplacePicadmin;
    String placeIDadmin="",imageurl;
    //String number;
   // AppCompatButton book;
private Button Abutton;
    String realpic;
    FirebaseAuth mAuth;
    String adplaceadmin,addescadmin,adamadmin,adactiadmin,admealsadmin;
    DatabaseReference databaseReference;
    private static final int galleryPic=1;
    private Uri ImageUri;
    // ImageView img;
    private StorageReference homepic;
   // DatabaseReference post1;
    ProgressDialog pg;
    //DatabaseReference databaseReference;
    String saveCurrentDate,currentTime,randomKey,saveCurrentTime;
    String downloadUri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placedetailsadmin);


        Aplaceadmin = findViewById(R.id.addnameadmin);
        Adesadmin = findViewById(R.id.adddesadmin);
        Aactivitiesadmin = findViewById(R.id.addactivitiesadmin);
        Aamountadmin = findViewById(R.id.addamountadmin);
        Amealsadmin=findViewById(R.id.addmealsadmin);
        HDplacePicadmin = findViewById(R.id.placeimgadmin);
//        Abutton=findViewById(R.id.bookadmin);
//        Abutton.setOnClickListener(v->
//        {
//            startActivity(new Intent(placedetailsadmin.this,MainPage.class));
//        });
        // book=findViewById(R.id.book);
        //=findViewById(R.id.imageView);
        //   post1= FirebaseDatabase.getInstance().getReference().child("BookPost");
//        homepic= FirebaseStorage.getInstance().getReference().child("BookPictures");
        pg = new ProgressDialog(this);
        placeIDadmin = getIntent().getStringExtra("pId");
        getPlaceDetails(placeIDadmin);
    }

    private void getPlaceDetails(String placeID)
    {
//        Toast.makeText(this, "toast msg", Toast.LENGTH_SHORT).show();
        DatabaseReference homeRef = FirebaseDatabase.getInstance().getReference().child("TravelPost");
        homeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    if (dataSnapshot.child("pId").getValue() != null) {
                        if (Objects.requireNonNull(dataSnapshot.child("pId").getValue()).toString().equals(placeID)) {
                            Model tour = dataSnapshot.getValue(Model.class);
                            Aplaceadmin.setText(tour.getTplace());
                            Adesadmin.setText(tour.getTdesc());
                            Aactivitiesadmin.setText(tour.getTacti());
                            Aamountadmin.append(tour.getTamnt());
                            Amealsadmin.setText(tour.getTmeals());
                            Picasso.get().load(tour.getTpic()).into(HDplacePicadmin);
                            break;
                        }
                    }
//                    Toast.makeText(placedetailsadmin.this, "" + dataSnapshot.child("pId").getValue(), Toast.LENGTH_SHORT).show();
                }}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}