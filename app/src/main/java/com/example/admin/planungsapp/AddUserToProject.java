package com.example.admin.planungsapp;

import android.annotation.SuppressLint;
import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Diese Activity ist dazu da andere Benutzer zu seinemProekt hinzuzufügen.
 * @author Nicolas Lachenal
 * @version 1.0.0
 */

public class AddUserToProject extends AppCompatActivity {


    private Button addUserButton;
    private TextView notFound;
    private EditText addUserUid;
    private String uId;
    private String proName;
    private DatabaseReference database, checkUser;
    private Intent lastIntent;


    /**
     *
     * Beim Starten dieser Activtvity werden alle Views den Instanzvariablen zugewiesen.
     * Es wird auf dem Button addUserButton ein OnClickListener durchgeführt
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        //Views
        addUserButton = findViewById(R.id.btnDoAdd);
        addUserUid    = findViewById(R.id.txtAddUser);
        notFound      = findViewById(R.id.txtNotFound);
        //Database reference and get uid
        database   = FirebaseDatabase.getInstance().getReference().child("User_Projekt");
        uId        = FirebaseAuth.getInstance().getCurrentUser().getUid();
        lastIntent = getIntent();
        //last Intent values
        proName    = lastIntent.getStringExtra("projektName");
        //Button OnClickistener
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addUserToProject();
            }
        });
    }

    /**
     * In dieser Methode wird das Aktuelle Projekt zu den Projektem vom User
     * Mit der eingegeben ID hinzugefügt.
     */
    private void addUserToProject(){
        //Schnittstelle Zu den Prjekten vom ausgewählten Benutzer
        checkUser = FirebaseDatabase.getInstance().getReference().child("User_Projekt").child(addUserUid.getText().toString());

        ValueEventListener eventListener = new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Checkt ob dieser Benutzer überhaupt vorhanden ist.
                if(!dataSnapshot.exists()) {
                    notFound.setText("User doesn't exist");
                } else {
                    // Das Projekt wird zu den Projekten des Nutzers hinzugefügt
                    String FullProjectName = proName;
                    database.child(addUserUid.getText().toString()).child(FullProjectName).setValue(FullProjectName);
                    Intent toView = new Intent(AddUserToProject.this, ViewProject.class);
                    toView.putExtra("ProjektName", proName);
                    AddUserToProject.this.startActivity(toView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        checkUser.addListenerForSingleValueEvent(eventListener);

    }
}