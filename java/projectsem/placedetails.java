package com.example.projectsem;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class placedetails extends AppCompatActivity {
    private TextView Aplace, Ades, Aactivities, Aamount, Ameals;
    private ImageView HDplacePic;
    String placeID = "", imageurl;
    //String number;
    AppCompatButton book;
    public static String key;
    int year, month, day;
    EditText Auname, Amobile, Anumperson, Acheckin;
    String realpic;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    String adplace, addesc, adam, adacti, adMeals, aduname, admobile, adnumperson, adcheckin;
    DatabaseReference databaseReference,d;
    private static final int galleryPic = 1;
    private Uri ImageUri;
    // ImageView img;
    private StorageReference homepic;
    DatabaseReference post1;

    ProgressDialog pg;
    //DatabaseReference databaseReference;
    String saveCurrentDate, currentTime, randomKey, saveCurrentTime;
    String downloadUri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placedetails);
        Aplace = findViewById(R.id.addname);
        Ades = findViewById(R.id.adddes);
        Aactivities = findViewById(R.id.addactivities);
        Aamount = findViewById(R.id.addamount);
        Ameals = findViewById(R.id.addmeals);
        HDplacePic = findViewById(R.id.placeimg);
        Auname = findViewById(R.id.adduname);
        book = findViewById(R.id.book);
        d=FirebaseDatabase.getInstance().getReference().child("Checkin");
        post1 = FirebaseDatabase.getInstance().getReference().child("BookPost");
//        homepic= FirebaseStorage.getInstance().getReference().child("BookPictures");
        pg = new ProgressDialog(this);
        placeID = getIntent().getStringExtra("pId");
        getPlaceDetails(placeID);
        String userId = getCurrentUserId();
        Amobile = findViewById(R.id.addcmble);

        Amobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (validatemobile(Amobile.getText().toString())) {
                    book.setEnabled(true);

                } else {
                    book.setEnabled(false);
                    Amobile.setError("Invalid mobile number");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Calendar calendar = Calendar.getInstance();
//        Anumperson=findViewById(R.id.addperson);
        Acheckin = findViewById(R.id.cdateformat);
        Acheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(placedetails.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, dayOfMonth);

                        if (selectedDate.before(calendar)) {
                            Toast.makeText(placedetails.this, "Please select a future date", Toast.LENGTH_SHORT).show();
                        } else {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                            String selectedDateString = dateFormat.format(selectedDate.getTime());
                            Acheckin.setText(selectedDateString);
                            String date = Acheckin.getText().toString();
                            checkBookingAvailability(date);
//                            DatabaseReference bookedPostRef = FirebaseDatabase.getInstance().getReference().child("BookPost");
//
//                            Query query = bookedPostRef.orderByChild("tcheckin").equalTo(date);
//                            query.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//
//                                        if (dataSnapshot.child("pIid").getValue() != null) {
//                                            if (Objects.requireNonNull(dataSnapshot.child("pIid").getValue()).toString().equals(placeID)) {
////
//                                                    if (snapshot.exists()) {
//                                                        // Date already booked, disable booking or show a message
//                                                        Toast.makeText(placedetails.this, "This date is already booked", Toast.LENGTH_SHORT).show();
//                                                        // Disable the book button or take appropriate action
//                                                        book.setEnabled(false);
//                                                    } else {
//
//                                                        book.setEnabled(true);
//                                                    }
//
//                                                }
//
//                                             }}}
//
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//                                    // Handle the error
//                                }
//                            });
//                            Acheckin.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                        }
                    }
                }, year, month, day);
//                Acheckin.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                calendar.add(Calendar.DAY_OF_MONTH, -1);
//                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });

        //=findViewById(R.id.imageView);
        post1 = FirebaseDatabase.getInstance().getReference().child("BookPost");
//        homepic= FirebaseStorage.getInstance().getReference().child("BookPictures");
        pg = new ProgressDialog(this);
        placeID = getIntent().getStringExtra("pId");
        getPlaceDetails(placeID);

