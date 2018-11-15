package com.example.admin.planungsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnlogin;
    Button btncreatep;
    Button btnviewp;
    Button btncalender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btncalender = findViewById(R.id.btnCalender);

        btnlogin = (Button) findViewById(R.id.btnLogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent login = new Intent(MainActivity.this, MainLogin.class);

                startActivity(login);

            }
        });

        btncreatep = (Button) findViewById(R.id.btnCreateProjects);
        btncreatep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent createproject = new Intent(MainActivity.this, CreateProjects.class);

                startActivity(createproject);

            }
        });

        btnviewp = (Button) findViewById(R.id.btnViewProjects);
        btnviewp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent viewprojects = new Intent(MainActivity.this, ListProjects.class);

                startActivity(viewprojects);

            }
        });

        btncalender = (Button) findViewById(R.id.btnCalender);
        btncalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent calender = new Intent(MainActivity.this, MainCalender.class);

                startActivity(calender);

            }
        });
    }
}
