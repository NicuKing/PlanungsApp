package com.example.admin.planungsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Diese Activity checkt ob ein Nutzer eingelogt ist
 * Auch ist es hier möglich sich einzuloggen
 * @author René Meisters
 * @version 1.0.0
 */
public class MainLogin extends AppCompatActivity {

    private EditText inp_email,inp_pwd;
    private Button btn_login, btn_toSignUp;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListener;

    /**
     *
     * Beim Starten dieser Activtvity werden alle Views den Instanzvariablen zugewiesen.
     * Es wird auf den Buttons btn_login udn btn_SignUp wird ein OnClickListener durchgeführt
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        mAuth        = FirebaseAuth.getInstance();

        inp_email    = findViewById(R.id.txt_login_email);
        inp_pwd      = findViewById(R.id.txt_login_pwd);
        btn_login    = findViewById(R.id.btn_login);
        btn_toSignUp = findViewById(R.id.btn_toSignUp);

        /**
         * Authentication Listener checkt ob benuter eingelggt ist
         * Wenn ja umleitung zur Home Activity
         */
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(MainLogin.this, HomeActivity.class));
                }
            }
        };
        btn_toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainLogin.this,CreateUserActivity.class));
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignIn();
            }
        });
    }

    /**
     * Authentication Lstener wird gestartet
     */
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authListener);
    }

    /**
     * Starten Login mit Firebase Athentication
     */
    private void startSignIn(){
        String email = inp_email.getText().toString();
        String password = inp_pwd.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(MainLogin.this, "Please fill all fields", Toast.LENGTH_LONG).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(MainLogin.this, "Sign In Problem", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
