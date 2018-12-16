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

    private EditText inpTask;
    private Button btnAddTask;
    private String taskName;
    private String projectName;
    private DatabaseReference database;

    /**
     *
     * Beim Starten dieser Activtvity werden alle Views den Instanzvariablen zugewiesen.
     * Es wird auf dem Button btnAddTask ein OnClickListener durchgeführt
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        //Views
        inpTask     = findViewById(R.id.txtTaskName);
        btnAddTask = findViewById(R.id.btnAddTasks);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
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
        if(inpTask.getText().toString().equals("")) {
            inpTask.setHintTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            Intent oldIntent = getIntent();
            String Projekt = oldIntent.getStringExtra("projectName");
            database = FirebaseDatabase.getInstance().getReference().child("Projekte").child(Projekt);
            taskName = inpTask.getText().toString();
            // Task hinzufügen zum Projekt
            database.child("Anforderungen").child(taskName).setValue(taskName);
            // Zurück zur ViewProject Activity
            Intent intent = new Intent(getApplicationContext(), ViewProject.class);
            intent.putExtra("ProjektName", Projekt);
            startActivity(intent);
        }
    }
}
