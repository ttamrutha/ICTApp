package com.example.ictapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ictapp.com.ict.adapter.CropAdapter;
import com.example.ictapp.org.ict.configuration.AlertMessage;
import com.example.ictapp.org.ict.configuration.Config;
import com.example.ictapp.org.ict.pojos.Category;
import com.example.ictapp.org.ict.pojos.Crop;
import com.example.ictapp.org.ict.pojos.CropDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewCropActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CropAdapter cropAdapter;
    Context context;
    int userId;
    List<CropDetails> cropList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_list);
       init();

    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerviewCropList);
        context = this;
        cropList=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        cropAdapter = new CropAdapter(context,cropList);
        recyclerView.setAdapter(cropAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getUserId();
        LoadCrops();
    }

    private void getUserId() {
        SharedPreferences sp = getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
         userId = sp.getInt(Config.KEY_NAME, 0);

    }

    private void LoadCrops() {
        if(userId>0) {
            final ProgressDialog dialog = ProgressDialog.show(ViewCropActivity.this, null, "LOADING..");

            StringRequest strReq = new StringRequest(Request.Method.POST, Config.URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int rs = jsonObject.getInt("status");
                        if(rs==0) {
                            JSONArray jsonArray=jsonObject.getJSONArray("cropList");
                            processResponse(jsonArray);
                        }
                        else
                        {
                            AlertMessage.callSnackBar(recyclerView,"Fail to fetch  Data..!!");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),
                            "Network error! Check your internet connection!" + error.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", userId+"");

                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(strReq);
        }
        else
        {
          startActivity(new Intent(ViewCropActivity.this,LogActivity.class));
          finish();
        }
    }

    private void processResponse(JSONArray rs) {
        try {
            if (rs.length() > 0) {

                for (int i = 0; i < rs.length(); i++) {
                    JSONObject jsonObject1 = rs.getJSONObject(i);
                     String cropName = jsonObject1.getString("crop_name");
                     String cropDescription = jsonObject1.getString("crop_description");
                     String cropImage = jsonObject1.getString("crop_image");
                     String cropRegisterDate = jsonObject1.getString("crop_register_date");
                     String cropHarvestTime = jsonObject1.getString("crop_harvest_time");
                     int cropQuantity = jsonObject1.getInt("crop_quantity");
                     String  cropCategory = jsonObject1.getString("crop_category");
                     int cropId = jsonObject1.getInt("crop_id");
                    CropDetails cropDetails = new CropDetails();
                        cropDetails.setCropName(cropName);
                        cropDetails.setCropDescription(cropDescription);
                        cropDetails.setCropHarvestTime(cropHarvestTime);
                        cropDetails.setCropRegisterDate(cropRegisterDate);
                        cropDetails.setCropImage(cropImage);
                        cropDetails.setCropCategory(cropCategory);
                        cropDetails.setCropQuantity(cropQuantity);
                        cropDetails.setCropId(cropId);
                    cropList.add(cropDetails);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
