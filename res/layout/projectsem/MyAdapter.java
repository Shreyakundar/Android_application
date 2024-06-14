package com.example.projectsem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context context;
    List<Item> items;

    public MyAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    
    
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mainview,parent,false));
        
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//holder.nameView.setText(items.get(position).name);
holder.imageView.setImageResource(items.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        System.out.println("Size: "+items.size());
        Toast.makeText(context, ""+items.size(), Toast.LENGTH_SHORT).show();
        return items.size();
    }


}
