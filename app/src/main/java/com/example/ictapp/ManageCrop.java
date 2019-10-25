package com.example.ictapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ictapp.org.ict.configuration.AlertMessage;
import com.example.ictapp.org.ict.configuration.Config;
import com.example.ictapp.org.ict.pojos.Category;
import com.example.ictapp.org.ict.pojos.Crop;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageCrop extends AppCompatActivity {
    MaterialSpinner spinnerCategory, spinnerCrop;
    List<Category> categoryList;
    List<Crop> cropList;
    String category[];
    String crop[];
    EditText editTextQuantity, editTextDescription, editTextAdd;
    int categoryId=0, cropId=0, quantity=0;
    String description=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_crop);

        init();
        eventListener();
    }

    private void eventListener() {
        //spinnerCategory.setItems("Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow");

        spinnerCategory.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                categoryId = categoryList.get(position).getCategoryId();
            }
        });
        // spinnerCrop.setItems("Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow");
        spinnerCrop.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                cropId = cropList.get(position).getCropId();
            }
        });
        editTextAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description = editTextDescription.getText().toString();
                quantity = Integer.parseInt(editTextQuantity.getText().toString());
                if(validate(description,quantity))
                {
                    requestAddCrop(description,quantity,categoryId,cropId);
                }
                else
                {
                    AlertMessage.callSnackBar(editTextAdd,"Please check the input type...");
                }
            }
        });
    }

    private void requestAddCrop(final String description, final int quantity, final int categoryId, final int cropId) {
        final ProgressDialog dialog = ProgressDialog.show(ManageCrop.this, null, "LOADING..");

        StringRequest strReq = new StringRequest(Request.Method.POST, Config.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int rs = jsonObject.getInt("status");
                    if (rs == 0) {
                        AlertMessage.callSnackBar(spinnerCategory, "Successfully Added ...");

                    } else {
                        AlertMessage.callSnackBar(spinnerCategory, "Error While fetching category...");
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
                params.put("crop_id", cropId+"");
                params.put("category_id", categoryId+"");
                params.put("crop_quantity", quantity+"");
                params.put("crop_description", description);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq);
    }

    private boolean validate(String description, int quantity) {
        if(description!=null&&categoryId>0&&cropId>0&&quantity>0)
        {
            return  true;
        }
        else
        {
            return  false;
        }
    }

    private void init() {
        spinnerCategory = (MaterialSpinner) findViewById(R.id.spinnerCategory);
        spinnerCrop = (MaterialSpinner) findViewById(R.id.spinnerCrop);
        editTextAdd = findViewById(R.id.signup);
        editTextDescription = findViewById(R.id.crop_description);
        editTextQuantity = findViewById(R.id.crop_quantity);


    }

    @Override
    protected void onStart() {
        super.onStart();
        loadCategory();
        loadCrop();
    }

    private void loadCrop() {
        final ProgressDialog dialog = ProgressDialog.show(ManageCrop.this, null, "LOADING..");

        StringRequest strReq = new StringRequest(Request.Method.POST, Config.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int rs = jsonObject.getInt("status");
                    if (rs == 0) {
                        processCropResponse(jsonObject);
                    } else {
                        AlertMessage.callSnackBar(spinnerCategory, "Error While fetching category...");
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

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq);
    }

    private void processCategoryResponse(JSONObject rs) {
        try {
            JSONArray jsonArray = rs.getJSONArray("categoryList");
            if (jsonArray.length() > 0) {
                category = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String categoryName = jsonObject1.getString("category");
                    int categoryId = jsonObject1.getInt("categoryId");
                    Category categoryObj = new Category();
                    categoryObj.setCategoryId(categoryId);
                    categoryObj.setCategoryName(categoryName);
                    category[i] = categoryName;
                    categoryList.add(categoryObj);
                }
                spinnerCategory.setItems(category);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void processCropResponse(JSONObject rs) {
        try {
            JSONArray jsonArray = rs.getJSONArray("categoryList");
            if (jsonArray.length() > 0) {
                crop = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String categoryName = jsonObject1.getString("crop");
                    int categoryId = jsonObject1.getInt("cropId");
                    Crop cropObj = new Crop();
                    cropObj.setCropId(categoryId);
                    cropObj.setCropName(categoryName);
                    crop[i] = categoryName;
                    cropList.add(cropObj);
                }
                spinnerCategory.setItems(category);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void loadCategory() {
        final ProgressDialog dialog = ProgressDialog.show(ManageCrop.this, null, "LOADING..");

        StringRequest strReq = new StringRequest(Request.Method.POST, Config.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int rs = jsonObject.getInt("status");
                    if (rs == 0) {
                        processCategoryResponse(jsonObject);
                    } else {
                        AlertMessage.callSnackBar(spinnerCategory, "Error While fetching category...");
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

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq);
    }
}
