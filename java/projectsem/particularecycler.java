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

import com.example.projectsem.homeInFeed.homesInFeedcart;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class particularecycler extends AppCompatActivity {
FirebaseAuth mAuth;
FirebaseUser firebaseUser;
    private DatabaseReference HomeRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
FirebaseUser firebaseuser;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        recyclerView =findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager( this);
        recyclerView.setLayoutManager(layoutManager);//"layoutManager"
       // mAuth = FirebaseAuth.getInstance();
        HomeRef = FirebaseDatabase.getInstance().getReference().child("BookPost");
        auth= FirebaseAuth.getInstance();
    }
    @Override
    protected void onStart() {
//        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        super.onStart();
String userId=getCurrentUserId();
        FirebaseRecyclerOptions<Model> option = new FirebaseRecyclerOptions.Builder<Model>().setQuery(FirebaseDatabase.getInstance().getReference().child("BookPost").orderByChild("user").equalTo(userId),Model.class).build();
       FirebaseRecyclerAdapter<Model, homesInFeedcart> adapter=new FirebaseRecyclerAdapter<Model, homesInFeedcart>(option) {
           @Override
           protected void onBindViewHolder(@NonNull homesInFeedcart holder, int position, @NonNull Model model) {
             //  String userid = getCurrentUserId();
               holder.txtcartplace.setText(model.getTplace());
               holder.txtcartfees.setText(model.getTamnt());
               holder.txtmail.setText(userId);
               holder.txtcheckin.setText(model.getTcheckin());
               holder.txtmobile.setText(model.getTmobile());
               holder.itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
//                       HomeRef=FirebaseDatabase.getInstance().getReference();
//                       DatabaseReference userBookingref=HomeRef.child("BookPost").child(userId)
//                       Intent intent = new Intent(particularecycler.this, dummy.class);
//                       intent.putExtra("pIid", model.getpIid());
//                       startActivity(intent);
                   }
               });

           }

//               public String getCurrentUserId () {
//                   mAuth = FirebaseAuth.getInstance();
//                   firebaseuser = mAuth.getCurrentUser();
//                   if (firebaseUser != null) {
//                       return firebaseUser.get
//                   }
//else
//                   {
//                       return null;
//                   }
//               }

           @NonNull
           @Override
           public homesInFeedcart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewcart, parent, false);
               homesInFeedcart holder=new homesInFeedcart(view);
               return holder;
           }
       };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    public String getCurrentUserId(){
        mAuth=FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();
        if(firebaseUser!=null){
            return  firebaseUser.getEmail();
        }
        else
        {
            return  null;

        }
    }
}
//            @Override
//            protected void onBindViewHolder(@NonNull homesInFeed holder, int position, @NonNull Model model) {
//                holder.txtplace.setText(model.getTplace());
////              holder.txtfees.setText("hello");
//                holder.txtfees.setText(model.getTamnt());
//                // holder.txtfees.setText(model.getAmountt());
////              String imageUri=null;
////              imageUri=model.getTpic();
////              Picasso.get().load(imageUri).into(holder.HIFpic);
//
////Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/travel-bees-9f21b.appspot.com/o/Pictures%2F100004549527%3A22%3A202315%3A06%3A65.jpg?alt=media&token=36a8ee44-0051-4f7d-9fd5-b7e39f8a58c0").into(holder.HIFpic);
//               // Picasso.get().load(model.getTpic()).into(holder.HIFpic);
////                holder.itemView.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        Intent intent=new Intent(MainPage.this,placedetails.class);
////                        intent.putExtra("pId", model.getpId());
////                        startActivity(intent);
////                    }
////                });
//            }
//
//            @NonNull
//            @Override
//            public homesInFeed onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
//                homesInFeed holder = new homesInFeed(view);
//                return holder;
//
//            }
//        };
//        recyclerView.setAdapter(adapter);
//        adapter.startListening();
//    }
