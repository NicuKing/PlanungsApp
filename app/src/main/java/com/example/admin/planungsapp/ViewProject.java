package com.example.admin.planungsapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * In dieser Activity werden alle Tasks aufgelisten und es gibt eine NAvigation.
 * @author René Meisters
 * @version 1.0.0
 */
public class ViewProject extends AppCompatActivity {

    private TextView projectName, projectDescrip;
    private Button toAddTask, btnToAddUser, toCalender;
    private ListView listTast;
    private ArrayAdapter taskListe;
    private RelativeLayout listUD;
    private boolean exist;
    private ArrayList<String> taskName = new ArrayList<>();
    private ArrayList<String> Keys = new ArrayList<>();
    /**
     *
     * Beim Starten dieser Activtvity werden alle Views den Instanzvariablen zugewiesen.
     * Es wird auf den Buttons toAddTask, btnToAddUser & toCalender ein OnClickListener durchgeführt
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_view);

        //Views
        projectName    = findViewById(R.id.projectName);
        projectDescrip = findViewById(R.id.projectDescription);
        btnToAddUser   = findViewById(R.id.btnToAddUser);
        toAddTask      = findViewById(R.id.btnToCreateTask);
        toCalender     = findViewById(R.id.btnToCalender);

        Intent intent  = getIntent();
        String proName = intent.getStringExtra("ProjektName");
        String[] proNameTitel = proName.split("_");
        projectName.setText(proNameTitel[1]);

        projectDescrip.setText(FirebaseDatabase.getInstance().getReference().child("Projekte").child(proName).child("beschreibung").toString());

        toAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toAddTask();
            }
        });

        btnToAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toAddUser();
            }
        });

        toCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toCalender();
            }
        });

        if(!existingTasks(proName)) {
            addTasksToList(proName);
        }
        getDescription(proName);
    }

    /**
     * hohlt die Beschreibung zu dem ausgewählten Projekt.
     * @param proName
     */
    private void getDescription(String proName) {
        DatabaseReference description = FirebaseDatabase.getInstance().getReference().child("Projekte").child(proName).child("beschreibung");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 projectDescrip.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        };
        description.addValueEventListener(postListener);
    }

    /**
     * Schaut ob Tasks existieren, bevor es die addTasksToList Methode ausführt.
     * @praam proName
     * @return boolean
     */
    private boolean existingTasks(String proName) {
        DatabaseReference checkTask = FirebaseDatabase.getInstance().getReference().child("Projekte").child(proName).child("Anforderungen");
        ValueEventListener eventListener = new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Checkt ob dieser Benutzer überhaupt vorhanden ist.
                if(!dataSnapshot.exists()) {
                    exist = false;
                } else {
                    // Das Projekt wird zu den Projekten des Nutzers hinzugefügt
                    exist = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        checkTask.addListenerForSingleValueEvent(eventListener);
        return exist;
    }

    /**
     * Tasks werden zu einer ArrayList hnzugefügt
     * @param proName Projektname
     */
    private void addTasksToList(String proName) {
        listTast = (ListView)findViewById(R.id.lstTasks);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Projekte").child(proName).child("Anforderungen");
        taskListe = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,taskName);
        listTast.setAdapter(taskListe);

        final AdapterView.OnItemClickListener ListClickedHandler = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChangeTasks.class);
                Intent getIntent = getIntent();
                String selected = parent.getItemAtPosition(position).toString();
                intent.putExtra("taskName", selected);
                intent.putExtra("projectName", getIntent.getStringExtra("ProjektName"));
                startActivity(intent);
            }
        };
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                taskName.add(value);

                String key = dataSnapshot.getKey();
                Keys.add(key);

                taskListe.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                String key = dataSnapshot.getKey();

                int index = Keys.indexOf(key);
                taskName.set(index, value);

              taskListe.notifyDataSetChanged();
                // ProjektNamen== taskName
                // arrayAdapter== taskListe;
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
        listTast.setOnItemClickListener(ListClickedHandler);
    }

    /**
     * Man wird zur AddUser Activity umgeleitet
     */
    private void toAddUser(){

        Intent toAddUser = new Intent(getApplicationContext(),AddUserToProject.class);
        Intent lastIntent = getIntent();
        // Replace with Create Activity
        toAddUser.putExtra("projektName", lastIntent.getStringExtra("ProjektName"));
        startActivity(toAddUser);
}

    /**
     * Umleitung zur Create Task Activity
     */
    private void toAddTask(){
        Intent toTask = new Intent(getApplicationContext(),CreateTask.class);
        Intent lastIntent = getIntent();
        toTask.putExtra("projectName", lastIntent.getStringExtra("ProjektName"));
        startActivity(toTask);
    }

    /**
     * Umleitung zur Kalender Activity
     */
    private void toCalender(){
        Intent toDate = new Intent(getApplicationContext(),MainCalender.class);
        Intent lastIntent = getIntent();
        toDate.putExtra("projectName", lastIntent.getStringExtra("ProjektName"));
        startActivity(toDate);
    }


}
