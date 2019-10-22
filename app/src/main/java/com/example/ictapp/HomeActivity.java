package com.example.ictapp;

import android.content.Context;
import android.graphics.Movie;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MenuAdapter menuAdapter;
    List<HomeMenu> menuList;
    Context context;
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setContentView(R.layout.activity_home);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMenu);
        context=this;
        menuAdapter = new MenuAdapter(menuList,context);

        recyclerView.setHasFixedSize(true);

        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        // adding inbuilt divider line
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(menuAdapter);

        // row click listener
      /*  recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Movie movie = movieList.get(position);
                Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/

        prepareMenuData();
    }

    private void prepareMenuData() {

        String[] titles = getResources().getStringArray(R.array.menu_items);
        String[] images = getResources().getStringArray(R.array.menu_images);
        for(int i=0;i<titles.length;i++)
        {
            HomeMenu men=new HomeMenu();
            men.setTitle(titles[i]);
            men.setImage(images[i]);
            menuList.add(men);
        }

    }
}

