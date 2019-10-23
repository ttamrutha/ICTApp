package com.example.ictapp.com.ict.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ictapp.R;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);
        }
    }


    public OrderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_temp, parent, false);

        return new OrderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderAdapter.MyViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }


}