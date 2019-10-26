package com.example.ictapp.org.ict.configuration;

import android.view.View;
import com.google.android.material.snackbar.Snackbar;

public class AlertMessage {
    public static  void callSnackBar(View view, String message)
    {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

}
