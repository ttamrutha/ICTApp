package com.example.ictapp.com.ict.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.ictapp.R;
import com.example.ictapp.ViewOrderDetails;
import com.example.ictapp.org.ict.pojos.OrderDetails;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    Context context;
    List<OrderDetails> orderList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCropName,textViewCropCategory;
        ImageView imageViewCropPhoto;
        ImageButton imageButtonView;
        public MyViewHolder(View view) {
            super(view);
        }
    }


    public OrderAdapter(Context context,    List<OrderDetails> orderList) {
        this.context = context;
        this.orderList=orderList;
    }

    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_temp, parent, false);

        return new OrderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderAdapter.MyViewHolder holder, final int position) {
        final OrderDetails orderDetails = orderList.get(position);
        final String cropName = orderDetails.getCropName();
        final String cropDescription = orderDetails.getCropDescription();
        final String cropImage = orderDetails.getCropImage();
        final String cropOrderDate = orderDetails.getOrderDate();
        final String cropHarvestTime = orderDetails.getCropHarvestTime();
        final String vendorName = orderDetails.getVendorName();
        final int cropQuantity = orderDetails.getCropQuantity();
        final double price = orderDetails.getPrice();
        String cropCategory = orderDetails.getCropCategory();
        final int manageCropId = orderDetails.getCropId();
        holder.textViewCropCategory.setText(cropCategory);
        holder.textViewCropName.setText(cropName);
        Bitmap bitmap = stringToBitMap(cropImage);
        holder.imageViewCropPhoto.setImageBitmap(bitmap);
        holder.imageButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ViewOrderDetails.class);
                i.putExtra("crop_name", cropName);
                i.putExtra("crop_description", cropDescription);
                i.putExtra("crop_order_date", cropOrderDate);
                i.putExtra("crop_harvest_time", cropHarvestTime);
                i.putExtra("crop_quantity", cropQuantity);
                i.putExtra("vendor_name", vendorName);
                i.putExtra("crop_price", price);
                i.putExtra("crop_image", cropImage);
                i.putExtra("manage_crop_id", manageCropId);
                context.startActivity(i);
            }
    });
        }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


    public Bitmap stringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}