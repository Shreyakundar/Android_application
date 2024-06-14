package com.example.projectsem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class imageupload extends AppCompatActivity {
    EditText placename, description, amount, activities, meals;
    Button upload;
    FirebaseAuth mAuth;
    String place, desc, am, acti, mealss;
    DatabaseReference databaseReference;
    private static final int galleryPic = 1;
    private Uri ImageUri;
    ImageView img;
    private StorageReference homepic;
    DatabaseReference post;
    ProgressDialog pg;
    //DatabaseReference databaseReference;
    String saveCurrentDate, currentTime, randomKey, saveCurrentTime;
    String downloadUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageupload);
        placename = findViewById(R.id.enterplace);
        description = findViewById(R.id.enterdes);
        meals = findViewById(R.id.entermeals);
        amount = findViewById(R.id.enteramount);
        img = findViewById(R.id.imageView);
        activities = findViewById(R.id.enteractivities);
        upload = findViewById(R.id.upload_btn);
        amount.setFilters(new InputFilter[]{new NumberInputFilter()});
//        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/travel-bees-9f21b.appspot.com/o/Pictures%2F100004549527%3A22%3A202315%3A06%3A65.jpg?alt=media&token=36a8ee44-0051-4f7d-9fd5-b7e39f8a58c0").into(img);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("adminupload");
        post = FirebaseDatabase.getInstance().getReference().child("TravelPost");
        homepic = FirebaseStorage.getInstance().getReference().child("Pictures");
        pg = new ProgressDialog(this);
        img.setOnClickListener(v -> {
            uploadgallery();
        });
        upload.setOnClickListener(v -> {
            collectData();
        });
    }

    class NumberInputFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            String pattern = "[0-9]+"; // Regular expression pattern for numbers only

            // Check if the input matches the pattern
            if (source.toString().matches(pattern)) {
                return null; // Accept the input
            }

            return ""; // Reject the input
        }
    }









    private void uploadgallery() {
        Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,galleryPic);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==galleryPic&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            ImageUri=data.getData();
            img.setImageURI(ImageUri);
        }
    }

    public void collectData(){
        place=placename.getText().toString();
        desc=description.getText().toString();
        am=amount.getText().toString();
         acti=activities.getText().toString();
        mealss=meals.getText().toString();

        if(place.isEmpty())
        {
            placename.setError("Enter placename");
        }
        else if(desc.isEmpty())
        {
            description.setError("Enter description");
        }
        else if(am.isEmpty())
        {
            amount.setError("Enter amount");
        }
        else if(acti.isEmpty()){
            activities.setError(" Enter activities");
        }
        else if(mealss.isEmpty()){
            meals.setError("Enter meals");
        }
        else
            storeImageData();
    }

private void storeImageData(){
    pg.setMessage("Uploading");
    pg.show();
    Calendar calendar= Calendar.getInstance();
    SimpleDateFormat currentDate=new SimpleDateFormat("mm:dd:yyyy");
    saveCurrentDate = currentDate.format(calendar.getTime());
    SimpleDateFormat currentTime=new SimpleDateFormat("HH:MM:SS");
    saveCurrentTime = currentTime.format(calendar.getTime());
    randomKey=saveCurrentDate+saveCurrentTime;
    if(randomKey==null){
        saveCurrentDate=currentDate.format(calendar.getTime());
        saveCurrentDate=currentTime.format(calendar.getTime());
    }
    //randomKey
    //String filename=System.currentTimeMillis() +".jpg";
    final StorageReference file=homepic.child(ImageUri.getLastPathSegment()+randomKey+".jpg");

    final UploadTask uploadtask=file.putFile(ImageUri);
    uploadtask.addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            String message=e.toString();
            Toast.makeText(imageupload.this, "Failed to upload image"+message, Toast.LENGTH_SHORT).show();
        }
    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//            Toast.makeText(imageupload.this, "Image uploaded sucessfully", Toast.LENGTH_SHORT).show();
        Task<Uri> urlTask=uploadtask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()){
                    throw task.getException();
                }
                downloadUri=file.getDownloadUrl().toString();
                return file.getDownloadUrl();

                //return null;
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    downloadUri=task.getResult().toString();
//                    Toast.makeText(imageupload.this, "Done", Toast.LENGTH_SHORT).show();
                    UpdateDatabase();
                }
            }
        });
        }
    });
}
private void UpdateDatabase(){
    HashMap<String,Object>map=new HashMap<>();
    map.put("pId",randomKey);
    map.put("tplace",place);
    map.put("tdesc",desc);
    map.put("tamnt",am);
    map.put("tacti",acti);
    map.put("tpic",downloadUri);
    map.put("tmeals",mealss);
    post.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                pg.dismiss();
                Toast.makeText(imageupload.this, "Uploaded sucessfull", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(imageupload.this,MainPage.class));
            } else {
                pg.dismiss();
                Toast.makeText(imageupload.this, "Failed" + task.getException().toString(), Toast.LENGTH_SHORT).show();
            }
        }

    });
//

}
}