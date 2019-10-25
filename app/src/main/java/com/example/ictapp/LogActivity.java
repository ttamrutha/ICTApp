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

public class LogActivity extends AppCompatActivity {
    EditText editTextUserName, editTextPassword, editTextSignIn, editTextSignUp;
    String userName,password;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        eventListener();
    }

    private void init() {
        context=this;
        editTextPassword=findViewById(R.id.password);
        editTextUserName=findViewById(R.id.username);
        editTextSignIn=findViewById(R.id.signin);
        editTextSignUp=findViewById(R.id.signup);
    }

    private void eventListener() {

        editTextSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                userName=editTextUserName.getText().toString();
                password=editTextPassword.getText().toString();
                if(validate(userName,password))
                {
                    checkCredentials(userName,password);
                }
                else
                {
                    AlertMessage.callSnackBar(editTextSignIn,"eventListener");
                }
            }
        });
        editTextSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogActivity.this, RegisterActivity.class));

            }
        });
    }

    private void checkCredentials(final String userName, final String password) {
        final ProgressDialog dialog = ProgressDialog.show(LogActivity.this, null, "LOADING..");

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

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq);
    }

    private void processResponse(int rs) {
        if(rs==0)
        {
            startActivity(new Intent(LogActivity.this, HomeActivity.class));
            finish();

        }
        else
        {
            AlertMessage.callSnackBar(editTextSignIn,"Fail to login...");
        }

}

    private boolean validate(String userName,String password) {
        Pattern ptr = Pattern.compile("\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b");
        if(ptr.matcher(userName).matches()&&password.length()>5)
        {
          return  false;
        }
        else
        {
            return  false;
        }

    }

}
