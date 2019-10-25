package com.example.ictapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ManageProfile extends AppCompatActivity {
    EditText editTextUserName, editTextPassword, editTextUpdate, editTextEmail, editTextPhone;
    String userName, password, phone, mail, fcmToken;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        eventListener();
    }

    private void init() {
        context = this;
        editTextPassword = findViewById(R.id.password);
        editTextUserName = findViewById(R.id.username);
        editTextEmail = findViewById(R.id.mail);
        editTextPhone = findViewById(R.id.phone);
        editTextUpdate = findViewById(R.id.signup);
        editTextEmail.setEnabled(false);
        editTextUserName.setEnabled(false);

    }

    private void eventListener() {

        editTextUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                userName = editTextUserName.getText().toString();
                password = editTextPassword.getText().toString();
                phone = editTextPassword.getText().toString();
                mail = editTextPassword.getText().toString();
                if (validate(password, phone)) {
                    updateProfile(password, phone);
                } else {
                    AlertMessage.callSnackBar(editTextUpdate, "Please check the input type...");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadProfile();
    }

    private void loadProfile() {
        final ProgressDialog dialog = ProgressDialog.show(ManageProfile.this, null, "LOADING..");

        StringRequest strReq = new StringRequest(Request.Method.POST, Config.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int rs = jsonObject.getInt("status");
                    if (rs == 0) {
                        processResponse(jsonObject);
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
                params.put("password", password);
                params.put("phone", phone);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq);
    }

    private void updateProfile(final String password, final String phone) {
        final ProgressDialog dialog = ProgressDialog.show(ManageProfile.this, null, "LOADING..");

        StringRequest strReq = new StringRequest(Request.Method.POST, Config.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int rs = jsonObject.getInt("status");
                    if (rs == 0) {
                        AlertMessage.callSnackBar(editTextUpdate, "Updated Successfully");
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
                params.put("password", password);
                params.put("phone", phone);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, "update");
    }

    private void processResponse(JSONObject rs) {
        try {
            userName = rs.getString("userName");
            mail = rs.getString("mail");
            password = rs.getString("password");
            phone = rs.getString("phone");
            editTextUserName.setText(userName);
            editTextEmail.setText(mail);
            editTextPassword.setText(password);
            editTextPhone.setText(phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean validate(String password, String phone) {
        if (password.length() > 5 && phone.length() == 10) {
            return false;
        } else {
            return false;
        }

    }

}
