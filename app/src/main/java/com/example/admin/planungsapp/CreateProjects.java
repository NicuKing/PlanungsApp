package com.example.admin.planungsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateProjects extends AppCompatActivity {

    private Button FirebaseButton;
    private DatabaseReference Database;
    private EditText Name;
    private EditText Description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_create);

        Database = FirebaseDatabase.getInstance().getReference();

        Name = (EditText) findViewById(R.id.txtProjectname);
        Description = (EditText) findViewById(R.id.txtProdescription);

        FirebaseButton = (Button) findViewById(R.id.btnFirebase);
        FirebaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database.child("Projekte").child(Name.getText().toString()).child("beschreibung").setValue(Description.getText().toString());
                Database.child("User_Projekt").child("Username").child(Name.getText().toString()).setValue(Name.getText().toString());
            }
        });
    }
}
