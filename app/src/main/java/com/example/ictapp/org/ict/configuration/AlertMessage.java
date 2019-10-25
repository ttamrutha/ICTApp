package com.example.ictapp.org.ict.configuration;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.ictapp.HomeActivity;
import com.example.ictapp.LogActivity;
import com.example.ictapp.R;
import com.google.android.material.snackbar.Snackbar;

public class AlertMessage {
    public static  void callSnackBar(View view, String message)
    {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

}
