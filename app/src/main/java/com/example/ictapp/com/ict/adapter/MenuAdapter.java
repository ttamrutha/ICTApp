package com.example.ictapp.com.ict.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ictapp.ManageMenuCropActivity;
import com.example.ictapp.ManageProfile;
import com.example.ictapp.NearDemands;
import com.example.ictapp.R;
import com.example.ictapp.ViewAlerts;
import com.example.ictapp.ViewFeedBack;
import com.example.ictapp.ViewOrder;


public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {
 Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imageView;
 
        public MyViewHolder(View view) {
            super(view);
            title =  view.findViewById(R.id.textViewTitle);
            imageView =  view.findViewById(R.id.imageViewIcon);
        }
    }
 
 
    public MenuAdapter(Context context) {
        this.context=context;
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_home_temp, parent, false);
 
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.title.setText(titles[position]);
      //  Glide.with(context).load(menu.getImage()).into(holder.imageView);
        holder.imageView.setImageResource(images[position]);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();

                switch (position)
                {
                    case 0:
                        intent.setClass(context, ManageMenuCropActivity.class);
                        break;
                    case 1:
                        intent.setClass(context, NearDemands.class);
                        break;
                    case 2:
                        intent.setClass(context, ViewAlerts.class);
                        break;
                    case 3:
                        intent.setClass(context, ViewOrder.class);
                        break;
                    case 4:
                        intent.setClass(context, ManageProfile.class);
                        break;
                    case 5:
                        intent.setClass(context, ViewFeedBack.class);
                        break;
                }
                context.startActivity(intent);
            }
        });
    }
 
    @Override
    public int getItemCount() {
        return images.length;
    }

    Integer[] images = {R.drawable.ic_stat_icon,R.drawable.ic_stat_near_you,R.drawable.ic_stat_notification,R.drawable.ic_stat_order,R.drawable.ic_stat_profile,R.drawable.ic_stat_feedback};
    String[] titles = {"Manage Crop","Vendor Demanding Near to you","View Alert's","View Order","Manage Profile","View Feedback"};

}