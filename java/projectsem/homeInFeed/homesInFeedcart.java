package com.example.projectsem.homeInFeed;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectsem.R;
import com.example.projectsem.interfaces.homesInFeedInterface;
import com.example.projectsem.interfaces.homesInFeedcartInterface;

public class homesInFeedcart extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtcartplace,txtcartfees,txtmail,txtcheckin,txtmobile;
    public homesInFeedcartInterface listener;
//    public homesInFeedcarInterface listener;
    // public ImageView HIFpic;

    public homesInFeedcart(@NonNull View itemView) {
        super(itemView);
        txtcartplace= itemView.findViewById(R.id.txtcartplace);
        txtcartfees= itemView.findViewById(R.id.txtcartfees);
        txtmail=itemView.findViewById(R.id.txtcartmail);
        txtcheckin=itemView.findViewById(R.id.txtcartcheckin);
        txtmobile=itemView.findViewById(R.id.txtcartmobile);
    }


//    public homesInFeed(@NonNull View itemView) {
//        super(itemView);
//        txtplace= itemView.findViewById(R.id.txtplace);
//        txtfees= itemView.findViewById(R.id.txtfees);
//        HIFpic=itemView.findViewById(R.id.imageView);
//    }


    public void setListener(homesInFeedcartInterface listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view,getAdapterPosition(),false);
    }
}

