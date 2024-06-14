package com.example.projectsem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class realprofile extends AppCompatActivity {
CircleImageView img;
EditText name;
    EditText phone;
    EditText gender;
    EditText city;
    EditText state;
    EditText address;
    ProgressDialog p;
    private StorageReference storageReference;
    private static final int galleryPic=1;
Button savereal,logoutreal;
String sname,sphone,sgender,scity,sstate,saddress;
//    String userId=getCurrentUserId();
    String placeID="",imageurl;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    private static final int galleryPic1=1;
    private Uri ImageUri;
    // ImageView img;
    private StorageReference homepic;
    DatabaseReference post1;
    ProgressDialog pg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realprofile);
        p = new ProgressDialog(this);
        name = findViewById(R.id.fullnamereal);
        phone = findViewById(R.id.fullmobilereal);
        gender = findViewById(R.id.fullgenderreal);
        city = findViewById(R.id.fullcityreal);
        state = findViewById(R.id.fullstatereal);
        address = findViewById(R.id.fulladdressreal1);
        savereal = findViewById(R.id.savebtnreal);
        logoutreal = findViewById(R.id.logoutbtnreal);
        logoutreal.setOnClickListener(v -> {
            mAuth.signOut();
//            p.setMessage("Logout");
//            p.show();
            startActivity(new Intent(realprofile.this, login.class));
        });
        String userId = getCurrentUserId();
        img = findViewById(R.id.imageViewprofilereal);
        img.setOnClickListener(v -> {
            uploadgallery();
        });


        //post1= FirebaseDatabase.getInstance().getReference().child("ProfilePost");
        pg = new ProgressDialog(this);
        placeID = getIntent().getStringExtra("pId");
        getPlaceDetails(userId);

        storageReference = FirebaseStorage.getInstance().getReference().child("ProfilePost");
        post1 = FirebaseDatabase.getInstance().getReference("ProfilePost");
        savereal.setOnClickListener(v -> {
            sname = name.getText().toString();
            sphone = phone.getText().toString();
            sgender = gender.getText().toString();
            scity = city.getText().toString();
            sstate = state.getText().toString();
            saddress = address.getText().toString();
            post1.orderByChild("Userid").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            DatabaseReference travelRef = dataSnapshot.getRef();
//                            Toast.makeText(realprofile.this, "HI", Toast.LENGTH_SHORT).show();
                            travelRef.child("useremail").setValue(sname);
                            travelRef.child("Mobileno").setValue(sphone);
                            travelRef.child("Gender").setValue(sgender);
                            travelRef.child("City").setValue(scity);
                            travelRef.child("Address").setValue(saddress);
//                            travelRef.child("tpic").setValue(imageUrl);
                            travelRef.child("State").setValue(sstate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
//                                        Toast.makeText(realprofile.this, "Updated successfully", Toast.LENGTH_SHORT).show();
//                           startActivity(new Intent(re));
                                    } else {
                                        Toast.makeText(realprofile.this, "Failed to update", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

    }
    private void uploadgallery() {
        Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,galleryPic1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==galleryPic&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            ImageUri=data.getData();



            img.setImageURI(ImageUri);
            uploadImageToStorage(ImageUri);
        }
    }
    private void uploadImageToStorage(Uri imageUri) {
        if (imageUri != null) {

            String fileName = System.currentTimeMillis() + ".jpg";
            StorageReference fileRef = storageReference.child(fileName);

            ProgressDialog progressDialog = new ProgressDialog(this);
//            progressDialog.setMessage("Uploading image...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();

            fileRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            saveImageUrlToProfile(imageUrl);
                        });
                        progressDialog.dismiss();
                    })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                    });
        }
    }
    private void saveImageUrlToProfile(String imageUrl) {
        String userId = getCurrentUserId();
        if (userId != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("ProfilePost");
            userRef.orderByChild("Userid").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            DatabaseReference profileRef = dataSnapshot.getRef();
                            profileRef.child("tpic").setValue(imageUrl)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
//                                            Toast.makeText(realprofile.this, "Image updated successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(realprofile.this, "Failed to update image", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
    private void getPlaceDetails(String userId)
    {
//        Toast.makeText(this, "toast msg", Toast.LENGTH_SHORT).show();
        DatabaseReference homeRef = FirebaseDatabase.getInstance().getReference().child("ProfilePost");
        homeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    if (dataSnapshot.child("Userid").getValue() != null) {
                        if (Objects.requireNonNull(dataSnapshot.child("Userid").getValue()).toString().equals(userId)) {


                            Model1 tour = dataSnapshot.getValue(Model1.class);
                           String name1=dataSnapshot.child("useremail").getValue().toString();
                           String phone1=dataSnapshot.child("Mobileno").getValue().toString();
                           String gender1=dataSnapshot.child("Gender").getValue().toString();
                           String city1=dataSnapshot.child("City").getValue().toString();
                           String state1=dataSnapshot.child("State").getValue().toString();
                           String img1=dataSnapshot.child("tpic").getValue().toString();
                           String address1=dataSnapshot.child("Address").getValue().toString();
                        // String address1=dataSnapshot.child("Address").getValue().toString();

//                            if (tour.getUid().matches("userId")) {
//                                Toast.makeText(realprofile.this, ""+userId, Toast.LENGTH_SHORT).show();
                                name.setText(name1);
                                phone.setText(phone1);
                                gender.setText(gender1);
                                city.setText(city1);
                                state.setText(state1);
                                address.setText(address1);
                            //address.setText(address1);
                                Picasso.get().load(img1).into(img);
                                break;

                        }
                    }
//                    Toast.makeText(realprofile.this, "" + dataSnapshot.child("pId").getValue(), Toast.LENGTH_SHORT).show();
                }}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public String getCurrentUserId() {
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            return firebaseUser.getEmail();
        } else {
            return null;
        }
    }
}