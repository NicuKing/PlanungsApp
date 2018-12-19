package com.example.admin.planungsapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
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

    private TextView txtV_ViewProject_ProjectName, txtV_ViewProject_ProjectDescription;
    private Button btn_ViewProject_ToAddTask, btn_ViewProject_ToAddUser, btn_ViewProject_ToCalendar;
    private ListView lst_ViewProject_allTasks;
    private ArrayAdapter taskListe;
    private RelativeLayout listUD;
    private boolean exist;
    private ArrayList<String> taskName = new ArrayList<>();
    private ArrayList<String> Keys = new ArrayList<>();
    /**
     *
     * Beim Starten dieser Activtvity werden alle Views den Instanzvariablen zugewiesen.
     * Es wird auf den Buttons btn_ViewProject_ToAddTask, btn_ViewProject_ToAddUser & btn_ViewProject_ToCalendar ein OnClickListener durchgeführt
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_view);

        //Views
        txtV_ViewProject_ProjectName        = findViewById(R.id.projectName);
        txtV_ViewProject_ProjectDescription = findViewById(R.id.projectDescription);
        btn_ViewProject_ToAddUser           = findViewById(R.id.btnToAddUser);
        btn_ViewProject_ToAddTask           = findViewById(R.id.btnToCreateTask);
        btn_ViewProject_ToCalendar          = findViewById(R.id.btnToCalender);

        Intent intent  = getIntent();
        String proName = intent.getStringExtra("ProjektName");
        String[] proNameTitel = proName.split("_");
        txtV_ViewProject_ProjectName.setText(proNameTitel[1]);

        txtV_ViewProject_ProjectDescription.setText(FirebaseDatabase.getInstance().getReference().child("Projekte").child(proName).child("beschreibung").toString());

        btn_ViewProject_ToAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toAddTask();
            }
        });

        btn_ViewProject_ToAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toAddUser();
            }
        });

        btn_ViewProject_ToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToCalendar();
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
                txtV_ViewProject_ProjectDescription.setText(dataSnapshot.getValue().toString());
                txtV_ViewProject_ProjectDescription.setPaintFlags(txtV_ViewProject_ProjectDescription.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
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
        lst_ViewProject_allTasks = (ListView)findViewById(R.id.lstTasks);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Projekte").child(proName).child("Anforderungen");
        taskListe = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,taskName);
        lst_ViewProject_allTasks.setAdapter(taskListe);

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
        lst_ViewProject_allTasks.setOnItemClickListener(ListClickedHandler);
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
    private void ToCalendar(){
        Intent toDate = new Intent(getApplicationContext(),MainCalendar.class);
        Intent lastIntent = getIntent();
        toDate.putExtra("projectName", lastIntent.getStringExtra("ProjektName"));
        startActivity(toDate);
    }


}
