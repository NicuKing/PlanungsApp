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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateProjects extends AppCompatActivity {

    private Button FirebaseButton;
    private DatabaseReference Database;
    private EditText Name;
    private EditText Description;
    private String uId;

    private FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_create);

        Database = FirebaseDatabase.getInstance().getReference();
        uId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Name = (EditText) findViewById(R.id.txtProjectname);
        Description = (EditText) findViewById(R.id.txtProdescription);

        FirebaseButton = (Button) findViewById(R.id.btnFirebase);
        FirebaseButton = (Button) findViewById(R.id.btnFirebase);
        FirebaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FullProjectName = uId+""+Name.getText().toString();
                Database.child("Projekte").child(FullProjectName).child("beschreibung").setValue(Description.getText().toString());
                Database.child("User_Projekt").child(uId).child(FullProjectName).setValue(FullProjectName);
                Intent intentMain = new Intent(CreateProjects.this ,
                        HomeActivity.class);
                CreateProjects.this.startActivity(intentMain);
            }
        });
    }
}