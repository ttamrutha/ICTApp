package com.example.ictapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
 
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {
 Context context;
    private List<HomeMenu> homemenuList;
 
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imageView;
 
        public MyViewHolder(View view) {
            super(view);
            title =  view.findViewById(R.id.textViewTitle);
            imageView =  view.findViewById(R.id.imageViewIcon);
        }
    }
 
 
    public MenuAdapter(List<HomeMenu> homemenuList,Context context) {
        this.homemenuList = homemenuList;
        this.context=context;
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_home_temp, parent, false);
 
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HomeMenu menu = homemenuList.get(position);
        holder.title.setText(menu.getTitle());
        Glide.with(context).load(menu.getImage()).into(holder.imageView);
    }
 
    @Override
    public int getItemCount() {
        return homemenuList.size();
    }
}