package com.example.projectsem;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class recycler extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        List<Item> items=new ArrayList<Item>();
        items.add(new Item("UDUPI", R.drawable.udupii));
        items.add(new Item("MYSORE",R.drawable.mysore));
        items.add(new Item("GOKARNA",R.drawable.gokarna));
        items.add(new Item("DANDELI",R.drawable.dandeli));
        items.add(new Item("HAMPI",R.drawable.hampi));
        items.add(new Item("COORG",R.drawable.coorg));

        items.add(new Item("SHIMOGA",R.drawable.a));
        items.add(new Item("CHIKKAMAGALURU",R.drawable.b));
        items.add(new Item("GOKARNA",R.drawable.c));

        items.add(new Item("SHIMOGA",R.drawable.a));
        items.add(new Item("CHIKKAMAGALURU",R.drawable.b));
        items.add(new Item("GOKARNA",R.drawable.c));




        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(this,items));
    }
}