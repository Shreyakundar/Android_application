package com.example.projectsem;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.adminapp.homeInFeed.homesInFeed;
import com.example.projectsem.homeInFeed.homesInFeed;
import com.example.projectsem.homeInFeed.homesmain2;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainPage extends AppCompatActivity {
    FirebaseAuth mAuth;
    private DatabaseReference HomeRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
//    private DatabaseReference storageReference;
private StorageReference storageReference;
    FirebaseUser firebaseUser;
    FloatingActionButton fb;
    String placeID = "", imageurl;
    private static final int galleryPic1 = 1;
    private Uri ImageUri;
    String downloadUri;
    ImageView imgedit;
    StorageReference homepic;
    String saveCurrentDate, currentTime, randomKey, saveCurrentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        // fb=(FloatingActionButton)findViewById(R.id.floatupdate);
//        fb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        })

        placeID = getIntent().getStringExtra("pId");
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);//"layoutManager"
        mAuth = FirebaseAuth.getInstance();
        HomeRef = FirebaseDatabase.getInstance().getReference().child("TravelPost");
        storageReference = FirebaseStorage.getInstance().getReference().child("TravelPost");
        mAuth = FirebaseAuth.getInstance();
        // databaseReference=FirebaseDatabase.getInstance().getReference().child("users");
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


    @Override
    protected void onStart() {
//        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        super.onStart();
        FirebaseRecyclerOptions<Model> option = new FirebaseRecyclerOptions.Builder<Model>().setQuery(HomeRef,Model.class).build();
        FirebaseRecyclerAdapter<Model,homesInFeed> adapter=new FirebaseRecyclerAdapter<Model,homesInFeed>(option) {
            @Override
            protected void onBindViewHolder(@NonNull homesInFeed holder, int position, @NonNull Model model) {
                holder.txtplace.setText(model.getTplace());
//              holder.txtfees.setText("hello");
                holder.txtfees.setText(model.getTamnt());


                holder.update.setOnClickListener((view) -> {
                    final DialogPlus dialogPlus = DialogPlus.newDialog(holder.HIFpic.getContext()).setContentHolder(new ViewHolder(R.layout.dialog_content)).setExpanded(true, 1000).create();

                    View myview = dialogPlus.getHolderView();

                    homepic = FirebaseStorage.getInstance().getReference().child("Pictures");

                    //final ImageView imageedit = myview.findViewById(R.id.imageedit);
                    final EditText enterplaceedit = myview.findViewById(R.id.enterplaceedit);
                    final EditText enteramountedit = myview.findViewById(R.id.enteramountedit);
                    final EditText enterdesedit = myview.findViewById(R.id.enterdesedit);
                    final EditText enteractivitiesedit = myview.findViewById(R.id.enteractivitiesedit);
                    final EditText entermealsedit = myview.findViewById(R.id.entermealedit);

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


//                    randomKey
//                    String filename=System.currentTimeMillis() +".jpg";
//
//                     final UploadTask uploadtask=file.putFile(ImageUri);
//                    imageedit.setOnClickListener(v -> {
//                        uploadGallery();
//                    });


                    Button updateedit = myview.findViewById(R.id.upload_btnedit);

                    enterplaceedit.setText(model.getTplace());
                    enterdesedit.setText(model.getTdesc());
                    enteractivitiesedit.setText(model.getTacti());
                    enteramountedit.setText(model.getTamnt());
                    entermealsedit.setText(model.getTmeals());


                    dialogPlus.show();

                    updateedit.setOnClickListener((v) -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("tplace", enterplaceedit.getText().toString());
                        map.put("tdesc", enterdesedit.getText().toString());
                        map.put("tamnt", enteramountedit.getText().toString());
                        map.put("tacti", enteractivitiesedit.getText().toString());
                        map.put("tmeals", entermealsedit.getText().toString());
//                        map.put("tpic",img);
//                        map.put("tpic",im);
                        FirebaseDatabase.getInstance().getReference().child("TravelPost").child(getRef(position).getKey()).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                dialogPlus.dismiss();
                            }
                        }).addOnFailureListener((e) -> {
                            dialogPlus.dismiss();
                        });
                    });

                });

                holder.delete.setOnClickListener((view) -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(holder.HIFpic.getContext());
                    builder.setTitle("Delete package");
                    builder.setMessage("Delete...?");
                    builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                        FirebaseDatabase.getInstance().getReference().child("TravelPost").child(getRef(position).getKey()).removeValue();
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                });
                    // holder.txtfees.setText(model.getAmountt());
//              String imageUri=null;
//              imageUri=model.getTpic();
//              Picasso.get().load(imageUri).into(holder.HIFpic);

//Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/travel-bees-9f21b.appspot.com/o/Pictures%2F100004549527%3A22%3A202315%3A06%3A65.jpg?alt=media&token=36a8ee44-0051-4f7d-9fd5-b7e39f8a58c0").into(holder.HIFpic);
                Picasso.get().load(model.getTpic()).into(holder.HIFpic);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(MainPage.this,placedetailsadmin.class);
                        intent.putExtra("pId", model.getpId());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public homesInFeed onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mainview, parent, false);
                homesInFeed holder = new homesInFeed(view1);
                return holder;

            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }}


