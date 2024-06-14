package com.example.projectsem.homeInFeed;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectsem.R;
import com.example.projectsem.interfaces.homesInmain2Interface;

public class homesmain2 extends RecyclerView.ViewHolder implements View.OnClickListener{
public TextView txtplacemain2,txtfeesmain2;
public homesInmain2Interface listener;
public ImageView HIFpicmain2;

public homesmain2(@NonNull View itemView){
        super(itemView);
        txtplacemain2=itemView.findViewById(R.id.txtplacemain2);
        txtfeesmain2=itemView.findViewById(R.id.txtfeesmain2);
        HIFpicmain2=itemView.findViewById(R.id.imageViewmain2);
        }

public void setItemClickListener(homesInmain2Interface listener)
        {
        this.listener=listener;
        }

@Override
public void onClick(View view){
        listener.onClick(view,getAdapterPosition(),false);
        }
        }


