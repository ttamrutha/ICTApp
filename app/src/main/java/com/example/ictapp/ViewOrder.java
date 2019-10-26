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
import com.example.ictapp.com.ict.adapter.OrderAdapter;
import com.example.ictapp.org.ict.configuration.AlertMessage;
import com.example.ictapp.org.ict.configuration.Config;
import com.example.ictapp.org.ict.pojos.CropDetails;
import com.example.ictapp.org.ict.pojos.OrderDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewOrder extends AppCompatActivity {
    RecyclerView recyclerView;
    OrderAdapter orderAdapter;
    Context context;
    int orderId, userId;
    List<OrderDetails> orderList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_list);
        init();

    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerviewCropList);
        context = this;
        orderList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        orderAdapter = new OrderAdapter(context, orderList);
        recyclerView.setAdapter(orderAdapter);
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
        if (userId > 0) {
            final ProgressDialog dialog = ProgressDialog.show(ViewOrder.this, null, "LOADING..");

            StringRequest strReq = new StringRequest(Request.Method.POST, Config.URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int rs = jsonObject.getInt("status");
                        if (rs == 0) {
                            JSONArray jsonArray = jsonObject.getJSONArray("orderList");
                            processResponse(jsonArray);
                        } else {
                            AlertMessage.callSnackBar(recyclerView, "Fail to fetch  Data..!!");
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
                    params.put("user_id", userId + "");

                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(strReq);
        } else {
            startActivity(new Intent(ViewOrder.this, LogActivity.class));
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
                    String cropOrderDate = jsonObject1.getString("crop_register_date");
                    String cropHarvestTime = jsonObject1.getString("crop_harvest_time");
                    String vendor_name = jsonObject1.getString("vendor_name");
                    int cropQuantity = jsonObject1.getInt("crop_quantity");
                    int cropPrice = jsonObject1.getInt("crop_price");
                    String cropCategory = jsonObject1.getString("crop_category");
                    int cropId = jsonObject1.getInt("crop_id");
                    OrderDetails orderDetails = new OrderDetails();
                    orderDetails.setCropName(cropName);
                    orderDetails.setCropDescription(cropDescription);
                    orderDetails.setOrderDate(cropHarvestTime);
                    orderDetails.setCropImage(cropImage);
                    orderDetails.setCropCategory(cropCategory);
                    orderDetails.setCropQuantity(cropQuantity);
                    orderDetails.setCropId(cropId);
                    orderDetails.setOrderDate(cropOrderDate);
                    orderDetails.setVendorName(vendor_name);
                    orderDetails.setPrice(cropPrice);
                    orderList.add(orderDetails);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
