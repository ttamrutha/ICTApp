package com.example.ictapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class UpdateCropActivity extends AppCompatActivity  {
    TextView textViewCropName,textViewCropQuantity,textViewCropHarvestTime,textViewRegisterDate,textViewCropDescription;
    ImageButton imageButtonEdit,imageButtonQuantity,imageButtonDatePicker;
    EditText editTextUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_crop);
        init();
        eventListener();
    }
    public void init()
    {
        textViewCropDescription=findViewById(R.id.textViewDescription);
        textViewCropHarvestTime=findViewById(R.id.textViewHarvestTime);
        textViewCropName=findViewById(R.id.cropName);
        textViewCropQuantity=findViewById(R.id.textViewQuantity);
        textViewRegisterDate=findViewById(R.id.textViewRegisterDate);
        imageButtonEdit=findViewById(R.id.imageButtonEdit);
        imageButtonDatePicker=findViewById(R.id.button_pick_date);
        imageButtonQuantity=findViewById(R.id.imageButtonUpdateQuantity);
        editTextUpdate=findViewById(R.id.buttonUpdate);
        imageButtonQuantity.setVisibility(View.GONE);
        editTextUpdate.setVisibility(View.GONE);
        imageButtonDatePicker.setVisibility(View.GONE);

    }
    public void eventListener()
    {
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

    }
  public void showDatePicker()
  {
      final Calendar c = Calendar.getInstance();
      final int mYear = c.get(Calendar.YEAR);
      int mMonth = c.get(Calendar.MONTH);
      final int mDay = c.get(Calendar.DAY_OF_MONTH);

      DatePickerDialog datePickerDialog = new DatePickerDialog(this,
              new DatePickerDialog.OnDateSetListener() {

                  @Override
                  public void onDateSet(DatePicker view, int year,
                                        int monthOfYear, int dayOfMonth) {

                    textViewCropHarvestTime.setText("HarvestTime:" +mDay+":"+(dayOfMonth+1)+":"+mYear);
                  }
              }, mYear, mMonth, mDay);
      datePickerDialog.show();
  }
  public void updateChanges()
  {

  }
  public void showAlertDialog()
  {
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
                     String  quantity = input.getText().toString();
                        textViewCropQuantity.setText("Quantity:"+quantity+" KG");
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
