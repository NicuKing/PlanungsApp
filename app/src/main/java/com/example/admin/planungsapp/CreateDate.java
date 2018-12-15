package com.example.admin.planungsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import java.util.Calendar;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Diese Activity dient dem erstellen von Terminen
 * @author Sebastian Tüscher
 * @version 1.0.0
 */
public class CreateDate extends AppCompatActivity implements View.OnClickListener{

    private EditText inp_name, selectDate, selectTime;
    private Button btn_firebase;
    private DatabaseReference Database;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String dateName, projectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_date);

        Database = FirebaseDatabase.getInstance().getReference();

        inp_name = (EditText) findViewById(R.id.edit_text_dateTitle);
        selectDate = (EditText)findViewById(R.id.edit_text_datePicked);
        selectTime = (EditText)findViewById(R.id.edit_text_dateTime);
        btn_firebase = (Button)findViewById(R.id.btn_firebase);

        btn_firebase.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        createDate();
                    }
                });

        selectDate.setOnClickListener(this);
        selectTime.setOnClickListener(this);
    }

    /**
     *
     * @return namen vom Termmin
     */
    public String getDateName(){
        return dateName = inp_name.getText().toString();
    }
    public String getProjectName() { return projectName = getIntent().getStringExtra("projektName");}

    /**
     * Diese Methode erstellt den Termin
     */
    private void createDate(){
        Database.child("Projekte")
                .child(getProjectName())
                .child("Termine")
                .child(getDateName())
                .child("Datum").setValue(mYear + "." + mMonth + "." + mDay );
        Database.child("Projekte")
                .child(getProjectName())
                .child("Termine")
                .child(getDateName())
                .child("Zeit").setValue(mHour + ":" + mMinute);
    }
    @Override
    public void onClick(View view) {

        if (view == selectDate) {
            final Calendar c = Calendar.getInstance();
            /*mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);*/
            Intent intent = getIntent();
            mYear = intent.getIntExtra("year",2000);
            mMonth = intent.getIntExtra("month",10);
            mDay = intent.getIntExtra("dayOfMonth",10);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            selectDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (view == selectTime) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            selectTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, true);
            timePickerDialog.show();
        }
    }
}