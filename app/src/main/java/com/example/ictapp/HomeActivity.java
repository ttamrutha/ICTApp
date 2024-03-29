package com.example.ictapp;

import android.content.Context;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ictapp.com.ict.adapter.MenuAdapter;


public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MenuAdapter menuAdapter;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recyclerViewMenu);
        context = this;
        GridLayoutManager glm = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(glm);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        menuAdapter = new MenuAdapter(context);
        recyclerView.setAdapter(menuAdapter);


    }

}

