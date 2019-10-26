package com.example.ictapp.com.ict.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ictapp.R;
import com.example.ictapp.UpdateCropActivity;
import com.example.ictapp.org.ict.pojos.CropDetails;

import java.util.List;

public class CropAdapter extends RecyclerView.Adapter<CropAdapter.MyViewHolder> {
    Context context;
    List<CropDetails> cropList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCropName, textViewCropCategory, textViewCropDescription, textViewHarvestTime, textViewRegisterDate, textViewQuantity;
        ImageView imageViewCropImage;
        ImageButton imageButtonEdit, imageButtonDelete;

        public MyViewHolder(View view) {
            super(view);
            textViewCropName = view.findViewById(R.id.textViewCropName);
            textViewCropDescription = view.findViewById(R.id.textViewDescription);
            textViewCropCategory = view.findViewById(R.id.textViewCategory);
            textViewHarvestTime = view.findViewById(R.id.textViewHarvestTime);
            textViewQuantity = view.findViewById(R.id.textViewQuantity);
            textViewRegisterDate = view.findViewById(R.id.textViewRegisterDate);
            imageViewCropImage = view.findViewById(R.id.imageViewCropImage);
            imageButtonEdit = view.findViewById(R.id.imageButtonEdit);
            imageButtonDelete = view.findViewById(R.id.imageButtonDelete);

        }
    }


    public CropAdapter(Context context, List<CropDetails> cropList) {
        this.context = context;
        this.cropList = cropList;
    }

    @Override
    public CropAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crop_list_temp, parent, false);

        return new CropAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CropAdapter.MyViewHolder holder, final int position) {
        final CropDetails cropDetails = cropList.get(position);
        final String cropName = cropDetails.getCropName();
        final String cropDescription = cropDetails.getCropDescription();
        final String cropImage = cropDetails.getCropImage();
        final String cropRegisterDate = cropDetails.getCropRegisterDate();
        final String cropHarvestTime = cropDetails.getCropHarvestTime();
        final int cropQuantity = cropDetails.getCropQuantity();
        String cropCategory = cropDetails.getCropCategory();
        final int manageCropId = cropDetails.getCropId();
        holder.textViewRegisterDate.setText(cropRegisterDate);
        holder.textViewQuantity.setText(cropQuantity);
        holder.textViewCropCategory.setText(cropCategory);
        holder.textViewHarvestTime.setText(cropHarvestTime);
        holder.textViewCropName.setText(cropName);
        holder.textViewCropDescription.setText(cropDescription);
        Bitmap bitmap = stringToBitMap(cropImage);
        holder.imageViewCropImage.setImageBitmap(bitmap);
        holder.imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, UpdateCropActivity.class);
                i.putExtra("crop_name", cropName);
                i.putExtra("crop_description", cropDescription);
                i.putExtra("crop_register_date", cropRegisterDate);
                i.putExtra("crop_harvest_time", cropHarvestTime);
                i.putExtra("crop_quantity", cropQuantity);
                i.putExtra("crop_image", cropImage);
                i.putExtra("manage_crop_id", manageCropId);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cropList.size();
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