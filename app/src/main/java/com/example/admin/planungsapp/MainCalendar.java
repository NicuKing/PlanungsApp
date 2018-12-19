package com.example.admin.planungsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainCalendar extends AppCompatActivity {
    private  static final String TAG = "CalendarActivity";
    private CalendarView mCalendarView;
    private String ProjektName;
    private ArrayList<String> DateList = new ArrayList<String>();
    private ArrayList<String> Keys = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_main);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int dayOfMonth) {
                String date = year + "/" + month + "/"+ dayOfMonth ;
                Log.d(TAG, "onSelectedDayChange: yyyy/mm/dd:" + date);
                Intent intent = new Intent(MainCalendar.this,CreateDate.class);
                Intent lastIntent = getIntent();
                intent.putExtra("projektName",lastIntent.getStringExtra("projectName"));
                intent.putExtra("year",year);
                intent.putExtra("month",month);
                intent.putExtra("dayOfMonth",dayOfMonth);
                startActivity(intent);
            }
        });
        Intent projektName = getIntent();
        ProjektName = projektName.getStringExtra("projectName");
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Projekte").child(ProjektName).child("Termine");

        ListView ViewDateList = (ListView) findViewById(R.id.dateList);

        int[] to = { android.R.id.text1, android.R.id.text2 };
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, DateList);
        ViewDateList.setAdapter(arrayAdapter);

        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String date = "";
                int count = 1;
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    String key = dataSnapshot.getKey();
                    if(count % 2 == 0){
                        DateList.add(key + ":  " + date +" "+ child.getValue(String.class));
                        count = 1;
                    }
                    else{
                        date = child.getValue(String.class);
                        count++;
                    }
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String date = "";
                int count = 1;
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    String key = dataSnapshot.getKey();
                    if(count % 2 == 0){
                        DateList.add(key + ": " + date +" "+ child.getValue(String.class));
                        count = 1;
                    }
                    else{
                        date = child.getValue(String.class);
                        count++;
                    }
                }
                arrayAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
