package com.example.ictapp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ictapp.org.ict.configuration.AlertMessage;
import com.example.ictapp.org.ict.configuration.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewOrderDetails extends AppCompatActivity {
    TextView textViewCropName, textViewCropQuantity, textViewOrderDate, textViewPrice, textViewCropDescription, textViewVendorName;
    Button imageButtonConfirmOrder;
    ImageView imageViewCrop;
    int quantity, manage_order_id;
    String cropName, cropOrderDate, cropDescription, cropImage, vendorName;
    double price;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_details);
        init();
        eventListener();
    }

    public void init() {
        textViewCropDescription = findViewById(R.id.crop_description);
        textViewOrderDate = findViewById(R.id.orderDate);
        textViewCropName = findViewById(R.id.CropName);
        textViewCropQuantity = findViewById(R.id.Quantity);
        textViewPrice = findViewById(R.id.textViewPrice);
        textViewVendorName = findViewById(R.id.vendorName);
        imageButtonConfirmOrder = findViewById(R.id.buttonOrderConfirm);
        imageViewCrop = findViewById(R.id.imageView);

    }

    public void eventListener() {
        imageButtonConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateChanges(manage_order_id);
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    private void loadData() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            cropName = extras.getString("crop_name");
            cropDescription = extras.getString("crop_description");
            cropOrderDate = extras.getString("crop_order_date");
            vendorName = extras.getString("crop_harvest_time");
            quantity = extras.getInt("crop_quantity");
            manage_order_id = extras.getInt("manage_crop_id");
            cropImage = extras.getString("crop_image");
            price = extras.getDouble("crop_price");
            textViewPrice.setText(price + "");
            textViewCropQuantity.setText(quantity);
            textViewVendorName.setText(vendorName);
            textViewCropDescription.setText(cropDescription);
            textViewCropName.setText(cropName);
            textViewCropQuantity.setText(quantity);
            textViewOrderDate.setText(cropOrderDate);
            imageViewCrop.setImageBitmap(stringToBitMap(cropImage));
        }
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


    public void updateChanges(final double manage_order_id) {
        final ProgressDialog dialog = ProgressDialog.show(ViewOrderDetails.this, null, "LOADING..");

        StringRequest strReq = new StringRequest(Request.Method.POST, Config.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int rs = jsonObject.getInt("status");
                    if (rs == 0) {
                        AlertMessage.callSnackBar(imageButtonConfirmOrder, "Updated Successfully...");

                    } else {
                        AlertMessage.callSnackBar(imageButtonConfirmOrder, "Something went wrong!!! try again...");

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
                params.put("order_id", manage_order_id + "");

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq);
    }


}
