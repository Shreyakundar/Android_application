package com.example.projectsem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    EditText fullname,fullmobile,fullcity,fullstate,fulladdress;
    Button save;
    FirebaseAuth mAuth;
    String afullname,afullmobile,afullcity,afullstate,afulladdress,gen;
    DatabaseReference databaseReference;
    private static final int galleryPic=1;
    private Uri ImageUri;
   CircleImageView img;
   RadioGroup radioGenderGroup;
   RadioButton radioGenderButton;
   FirebaseUser firebaseUser;
    private StorageReference homepic;
    DatabaseReference post2;
    ProgressDialog pg;
    //DatabaseReference databaseReference;
    String saveCurrentDate,currentTime,randomKey,saveCurrentTime;
    String downloadUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        fullname = findViewById(R.id.fullname);
        fullmobile = findViewById(R.id.fullmobile);

       // fullgender=findViewById(R.id.fullgender);
        fullcity = findViewById(R.id.fullcity);
        fulladdress=findViewById(R.id.fulladdress);
        img=findViewById(R.id.imageViewprofile);
        fullstate = findViewById(R.id.fullstate);
        radioGenderGroup=findViewById(R.id.radioGender1);

        save= findViewById(R.id.savebtn);
        fullmobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(validatemobile(fullmobile.getText().toString())){
save.setEnabled(true);

                }
                else
                {
                    save.setEnabled(false);
                    fullmobile.setError("Invalid mobile number");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/travel-bees-9f21b.appspot.com/o/Pictures%2F100004549527%3A22%3A202315%3A06%3A65.jpg?alt=media&token=36a8ee44-0051-4f7d-9fd5-b7e39f8a58c0").into(img);
       databaseReference= FirebaseDatabase.getInstance().getReference().child("adminupload");
        post2=FirebaseDatabase.getInstance().getReference().child("ProfilePost");
        homepic= FirebaseStorage.getInstance().getReference().child("Pictures1");
        pg=new ProgressDialog(this);
        img.setOnClickListener(v->{
            uploadgallery();
        });
        save.setOnClickListener(v->{
            collectData();
        });
    }
    boolean validatemobile(String input){
        Pattern p=Pattern.compile("[6-9][0-9]{9}");
        Matcher m=p.matcher(input);
        return m.matches();
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
        int selectedId=radioGenderGroup.getCheckedRadioButtonId();
        radioGenderButton=findViewById(selectedId);
        gen=radioGenderButton.getText().toString();
        afullname=fullname.getText().toString();
        afullmobile=fullmobile.getText().toString();
      //  afullgender=fullgender.getText().toString();
        afullcity=fullcity.getText().toString();
        afullstate=fullstate.getText().toString();
        afulladdress=fulladdress.getText().toString();
        if(TextUtils.isEmpty(afullname))
        {
            fullname.setError("Enter fullname");
        }
        else if(TextUtils.isEmpty(afulladdress))
        {
            fulladdress.setError("Enter address");
        }
        else if(TextUtils.isEmpty(afullmobile))
        {
            fullmobile.setError("Enter mobile number");
        }
//        else if(afullgender.isEmpty())
//        {
//            fullgender.setError("Enter amount");
//        }
        else if(TextUtils.isEmpty(afullcity)){
            fullcity.setError(" Enter city");
        }
        else if(TextUtils.isEmpty(afullstate)){
            fullstate.setError("Enter state");
        }

        else
            storeImageData();
    }

    private void storeImageData(){
        pg.setMessage("Saving..");
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
                Toast.makeText(Profile.this, "Failed to upload image"+message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(Profile.this, "Image uploaded sucessfully", Toast.LENGTH_SHORT).show();
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
                           // firebaseUser=mAuth.getCurrentUser();
                            downloadUri=task.getResult().toString();
//                            Toast.makeText(Profile.this, "Saved", Toast.LENGTH_SHORT).show();
                            UpdateDatabase();

                        }
                    }
                });
            }
        });
    }
    private void UpdateDatabase(){
        String userId=getCurrentUserId();
        HashMap<String,Object> map=new HashMap<>();
        map.put("pId",randomKey);
        map.put("useremail",afullname);
        map.put("Mobileno",afullmobile);
        map.put("Gender",gen);
        map.put("City",afullcity);
        map.put("Address",afulladdress);
        map.put("tpic",downloadUri);
        map.put("State",afullstate);
        map.put("Userid",userId);
        post2.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    pg.dismiss();
//                    Toast.makeText(Profile.this, "Sucessfull", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Profile.this,SplashScreen.class));
                } else {
                    pg.dismiss();
                    Toast.makeText(Profile.this, "Failed" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }

        });
//

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
