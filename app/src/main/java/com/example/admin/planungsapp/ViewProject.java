package com.example.admin.planungsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class ViewProject extends AppCompatActivity {

    TextView projectName;
    Button toAddTask, toAddDate, toCalender;
    ListView listTast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_view);

        //Views
        projectName = (TextView)findViewById(R.id.projectName);
        toAddDate = (Button)findViewById(R.id.btnToCreateDate);
        toAddTask = (Button)findViewById(R.id.btnToCreateTask);
        toCalender = (Button)findViewById(R.id.btnToCalender);
        Intent intent = getIntent();

        projectName.setText(intent.getStringExtra("ProjektName"));

        toAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toAddTask();
            }
        });

        toAddDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toAddDate();
            }
        });

        toCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toCalender();
            }
        });
    }

    private void toAddDate(){
        Intent toDate = new Intent(getApplicationContext(),MainCalender.class);
        Intent lastIntent = getIntent();
        // Replace with Create Activity
        toDate.putExtra("projectName", lastIntent.getStringExtra("ProjektName"));
        startActivity(toDate);
    }
    private void toAddTask(){
        Intent toDate = new Intent(getApplicationContext(),CreateTask.class);
        Intent lastIntent = getIntent();
        toDate.putExtra("projectName", lastIntent.getStringExtra("ProjektName"));
        startActivity(toDate);
    }
    private void toCalender(){
        Intent toDate = new Intent(getApplicationContext(),MainCalender.class);
        Intent lastIntent = getIntent();
        toDate.putExtra("projectName", lastIntent.getStringExtra("ProjektName"));
        startActivity(toDate);
    }


}