//adplace=Aplace.getText().toString();
//addesc=Ades.getText().toString();
//adam=Aamount.getText().toString();
//adacti=Aactivities.getText().toString();
//adMeals=Ameals.getText().toString();
        book.setOnClickListener(v -> {

            aduname = Auname.getText().toString();
            admobile = Amobile.getText().toString();
//            adnumperson=Anumperson.getText().toString();
            adcheckin = Acheckin.getText().toString();
            if (TextUtils.isEmpty(aduname)) {
                Auname.setError("Fill your email to proceed");
//                Toast.makeText(this, "Fill all the detsto proceed", Toast.LENGTH_SHORT).show();
            }
            else  if (!isValidEmail(aduname)) {
                Auname.setError("Invalid email");
                return;
            }
            else if (TextUtils.isEmpty(admobile)) {
                Amobile.setError("Fill your mobile to proceed");
//                Toast.makeText(this, "Fill all the detsto proceed", Toast.LENGTH_SHORT).show();
            }
//
            else if (TextUtils.isEmpty(adcheckin)) {
                Acheckin.setError("Fill your checkin date to proceed");
//
            } else {
UpdateDatabase();
                //datasnapshot
                //Model model = new Model();
//                Toast.makeText(this, "toast msg", Toast.LENGTH_SHORT).show();
                DatabaseReference homeRef = FirebaseDatabase.getInstance().getReference().child("Checkin");
                homeRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            if (dataSnapshot.child("pIid").getValue() != null) {
                                if (Objects.requireNonNull(dataSnapshot.child("pIid").getValue()).toString().equals(placeID)) {
                                    Model2 tour = dataSnapshot.getValue(Model2.class);
//                                    Toast.makeText(placedetails.this,""+tour.getpIid(),Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(placedetails.this, razro_pay1.class);
                                    intent.putExtra("pId", tour.getpIid());
                                    intent.putExtra("place", tour.getTplace());
                                    intent.putExtra("amount", tour.getTamnt());
                                    intent.putExtra("uname", tour.getTuname());
                                    intent.putExtra("mobile", tour.getTmobile());
                                    intent.putExtra("checkin", tour.getTcheckin());
                                    intent.putExtra("pic", tour.getTpic());
                                    startActivity(intent);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }
    private void checkBookingAvailability(String date) {
        DatabaseReference bookedPostRef = FirebaseDatabase.getInstance().getReference().child("BookPost");

        Query query = bookedPostRef.orderByChild("tcheckin").equalTo(date);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                if (dataSnapshot.child("pIid").getValue() != null) {
                    if (Objects.requireNonNull(dataSnapshot.child("pIid").getValue()).toString().equals(placeID)) {
                if (snapshot.exists()) {
                    // Date already booked, disable booking or show a message
                    Toast.makeText(placedetails.this, "This date is already booked", Toast.LENGTH_SHORT).show();
                    // Disable the book button or take appropriate action
                    book.setEnabled(false);
                } else {
                    // Date is available for booking, enable the book button
                    book.setEnabled(true);
                }}}}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }



    boolean validatemobile(String input) {
        Pattern p = Pattern.compile("[6-9][0-9]{9}");
        Matcher m = p.matcher(input);
        return m.matches();
    }


//                    }}}});
    // UpdateDatabase();

//            //realpic=HDplacePic.get
//            adplace = Aplace.getText().toString();
//            addesc = Ades.getText().toString();
//            adacti = Aactivities.getText().toString();
//            adam = Aamount.getText().toString();
//
//
//            Calendar calendar = Calendar.getInstance();
//            SimpleDateFormat currentDate = new SimpleDateFormat("mm:dd:yyyy");
//            saveCurrentDate = currentDate.format(calendar.getTime());
//            SimpleDateFormat currentTime = new SimpleDateFormat("HH:MM:SS");
//            saveCurrentTime = currentTime.format(calendar.getTime());
//            randomKey = saveCurrentDate + saveCurrentTime;
//            if (randomKey == null) {
//                saveCurrentDate = currentDate.format(calendar.getTime());
//                saveCurrentDate = currentTime.format(calendar.getTime());
//            }
//            final StorageReference file = homepic.child(ImageUri.getLastPathSegment() + randomKey + ".jpg");
//
//            final UploadTask uploadtask = file.putFile(ImageUri);
//            uploadtask.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    String message = e.toString();
//                    Toast.makeText(placedetails.this, "Failed to upload image"+message, Toast.LENGTH_SHORT).show();
//                }
//            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    // Toast.makeText(imageupload.this, "Image uploaded sucessfully", Toast.LENGTH_SHORT).show();
//                    Task<Uri> urlTask = uploadtask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                        @Override
//                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                            if (!task.isSuccessful()) {
//                                throw task.getException();
//                            }
//                            downloadUri = file.getDownloadUrl().toString();
//                            return file.getDownloadUrl();
//
//                            //return null;
//                        }
//                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Uri> task) {
//                            if (task.isSuccessful()) {
//                                downloadUri = task.getResult().toString();
//                                Toast.makeText(placedetails.this, "Done", Toast.LENGTH_SHORT).show();
//                                UpdateDatabase();
//                            }
//                        }
//                    });
//                }
//            });


    public String getCurrentUserId() {
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            return firebaseUser.getEmail();
        } else {
            return null;
        }
    }
    boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void UpdateDatabase() {
//        pg.setMessage("Uploading");
//        pg.show();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("mm:dd:yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:MM:SS");
        saveCurrentTime = currentTime.format(calendar.getTime());
        randomKey = saveCurrentDate + saveCurrentTime;
        if (randomKey == null) {
            saveCurrentDate = currentDate.format(calendar.getTime());
            saveCurrentDate = currentTime.format(calendar.getTime());
        }
        adplace = Aplace.getText().toString();
        addesc = Ades.getText().toString();
        adacti = Aactivities.getText().toString();
        adam = Aamount.getText().toString();
        adMeals = Ameals.getText().toString();
        aduname = Auname.getText().toString();
        admobile = Amobile.getText().toString();
//        adnumperson = Anumperson.getText().toString();
        adcheckin = Acheckin.getText().toString();
        String userId = getCurrentUserId();
//            Model tour = dataSnapshot.getValue(Model.class);
//            String piic=tour.getTpic())
        HashMap<String, Object> map = new HashMap<>();
        map.put("pIid", placeID);
        map.put("tplace", adplace);
        map.put("tdesc", addesc);
        map.put("tamnt", adam);
        map.put("tacti", adacti);
        map.put("tmeals", adMeals);
        map.put("tuname", aduname);
        map.put("tmobile", admobile);
//        map.put("tnumperson", adnumperson);
        map.put("tcheckin", adcheckin);
        map.put("user", userId);
//        map.put("tpic", HDplacePic);
//            key=post1.push().getKey();
        d.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    pg.dismiss();
//                    Toast.makeText(placedetails.this, "Stored", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(placedetails.this, customer2.class));
//                    Model model = new Model();
//                    Intent intent = new Intent(placedetails.this, razro_pay.class);
//                    intent.putExtra("pId", model.getpId());
//                    startActivity(intent);

                } else {
                    pg.dismiss();
                    Toast.makeText(placedetails.this, "Failed" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }

        });
//

//        startActivity(new Intent(placedetails.this, dummy.class));
//
    }

    private void getPlaceDetails(String placeID) {
        String userId = getCurrentUserId();

        DatabaseReference homeRef = FirebaseDatabase.getInstance().getReference().child("TravelPost");
        homeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("pId").getValue() != null) {
                        if (Objects.requireNonNull(dataSnapshot.child("pId").getValue()).toString().equals(placeID)) {
                            Model tour = dataSnapshot.getValue(Model.class);

                            Aplace.setText(tour.getTplace());
                            Ades.setText(tour.getTdesc());
                            Aactivities.setText(tour.getTacti());
                            Aamount.setText(tour.getTamnt());
                            Ameals.setText(tour.getTmeals());
//                            Toast.makeText(placedetails.this, "toast msg"+userId, Toast.LENGTH_SHORT).show();
                            Auname.setText(userId);
                            Picasso.get().load(tour.getTpic()).into(HDplacePic);
//                            UpdateDatabase();
                            break;
                        }
                    }
//                    Toast.makeText(placedetails.this, "" + dataSnapshot.child("pId").getValue(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
