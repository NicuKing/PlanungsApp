package com.example.admin.planungsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class CreateTask extends AppCompatActivity {

    EditText inpTask;
    Button btnAddTask;
    String taskName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        //Views
        inpTask = findViewById(R.id.txtTaskName);
        btnAddTask = findViewById(R.id.btnAddTask);

    }
}
