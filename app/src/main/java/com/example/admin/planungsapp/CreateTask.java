package com.example.admin.planungsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateTask extends AppCompatActivity {

    EditText inpTask;
    Button btnAddTask;
    String taskName;
    String projectName;
    private DatabaseReference Database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        //Views
        inpTask = findViewById(R.id.txtTaskName);

        btnAddTask = findViewById(R.id.btnAddTasks);



        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });

    }

    private void addTask(){
        // Database.child("User_Projekt").child("Username").child(projectName).setValue(Name.getText().toString());
        Database = FirebaseDatabase.getInstance().getReference();
        Intent oldIntent = getIntent();
        taskName = inpTask.getText().toString();
       Database.child("Projekte").child(oldIntent.getStringExtra("projectName")).child("Anforderungen").child(taskName).setValue(taskName);
        Intent intent = new Intent(getApplicationContext(), ViewProject.class);
        intent.putExtra("ProjektName",projectName);
            startActivity(intent);
    }
}
