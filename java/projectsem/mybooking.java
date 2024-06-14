package com.example.projectsem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class mybooking extends AppCompatActivity {
RecyclerView recyclerView;
DatabaseReference databaseReference;
MyAdapter myAdapter;
FirebaseAuth mAuth;
FirebaseUser firebaseUser;
    ArrayList<Model>list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybooking);
        recyclerView=findViewById(R.id.userlist);
        databaseReference= FirebaseDatabase.getInstance().getReference("BookPost");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
list=new ArrayList<>() ;
    myAdapter=new MyAdapter(this,list);
    recyclerView.setAdapter(myAdapter);
//    String userId=getCurrentUserId();
//
//        FirebaseRecyclerOptions<Model>options=new FirebaseRecyclerOptions.Builder<Model>()
//                .setQuery(FirebaseDatabase.getInstance().getReference().child("BookPost").orderByChild("user").equalTo(userId),Model.class).build();
//        myAdapter=new MyAdapter(options);
//        recyclerView.setAdapter(myAdapter);
    databaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                Model model=dataSnapshot.getValue(Model.class);
                list.add(model);

            }
            myAdapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
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