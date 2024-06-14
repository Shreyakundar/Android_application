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
import android.widget.Button;

import com.example.projectsem.homeInFeed.homesInFeed;
import com.example.projectsem.homeInFeed.homesInFeedcart;
import com.example.projectsem.homeInFeed.homesInallcart;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class allcustrecycler extends AppCompatActivity {
    FirebaseAuth mAuth;
    private DatabaseReference HomeRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private FirebaseAuth auth;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allcustrecycler);

        recyclerView = findViewById(R.id.allcust);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);//"layoutManager"
        mAuth = FirebaseAuth.getInstance();
        HomeRef = FirebaseDatabase.getInstance().getReference().child("BookPost");
        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
//        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        super.onStart();
        FirebaseRecyclerOptions<Model2> option = new FirebaseRecyclerOptions.Builder<Model2>().setQuery(HomeRef, Model2.class).build();
        FirebaseRecyclerAdapter<Model2, homesInallcart> adapter;
        adapter = new FirebaseRecyclerAdapter<Model2, homesInallcart>(option) {
            @Override
            protected void onBindViewHolder(@NonNull homesInallcart holder, int position, @NonNull Model2 model) {
                holder.txtallplace.setText(model.getTplace());
//              holder.txtfees.setText("hello");
                holder.txtallfees.setText(model.getTamnt());
                holder.txtallcheckin.setText(model.getTcheckin());
                holder.txtallmobile.setText(model.getTmobile());
                holder.txtalluseremail.setText(model.getTuname());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(allcustrecycler.this, placedetails.class);
//                        intent.putExtra("pIid", model.getpId());
//                        startActivity(intent);
                    }
                });
                holder.button2.setOnClickListener(v -> {
                    Intent i=new Intent(allcustrecycler.this,mail.class);
                    i.putExtra("pIid", model.getpIid());
                    i.putExtra("user",holder.txtalluseremail.getText().toString());
                    startActivity(i);
//                    startActivity(new Intent(allcustrecycler.this, mail.class));

                });
            }



//              imageUri=model.getTpic();
//              Picasso.get().load(imageUri).into(holder.HIFpic);

//Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/travel-bees-9f21b.appspot.com/o/Pictures%2F100004549527%3A22%3A202315%3A06%3A65.jpg?alt=media&token=36a8ee44-0051-4f7d-9fd5-b7e39f8a58c0").into(holder.HIFpic);
                //   Picasso.get().load(model.getTpic()).into(holder.HIFpic);
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(allcustrecycler.this, placedetails.class);
//                        intent.putExtra("pId", model.getpId());
//                        startActivity(intent);
//                    }
//                });
//                holder.button2.setOnClickListener(v -> {
//                    startActivity(new Intent(allcustrecycler.this, mail.class));
//                });
//            }

            @NonNull
            @Override
            public homesInallcart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewall, parent, false);
                homesInallcart holder = new homesInallcart(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}