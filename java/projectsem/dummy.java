package com.example.projectsem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class dummy extends AppCompatActivity {
    private DatabaseReference HomeRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
        Button btzzz=findViewById(R.id.button);
        HomeRef = FirebaseDatabase.getInstance().getReference().child("BookPost");
        btzzz.setOnClickListener(v->{
//            Model mode=new Model();
////            FirebaseRecyclerOptions<Model> option = new FirebaseRecyclerOptions.Builder<Model>().setQuery(HomeRef,Model.class).build();
            startActivity(new Intent(dummy.this,particularecycler.class));
//            Intent intent=new Intent();
//            intent.putExtra("pId", mode.getpId());
//            startActivity(intent);
        });
    }
}