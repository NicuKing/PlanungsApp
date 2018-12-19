package com.example.admin.planungsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Diese Activity dient dem Erstellen von Anforderungen für das jeweilige Projekt.
 * @author René Meisters
 * @version 1.0.0
 */
public class ChangeTasks extends AppCompatActivity {

    private EditText txtE_ChangeTask_NewTaskName;
    private Button btn_ChangeTask_UpdateTask;
    private Button btn_ChangeTask_DeleteTask;
    private String name;
    private DatabaseReference Database;
    /**
     *
     * Beim Starten dieser Activtvity werden alle Views den Instanzvariablen zugewiesen.
     * Es wird auf den Buttons btn_ChangeTask_UpdateTask und btn_ChangeTask_DeleteTask ein OnClickListener durchgeführt
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        txtE_ChangeTask_NewTaskName  = findViewById(R.id.txtTaskName_Change);
        btn_ChangeTask_UpdateTask = findViewById(R.id.btnChangeTasks);
        btn_ChangeTask_DeleteTask = findViewById(R.id.btnDeleteTasks);

        Intent getIntent = getIntent();
        name = getIntent.getStringExtra("taskName");
        btn_ChangeTask_DeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTask();
            }
        });

        btn_ChangeTask_UpdateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTask();
            }
        });

    }

    /**
     * Der Ausgewählte Task wird gelöscht
     */
    private void deleteTask(){
        Database = FirebaseDatabase.getInstance().getReference();
        Intent oldIntent = getIntent();

        Database.child("Projekte").child(oldIntent.getStringExtra("projectName")).child("Anforderungen").child(name).removeValue();
        Intent intent = new Intent(getApplicationContext(), ViewProject.class);
        intent.putExtra("ProjektName",oldIntent.getStringExtra("projectName"));
        startActivity(intent);
    }

    /**
     * Der Ausgewählt Task wird mit den Nutzereingabe(taskName) geupdated
     */
    private void updateTask(){
        Toast.makeText(ChangeTasks.this, "Not yet implemented",
                Toast.LENGTH_SHORT).show();
    }
}
