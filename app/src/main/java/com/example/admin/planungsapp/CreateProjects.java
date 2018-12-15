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

/**
 * Diese Klasse dient dem Erstellen von Projekten.
 * @author Nicolas Lachenal
 * @version 1.0.0
 */
public class CreateProjects extends AppCompatActivity {

    private Button firebaseButton;
    private DatabaseReference database;
    private EditText name;
    private EditText description;
    private String uId;

    private FirebaseAuth Auth;
    /**
     *
     * Beim Starten dieser Activtvity werden alle Views den Instanzvariablen zugewiesen.
     * Es wird auf dem Button firebaseButton ein OnClickListener durchgeführt
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_create);

        database       = FirebaseDatabase.getInstance().getReference();
        uId            = FirebaseAuth.getInstance().getCurrentUser().getUid();
        name           = findViewById(R.id.txtProjectname);
        description    = findViewById(R.id.txtProdescription);

        firebaseButton = (Button) findViewById(R.id.btnFirebase);
        firebaseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

              createProject();

            }
        });
    }

    /**
     * Diese Klasse erstellt Projekte und fügt die Projekte dem Angemeldeten Benutzer hinzu
     */
    private void createProject(){
        // Projektname besteht aus uid des Ersteller und Projektnamen
        String FullProjectName = uId+"_"+name.getText().toString();
        // Projekt erstellen
        database.child("Projekte").child(FullProjectName).child("beschreibung").setValue(description.getText().toString());
        // Projekt Benutzer hinzufügen
        database.child("User_Projekt").child(uId).child(FullProjectName).setValue(FullProjectName);
        Intent intentMain = new Intent(CreateProjects.this ,
                HomeActivity.class);
        CreateProjects.this.startActivity(intentMain);
    }
}