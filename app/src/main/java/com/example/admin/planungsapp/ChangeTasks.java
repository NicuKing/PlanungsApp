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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Diese Activity dient dem Erstellen von Anforderungen für das jeweilige Projekt.
 * @author René Meisters
 * @version 1.0.0
 */
public class ChangeTasks extends AppCompatActivity {

    private EditText txtE_ChangeTasks_TaskName;
    private Button btn_ChangeTasks_ChangeTaskName;
    private Button btn_ChangeTasks_DeleteTask;
    private String str_ChangeTasks_TaskName;
    private DatabaseReference Database;
    /**
     *
     * Beim Starten dieser Activtvity werden alle Views den Instanzvariablen zugewiesen.
     * Es wird auf den Buttons btn_ChangeTasks_ChangeTaskName und btn_ChangeTasks_DeleteTask ein OnClickListener durchgeführt
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
        txtE_ChangeTasks_TaskName      = findViewById(R.id.txtTaskName_Change);
        btn_ChangeTasks_ChangeTaskName = findViewById(R.id.btnChangeTasks);
        btn_ChangeTasks_DeleteTask     = findViewById(R.id.btnChangeTasks);

        Intent getIntent = getIntent();
        str_ChangeTasks_TaskName = getIntent.getStringExtra("txtE_ChangeTasks_TaskName");
        btn_ChangeTasks_DeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTask();
            }
        });

        btn_ChangeTasks_ChangeTaskName.setOnClickListener(new View.OnClickListener() {
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

        Database.child("Projekte").child(oldIntent.getStringExtra("projectName")).child("Anforderungen").child(str_ChangeTasks_TaskName).removeValue();
        Intent intent = new Intent(getApplicationContext(), ViewProject.class);
        intent.putExtra("ProjektName",oldIntent.getStringExtra("projectName"));
        startActivity(intent);
    }

    /**
     * Der Ausgewählt Task wird mit den Nutzereingabe(txtE_ChangeTasks_TaskName) geupdated
     */
    private void updateTask(){
        Database = FirebaseDatabase.getInstance().getReference();
        Intent oldIntent = getIntent();

        Database.child("Projekte").child(oldIntent.getStringExtra("projectName")).child("Anforderungen").child(str_ChangeTasks_TaskName).setValue(txtE_ChangeTasks_TaskName);
        Intent intent = new Intent(getApplicationContext(), ViewProject.class);
        intent.putExtra("ProjektName",oldIntent.getStringExtra("projectName"));
        startActivity(intent);
    }
}
