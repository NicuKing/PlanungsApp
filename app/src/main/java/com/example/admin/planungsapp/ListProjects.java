package com.example.admin.planungsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;
import java.util.ArrayList;

public class ListProjects extends AppCompatActivity {
    private DatabaseReference Database;
    private ListView ProjektList;
    private ArrayList<String> ProjektNamen = new ArrayList<>();
    private ArrayList<String> Keys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Database = FirebaseDatabase.getInstance().getReference().child("User_Projekt").child("Username");

        ProjektList = (ListView) findViewById(R.id.lstProjects);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ProjektNamen);
        ProjektList.setAdapter(arrayAdapter);

        final AdapterView.OnItemClickListener ListClickedHandler = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ViewProject.class);
                String selected = parent.getItemAtPosition(position).toString();
                intent.putExtra("ProjektName", selected);
                startActivity(intent);
            }
        };

        Database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                ProjektNamen.add(value);

                String key = dataSnapshot.getKey();
                Keys.add(key);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                String key = dataSnapshot.getKey();

                int index = Keys.indexOf(key);
                ProjektNamen.set(index, value);

                arrayAdapter.notifyDataSetChanged();
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
        ProjektList.setOnItemClickListener(ListClickedHandler);
    }
}
