package com.example.ictapp.com.ict.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ictapp.R;

public class CropAdapter extends RecyclerView.Adapter<CropAdapter.MyViewHolder> {
    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);
        }
    }


    public CropAdapter(Context context) {
        this.context = context;
    }

    @Override
    public CropAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crop_list_temp, parent, false);

        return new CropAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CropAdapter.MyViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }


}