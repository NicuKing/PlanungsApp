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

public class AddUserToProject extends AppCompatActivity {


    private Button AddUserButton;
    private TextView NotFound;
    private EditText AddUserUid;
    private String uId;
    private String proName;
    private DatabaseReference Database, CheckUser;
    private Intent LastIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        AddUserButton = (Button) findViewById(R.id.btnDoAdd);
        AddUserUid = (EditText) findViewById(R.id.txtAddUser);
        NotFound = (TextView)findViewById(R.id.txtNotFound);

        Database = FirebaseDatabase.getInstance().getReference().child("User_Projekt");
        uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        LastIntent = getIntent();
        final String proName = LastIntent.getStringExtra("projektName");

        AddUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckUser = FirebaseDatabase.getInstance().getReference().child("User_Projekt").child(AddUserUid.getText().toString());

                ValueEventListener eventListener = new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()) {
                            NotFound.setText("User doesn't exist");
                        } else {
                            String FullProjectName = proName;
                            Database.child(AddUserUid.getText().toString()).child(FullProjectName).setValue(FullProjectName);
                            Intent toView = new Intent(AddUserToProject.this, ViewProject.class);
                            toView.putExtra("ProjektName", proName);
                            AddUserToProject.this.startActivity(toView);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };

                CheckUser.addListenerForSingleValueEvent(eventListener);
            }
        });
    }
}