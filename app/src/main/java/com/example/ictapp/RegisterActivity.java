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

public class RegisterActivity extends AppCompatActivity {
    EditText editTextUserName, editTextPassword, editTextSignUp,editTextEmail,editTextPhone;
    String userName,password,phone,mail,fcmToken;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        eventListener();
    }

    private void init() {
        context=this;
        editTextPassword=findViewById(R.id.password);
        editTextUserName=findViewById(R.id.username);
        editTextEmail=findViewById(R.id.mail);
        editTextPhone=findViewById(R.id.phone);
        editTextSignUp=findViewById(R.id.signup);
    }

    private void eventListener() {

        editTextSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                userName=editTextUserName.getText().toString();
                password=editTextPassword.getText().toString();
                phone=editTextPassword.getText().toString();
                mail=editTextPassword.getText().toString();
                if(validate(mail,password,userName,phone))
                {
                    checkCredentials(userName,password,mail,phone);
                }
                else
                {
                    AlertMessage.callSnackBar(editTextSignUp,"Please check the input type...");
                }
            }
        });
    }

    private void checkCredentials(final String userName, final String password,final String mail,final String phone) {
        final ProgressDialog dialog = ProgressDialog.show(RegisterActivity.this, null, "LOADING..");

        StringRequest strReq = new StringRequest(Request.Method.POST, Config.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int rs = jsonObject.getInt("status");
                    processResponse(rs);
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
                params.put("user_name", userName);
                params.put("password", password);
                params.put("mail", mail);
                params.put("phone", phone);
                params.put("fcm_token", fcmToken);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, "update");
    }

    private void processResponse(int rs) {
        if(rs==0)
        {
            startActivity(new Intent(RegisterActivity.this, LogActivity.class));
            finish();

        }
        else
        {
            AlertMessage.callSnackBar(editTextSignUp,"Something went wrong!!!");
        }

    }

    private boolean validate(String email,String password,String userName,String phone) {
        Pattern ptr = Pattern.compile("\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b");
        if(ptr.matcher(email).matches()&&password.length()>5&&userName.length()>5&&phone.length()==10)
        {
            return  false;
        }
        else
        {
            return  false;
        }

    }

}
