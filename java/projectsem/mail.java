package com.example.projectsem;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectsem.databinding.ActivityMailBinding;
import com.example.projectsem.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class mail extends AppCompatActivity {
    ActivityMailBinding binding;
    TextView  t;
    String placeId = "", imageurl;

    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);
        t=findViewById(R.id.emailAddress);
        user=getIntent().getStringExtra("user");
//        Toast.makeText(this, ""+user, Toast.LENGTH_SHORT).show();
        placeId = getIntent().getStringExtra("pIid");
        //t.setText(user+"mail");
        getPlaceDetails(placeId);
        binding= ActivityMailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.emailAddress.setText(user);
        binding.sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String email=binding.emailAddress.getText().toString();
                String subject=binding.subject.getText().toString();
                String message=binding.message.getText().toString();



                String[] addresses=email.split(",");
                Intent intent =new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL,addresses);
                intent.putExtra(Intent.EXTRA_SUBJECT,subject);
                intent.putExtra(Intent.EXTRA_TEXT,message);

                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent,"choose an email client"));


            }

        });
    }
    private void getPlaceDetails(String placeID)
    {
//        Toast.makeText(this, "toast msg"+placeID, Toast.LENGTH_SHORT).show();
        DatabaseReference homeRef = FirebaseDatabase.getInstance().getReference().child("BookPost");
        homeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    if (dataSnapshot.child("pIid").getValue() != null) {
                        if (Objects.requireNonNull(dataSnapshot.child("pIid").getValue()).toString().equals(placeID)) {
                            Model2 tour = dataSnapshot.getValue(Model2.class);
//                            Toast.makeText(razro_pay1.this, ""+amount, Toast.LENGTH_SHORT).show();
                            t.setText(tour.getTuname());

                            break;
                        }
                    }
//                    Toast.makeText(razro_pay1.this, "" + dataSnapshot.child("pIid").getValue(), Toast.LENGTH_SHORT).show();
                }}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });}
}