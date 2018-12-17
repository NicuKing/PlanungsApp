package com.example.admin.planungsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ViewDate extends AppCompatActivity {

    private String value;

    public ViewDate(){}
    public ViewDate(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_date);
    }
}
