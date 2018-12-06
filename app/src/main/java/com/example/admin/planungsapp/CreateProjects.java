package com.example.admin.planungsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateProjects extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener authListener;
    private Button FirebaseButton;
    private DatabaseReference Database;
    private EditText Name;
    private EditText Description;
    String projectName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_create);

        Database = FirebaseDatabase.getInstance().getReference();

        Name = (EditText) findViewById(R.id.txtProjectname);
        Description = (EditText) findViewById(R.id.txtProdescription);




        FirebaseButton = (Button) findViewById(R.id.btnFirebase);
        FirebaseButton = (Button) findViewById(R.id.btnFirebase);
        FirebaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database.child("Projekte").child(Name.getText().toString()).child("beschreibung").setValue(Description.getText().toString());
                Database.child("User_Projekt").child("Username").child(projectName).setValue(Name.getText().toString());
                Intent intentMain = new Intent(CreateProjects.this ,
                        HomeActivity.class);
                CreateProjects.this.startActivity(intentMain);
            }
        });
    }
}
