package com.example.admin.planungsapp;

import android.annotation.SuppressLint;
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

    private Button btn_CreateProjects_CreateProject;
    private DatabaseReference database;
    private EditText txtE_CreateProjects_ProjectName, txtE_CreateProjects_ProjectDescription;
    private String uId;

    private FirebaseAuth Auth;
    /**
     *
     * Beim Starten dieser Activtvity werden alle Views den Instanzvariablen zugewiesen.
     * Es wird auf dem Button btn_CreateProjects_CreateProject ein OnClickListener durchgeführt
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_create);

        database       = FirebaseDatabase.getInstance().getReference();
        uId            = FirebaseAuth.getInstance().getCurrentUser().getUid();

        txtE_CreateProjects_ProjectName           = findViewById(R.id.txtProjectname);
        txtE_CreateProjects_ProjectDescription    = findViewById(R.id.txtProdescription);
        btn_CreateProjects_CreateProject          = findViewById(R.id.btnFirebase);
        btn_CreateProjects_CreateProject.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

              createProject();

            }
        });
    }

    /**
     * Diese Methode erstellt Projekte und fügt die Projekte dem Angemeldeten Benutzer hinzu
     */
    @SuppressLint("SetTextI18n")
    private void createProject(){
        //wird gebraucht um zu überprüfen, dass beide EditTexts nicht leer sind.
        boolean valid = true;
        if(txtE_CreateProjects_ProjectName.getText().toString().equals("")) {
            txtE_CreateProjects_ProjectName.setHintTextColor(getResources().getColor(R.color.colorAccent));
            valid = false;
        }
        if (txtE_CreateProjects_ProjectDescription.getText().toString().equals("")) {
            txtE_CreateProjects_ProjectDescription.setHintTextColor(getResources().getColor(R.color.colorAccent));
            valid = false;
        }
        if (valid) {
            // Projektname besteht aus uid des Ersteller und Projektnamen
            String FullProjectName = uId + "_" + txtE_CreateProjects_ProjectName.getText().toString();
            // Projekt erstellen
            database.child("Projekte").child(FullProjectName).child("beschreibung").setValue(txtE_CreateProjects_ProjectDescription.getText().toString());
            // Projekt Benutzer hinzufügen
            database.child("User_Projekt").child(uId).child(FullProjectName).setValue(FullProjectName);
            Intent intentMain = new Intent(CreateProjects.this,
                    HomeActivity.class);
            CreateProjects.this.startActivity(intentMain);
        }
    }
}