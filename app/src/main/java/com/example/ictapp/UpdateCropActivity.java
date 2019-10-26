package com.example.ictapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ictapp.org.ict.configuration.AlertMessage;
import com.example.ictapp.org.ict.configuration.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UpdateCropActivity extends AppCompatActivity {
    TextView textViewCropName, textViewCropQuantity, textViewCropHarvestTime, textViewRegisterDate, textViewCropDescription;
    ImageButton imageButtonEdit, imageButtonQuantity, imageButtonDatePicker;
    EditText editTextUpdate;
    ImageView imageViewCrop;
    int quantity, manage_crop_id;
    String harvestDate, cropName, cropRegisterDate, cropDescription, cropImage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_crop);
        init();
        eventListener();
    }

    public void init() {
        textViewCropDescription = findViewById(R.id.textViewDescription);
        textViewCropHarvestTime = findViewById(R.id.textViewHarvestTime);
        textViewCropName = findViewById(R.id.cropName);
        textViewCropQuantity = findViewById(R.id.textViewQuantity);
        textViewRegisterDate = findViewById(R.id.textViewRegisterDate);
        imageButtonEdit = findViewById(R.id.imageButtonEdit);
        imageButtonDatePicker = findViewById(R.id.button_pick_date);
        imageButtonQuantity = findViewById(R.id.imageButtonUpdateQuantity);
        imageViewCrop = findViewById(R.id.imageViewCrop);
        editTextUpdate = findViewById(R.id.buttonUpdate);
        imageButtonQuantity.setVisibility(View.GONE);
        editTextUpdate.setVisibility(View.GONE);
        imageButtonDatePicker.setVisibility(View.GONE);

    }

    public void eventListener() {
        imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageButtonQuantity.setVisibility(View.VISIBLE);
                editTextUpdate.setVisibility(View.VISIBLE);
                imageButtonDatePicker.setVisibility(View.VISIBLE);
            }
        });
        imageButtonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
        imageButtonQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });
        editTextUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                harvestDate = textViewCropHarvestTime.getText().toString();
                quantity = Integer.parseInt(textViewCropQuantity.getText().toString());
                if (validate(harvestDate, quantity)) {
                    updateChanges(harvestDate, quantity);
                } else {
                    AlertMessage.callSnackBar(editTextUpdate, "Please input valid inputs!!!");
                }
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
            cropRegisterDate = extras.getString("crop_register_date");
            harvestDate = extras.getString("crop_harvest_time");
            quantity = extras.getInt("crop_quantity");
            manage_crop_id=extras.getInt("manage_crop_id");
            cropImage=extras.getString("crop_image");
            textViewCropHarvestTime.setText(harvestDate);
            textViewCropQuantity.setText(quantity);
            textViewRegisterDate.setText(cropRegisterDate);
            textViewCropDescription.setText(cropDescription);
            textViewCropName.setText(cropName);
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

    private boolean validate(String harvestDate, int quantity) {
        if (harvestDate != null && quantity > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        textViewCropHarvestTime.setText("HarvestTime:" + mDay + ":" + (dayOfMonth + 1) + ":" + mYear);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void updateChanges(final String harvestDate, final int quantity) {
        final ProgressDialog dialog = ProgressDialog.show(UpdateCropActivity.this, null, "LOADING..");

        StringRequest strReq = new StringRequest(Request.Method.POST, Config.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int rs = jsonObject.getInt("status");
                    if (rs == 0) {
                        AlertMessage.callSnackBar(editTextUpdate, "Updated Successfully...");

                    } else {
                        AlertMessage.callSnackBar(editTextUpdate, "Something went wrong!!! try again...");

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
                params.put("harvest_date", harvestDate);
                params.put("quantity", quantity + "");
                params.put("manage_crop_id", manage_crop_id + "");

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq);
    }

    public void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Quantity");
        alertDialog.setMessage("Enter Quantity");

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.logo);

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String quantity = input.getText().toString();
                        textViewCropQuantity.setText("Quantity:" + quantity + " KG");
                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }


}
