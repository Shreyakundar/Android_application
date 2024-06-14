package com.example.projectsem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectsem.homeInFeed.homesInFeed;
import com.example.projectsem.homeInFeed.homesmain2;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Mainpage2 extends AppCompatActivity {
    FirebaseAuth mAuth;
    private DatabaseReference HomeRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage2);

        recyclerView =findViewById(R.id.recycler2);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager( this);
        recyclerView.setLayoutManager(layoutManager);//"layoutManager"
        mAuth = FirebaseAuth.getInstance();
        HomeRef = FirebaseDatabase.getInstance().getReference().child("TravelPost");
        auth= FirebaseAuth.getInstance();
    }


    @Override
    protected void onStart() {
//        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        super.onStart();
        FirebaseRecyclerOptions<Model> option = new FirebaseRecyclerOptions.Builder<Model>().setQuery(HomeRef,Model.class).build();
        FirebaseRecyclerAdapter<Model, homesmain2> adapter=new FirebaseRecyclerAdapter<Model, homesmain2>(option) {
            @Override
            protected void onBindViewHolder(@NonNull homesmain2 holder, int position, @NonNull Model model) {
                holder.txtplacemain2.setText(model.getTplace());
//              holder.txtfees.setText("hello");
                holder.txtfeesmain2.setText(model.getTamnt());

                // holder.txtfees.setText(model.getAmountt());
//              String imageUri=null;
//              imageUri=model.getTpic();
//              Picasso.get().load(imageUri).into(holder.HIFpic);

//Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/travel-bees-9f21b.appspot.com/o/Pictures%2F100004549527%3A22%3A202315%3A06%3A65.jpg?alt=media&token=36a8ee44-0051-4f7d-9fd5-b7e39f8a58c0").into(holder.HIFpic);
                Picasso.get().load(model.getTpic()).into(holder.HIFpicmain2);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(Mainpage2.this,placedetails.class);
                        intent.putExtra("pId", model.getpId());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public homesmain2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main2view, parent, false);
                homesmain2 holder = new homesmain2(view);
                return holder;

            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
}}