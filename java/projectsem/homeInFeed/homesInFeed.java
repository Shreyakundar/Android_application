package com.example.projectsem.homeInFeed;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.adminapp.R;
//import com.example.adminapp.interfaces.homesInFeedInterface;
import com.example.projectsem.R;
import com.example.projectsem.interfaces.homesInFeedInterface;
import com.example.projectsem.interfaces.homesInFeedcartInterface;

public class homesInFeed extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtplace,txtfees;
    public homesInFeedInterface listener;
    public ImageView HIFpic,update,delete;


    public homesInFeed(@NonNull View itemView) {
        super(itemView);
        txtplace=itemView.findViewById(R.id.txtplace);
        txtfees= itemView.findViewById(R.id.txtfees);
        HIFpic=itemView.findViewById(R.id.imageView);
        update=itemView.findViewById(R.id.update);
        delete=itemView.findViewById(R.id.delete);
    }

    public void setItemClickListener(homesInFeedInterface listener)
{
    this.listener=listener;
}

    @Override
    public void onClick(View view) {
        listener.onClick(view,getAdapterPosition(),false);
    }
}
