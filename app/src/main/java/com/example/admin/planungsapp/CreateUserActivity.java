package com.example.admin.planungsapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * In dieser ctivity können sich User registrieren. Umgesetzt wird das mit hilf on Firebase Authentication
 */
public class CreateUserActivity extends AppCompatActivity {

    private EditText input_email, input_pwd,input_pwd2;
    private Button btn_signUp;
    private String email,pwd, pwd2;

    private FirebaseAuth mAuth;

    /**
     *
     * Beim Starten dieser Activtvity werden alle Views den Instanzvariablen zugewiesen.
     * Es wird auf dem Button btn_signUp ein OnClickListener durchgeführt
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        //Views

        input_email = findViewById(R.id.txt_createUser_email);
        input_pwd   = findViewById(R.id.txt_createUser_pwd);
        input_pwd2  = findViewById(R.id.txt_createUser_pwd2);
        btn_signUp  = findViewById(R.id.btn_createUser);

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNewUser();
            }
        });
    }

    private void CreateNewUser(){

        email = input_email.getText().toString();
        pwd   = input_pwd.getText().toString();
        pwd2  = input_pwd2.getText().toString();
        // Check if passwords match
        if(pwd == pwd2){
            Toast.makeText(CreateUserActivity.this, "Passwords don't match", Toast.LENGTH_LONG).show();
        }
        //Check If Empty
        else if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwd2)) {
            Toast.makeText(CreateUserActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
        }
        else{
            //SignUP
            mAuth.createUserWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                Toast.makeText(CreateUserActivity.this, "SignUp succesfully",
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(CreateUserActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }


                        }
                    });
        }
    }
}
