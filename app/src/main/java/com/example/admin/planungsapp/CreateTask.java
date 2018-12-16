package com.example.admin.planungsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Diese Klasse dient dem Erstellen von Tasks
 * @author René Meisters
 * @version 1.0.0
 */
public class CreateTask extends AppCompatActivity {

    private EditText txtE_CreateTask_TaskName;
    private Button btn_CreateTask_addTask;
    private String taskName;
    private String projectName;
    private DatabaseReference database;

    /**
     *
     * Beim Starten dieser Activtvity werden alle Views den Instanzvariablen zugewiesen.
     * Es wird auf dem Button btn_CreateTask_addTask ein OnClickListener durchgeführt
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        //Views
        txtE_CreateTask_TaskName     = findViewById(R.id.txtTaskName);
        btn_CreateTask_addTask = findViewById(R.id.btnAddTasks);

        btn_CreateTask_addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });

    }

    /**
     * In dieser Methode werden Tasks zu dem Projekt in dem sich der Benutzer gerae befindet erstellt
     */
    private void addTask(){
        if(txtE_CreateTask_TaskName.getText().toString().equals("")) {
            txtE_CreateTask_TaskName.setHintTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            Intent oldIntent = getIntent();
            String Projekt = oldIntent.getStringExtra("projectName");
            database = FirebaseDatabase.getInstance().getReference().child("Projekte").child(Projekt);
            taskName = txtE_CreateTask_TaskName.getText().toString();
            // Task hinzufügen zum Projekt
            database.child("Anforderungen").child(taskName).setValue(taskName);
            // Zurück zur ViewProject Activity
            Intent intent = new Intent(getApplicationContext(), ViewProject.class);
            intent.putExtra("ProjektName", Projekt);
            startActivity(intent);
        }
    }
}
