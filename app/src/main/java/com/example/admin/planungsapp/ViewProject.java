package com.example.admin.planungsapp;

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

import java.util.ArrayList;


public class ViewProject extends AppCompatActivity {

    TextView projectName, ListEmpty;
    Button toAddTask, btnToAddUser, toCalender;
    ListView listTast;
    ArrayAdapter taskListe;
    RelativeLayout listUD;
    private ArrayList<String> taskName = new ArrayList<>();
    private ArrayList<String> Keys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_view);

        //Views
        projectName = (TextView)findViewById(R.id.projectName);
        btnToAddUser = (Button)findViewById(R.id.btnToAddUser);
        toAddTask = (Button)findViewById(R.id.btnToCreateTask);
        toCalender = (Button)findViewById(R.id.btnToCalender);
        ListEmpty = (TextView)findViewById(R.id.txtEmptyList);

        Intent intent = getIntent();
        String proName = intent.getStringExtra("ProjektName");
        projectName.setText(intent.getStringExtra("ProjektName"));

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

            addTasksToList(proName);
    }

    private void addTasksToList(String proName) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Projekte").child(proName).child("Anforderungen");
        listTast = (ListView)findViewById(R.id.lstTasks);

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

    private void toAddUser(){
        Intent toAddUser = new Intent(getApplicationContext(),AddUserToProject.class);
        Intent lastIntent = getIntent();
        // Replace with Create Activity
        toAddUser.putExtra("projektName", lastIntent.getStringExtra("ProjektName"));
        startActivity(toAddUser);
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
