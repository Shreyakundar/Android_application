package com.example.projectsem.homeInFeed;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectsem.R;
import com.example.projectsem.interfaces.homesInallcartInterface;

public class homesInallcart extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtallplace,txtallfees,txtallcheckin,txtallmobile,txtalluseremail;
    public homesInallcartInterface listener;
public Button button2;
    public homesInallcart(@NonNull View itemView) {
        super(itemView);
        this.txtallplace =  itemView.findViewById(R.id.txtallplace);;
        this.txtallfees =  itemView.findViewById(R.id.txtallfees);;
        this.button2=itemView.findViewById(R.id.button2);
        this.txtallcheckin=itemView.findViewById(R.id.txtallcheckin);
        this.txtallmobile=itemView.findViewById(R.id.txtallmobile);
        this.txtalluseremail=itemView.findViewById(R.id.txtalluseremail);
    }
    public void setItemClickListener(homesInallcartInterface listener)
    {
        this.listener=listener;
    }
    @Override
    public void onClick(View view) {
        listener.onClick(view,getAdapterPosition(),false);
    }
}

