package com.example.ictapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ManageMenuCropActivity extends AppCompatActivity {
    TextView textViewCropView,textViewAddCrop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_crop_menu);
        init();
        eventListener();

    }

    private void init() {
        textViewAddCrop=findViewById(R.id.addcrop);
        textViewCropView=findViewById(R.id.addcrop);

    }

    private void eventListener() {
        textViewAddCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageMenuCropActivity.this,ManageCrop.class));
            }
        });
        textViewCropView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageMenuCropActivity.this,ViewCropActivity.class));

            }
        });
    }

}
